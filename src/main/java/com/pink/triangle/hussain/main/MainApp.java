package com.pink.triangle.hussain.main;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.file.FileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Hussain on 03/02/2016.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception{
        ConfigManager.init();
        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();
        File landingFolder = new File(applicationConfig.getLandingDir());
        Thread fileWatcherThread = new Thread(new FileWatcher(landingFolder.toPath()));
        LOG.info("Starting the file watcher thread");
        fileWatcherThread.start();
    }
}
