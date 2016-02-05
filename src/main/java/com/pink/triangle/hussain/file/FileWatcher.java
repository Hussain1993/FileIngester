package com.pink.triangle.hussain.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hmiah on 04-Feb-16.
 */
public class FileWatcher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(FileWatcher.class);
    private static final int THREAD_POOL_SIZE = 10;

    private Path landingDir;
    private ExecutorService executorService;

    public FileWatcher(Path landingDir){
        this.landingDir = landingDir;
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void run() {
        try{
            Boolean isFolder = (Boolean) Files.getAttribute(landingDir,"basic:isDirectory", LinkOption.NOFOLLOW_LINKS);
            if(!isFolder)
            {
                throw new IOException("The landing directory is not a folder");
            }
            LOG.info("Watching the path: "+landingDir);
            FileSystem fileSystem = landingDir.getFileSystem();
            WatchService service = fileSystem.newWatchService();
            try{
                landingDir.register(service,StandardWatchEventKinds.ENTRY_CREATE);
                while(true)
                {
                    final WatchKey key = service.take();
                    for(WatchEvent<?> watchEvent : key.pollEvents())
                    {
                        WatchEvent.Kind kind = watchEvent.kind();
                        if(kind == StandardWatchEventKinds.ENTRY_CREATE)
                        {
                            final Path newPath = landingDir.resolve(((WatchEvent<Path>)watchEvent).context());
                            executorService.execute(new FileIngester(newPath));
                            key.reset();
                        }
                    }
                }
            } catch (InterruptedException e) {
                LOG.error("There was an error when watching the landing directory", e);
            } finally {
                service.close();
            }
        }
        catch(IOException ioException){
            LOG.error("There was an error in determine if the landing directory is a folder", ioException);
        }
    }
}
