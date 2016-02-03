package com.pink.triangle.hussain.main;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.*;

/**
 * Created by Hussain on 03/02/2016.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception{
        ConfigManager.init();
        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();
        File lanndingFolder = new File(applicationConfig.getLandingDir());
        watchDirectory(lanndingFolder.toPath());
    }

    public static void watchDirectory(Path path) throws Exception{
        Boolean isFolder = (Boolean) Files.getAttribute(path,"basic:isDirectory", LinkOption.NOFOLLOW_LINKS);
        System.out.println("Watching path: "+path);

        FileSystem fileSystem = path.getFileSystem();
        WatchService service = fileSystem.newWatchService();
        try{
            path.register(service,StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key = null;

            while(true)
            {
                key = service.take();
                WatchEvent.Kind kind = null;
                for(WatchEvent<?> watchEvent : key.pollEvents())
                {
                    kind = watchEvent.kind();
                    if(kind == StandardWatchEventKinds.OVERFLOW)
                    {
                        continue;
                    }
                    else if(kind == StandardWatchEventKinds.ENTRY_CREATE)
                    {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        System.out.println("New path created: "+ newPath);
                    }
                }
            }
        }
        finally {
            service.close();
        }
    }
}
