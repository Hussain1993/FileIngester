package com.pink.triangle.hussain.config;

import org.apache.log4j.xml.DOMConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Hussain on 03/02/2016.
 */
public class ConfigManager {
    private static final String CONFIG_FILE_PATH = "config.file";
    private static final String LOG4J_XML = "log4j.xml";

    private static ApplicationConfig applicationConfig;

    //You can not make an object of this class
    private ConfigManager(){}

    public static void init(){
        stdOut("Initialising Config Manager");

        Properties properties = new Properties();

        String propertiesFileLocation = System.getProperty(CONFIG_FILE_PATH);
        String log4jxml = System.getProperty(LOG4J_XML);

        try{
            if(propertiesFileLocation != null && !propertiesFileLocation.isEmpty())
            {
                stdOut("Loading configuration from: "+propertiesFileLocation);
                properties.load(new FileInputStream(propertiesFileLocation));
            }

            applicationConfig = new ApplicationConfig(properties);
        }
        catch(IOException ioException){
            stdOut("Failed to configure the system from the config file" + propertiesFileLocation + ".",ioException);
        }

        if(log4jxml != null && !log4jxml.isEmpty())
        {
            DOMConfigurator.configure(log4jxml);
        }

        stdOut("Config manager init ok");
    }

    public static ApplicationConfig getApplicationConfig(){
        if(applicationConfig !=  null)
        {
            return applicationConfig;
        }
        else
        {
            stdOut("The config manager has not be Initialised", new Throwable("Invoke the init() method"));
            return new ApplicationConfig(new Properties());
        }
    }

    private static void stdOut(String message){
        System.out.println(message);
    }

    private static void stdOut(String message, Throwable throwable){
        System.out.println(message);
        throwable.printStackTrace();
    }


}
