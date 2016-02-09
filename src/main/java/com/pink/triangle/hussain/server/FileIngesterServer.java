package com.pink.triangle.hussain.server;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.elastic.client.ElasticClient;
import com.pink.triangle.hussain.file.FileWatcher;
import io.searchbox.client.JestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Created by Hussain on 08/02/2016.
 */
public class FileIngesterServer implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(FileIngesterServer.class);

    private static JestClient client;

    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Starting the FileIngester Server");
        ConfigManager.init();
        client = ElasticClient.getInstance();
        LOG.info("The client has started");
        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();
        File landingFolder = new File(applicationConfig.getLandingDir());
        Thread fileWatcherThread = new Thread(new FileWatcher(landingFolder.toPath()));
        fileWatcherThread.start();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Shutting down the client");
        client.shutdownClient();
        FileWatcher.getThreadPool().shutdown();
    }
}
