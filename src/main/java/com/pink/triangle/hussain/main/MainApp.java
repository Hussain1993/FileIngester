package com.pink.triangle.hussain.main;

import com.pink.triangle.hussain.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hussain on 03/02/2016.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception{
        ConfigManager.init();
        LOG.info("We are here");
    }
}
