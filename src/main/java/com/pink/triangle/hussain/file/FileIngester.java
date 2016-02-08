package com.pink.triangle.hussain.file;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.elastic.client.ElasticClient;
import com.pink.triangle.hussain.util.PutResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by hmiah on 04-Feb-16.
 */
public class FileIngester implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(FileIngester.class);

    private File fileToIngest;
    private File workingDir;
    private File errorDir;

    public FileIngester(Path fileToIngest){
        try {
            this.fileToIngest = fileToIngest.toRealPath(LinkOption.NOFOLLOW_LINKS).toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();
        workingDir = new File(applicationConfig.getWorkingDir());
        errorDir = new File(applicationConfig.getErrorDir());
        if(!workingDir.exists() || !errorDir.exists())
        {
            LOG.info("Creating the working and error directories");
            workingDir.mkdirs();
            errorDir.mkdirs();
        }
    }

    public void run() {
        String message = String.format("Moving the file %s to the working directory",fileToIngest.getPath());
        LOG.info(message);
        try {
            FileUtils.moveToDirectory(fileToIngest,workingDir,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File newFile = new File(workingDir,fileToIngest.getName());
        if(newFile.exists())
        {
            message = String.format("The file %s has been moved to the working directory %s",fileToIngest.getName(),
                    newFile.toString());
            LOG.info(message);
        }
        IngestFile ingestFile = _createObject(newFile);
        _ingestFile(ingestFile);
    }

    private IngestFile _createObject(File workingFile){
        FileInputStream fis = null;
        String md5 = StringUtils.EMPTY;
        String content = StringUtils.EMPTY;
        try{
            fis = new FileInputStream(workingFile);
            md5 = DigestUtils.md5Hex(fis);
            byte[] encoded = Files.readAllBytes(Paths.get(workingFile.toString()));
            content = new String(encoded, Charset.forName("UTF-8"));
        } catch (FileNotFoundException e) {
            LOG.error("The ingest file was not found",e);
        } catch (IOException e) {
            LOG.error("There was an error when getting the hash for the file");
        } finally {
            IOUtils.closeQuietly(fis);
        }
        IngestFile ingestFile = new IngestFile(md5);
        ingestFile.setDocumentContent(content);
        ingestFile.setFileSize(workingFile.length());
        ingestFile.setDateIngested(System.currentTimeMillis());
        ingestFile.setFilename(workingFile.getName());
        Path path = workingFile.toPath();
        try {
            BasicFileAttributes attr = Files.readAttributes(path,BasicFileAttributes.class);
            ingestFile.setDateCreated(attr.creationTime().toMillis());
        } catch (IOException e) {
            LOG.info("There was an error reading the file attributes");
        }
        return ingestFile;
    }

    private void _ingestFile(IngestFile ingestFile){
        PutResult result = ElasticClient.saveItem("files","document",ingestFile);
        if(result.get_version().equals("1"))
        {
            LOG.info("The file has been indexed");
        }
    }
}
