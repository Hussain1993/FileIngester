package com.pink.triangle.hussain.server;

import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.elastic.client.ElasticClient;
import io.searchbox.client.JestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Shutting down the client");
        client.shutdownClient();
    }
}
