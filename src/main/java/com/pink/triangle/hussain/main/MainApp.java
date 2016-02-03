package com.pink.triangle.hussain.main;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hussain on 03/02/2016.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    private static final String CONFIG_FILE_PATH = "config.file";
    private static final String LOG4J_XML = "log4j.xml";

    public static void main(String[] args) throws Exception{
        checkSystemProperties();
        LOG.info("We are here");
    }

    private static void checkSystemProperties() throws Exception{
        String propertiesFile = System.getProperty(CONFIG_FILE_PATH);
        if(propertiesFile == null || propertiesFile.isEmpty())
        {
            throw new Exception("The system property: config.file has not been" +
                    " specified at start up. Re-run the application again with the command" +
                    " java -Dconfig.file=[PATH TO PROPERTIES FILE] -jar [JAR FILE]");
        }
        String lof4jFile = System.getProperty(LOG4J_XML);
        if(lof4jFile == null || lof4jFile.isEmpty())
        {
            throw new Exception("The name of the log4j file has not been specified. Re-run the application" +
                    " again with the command java -Dlog4j.xml=[PATH TO LOG4J.XML] -jar [JAR FILE]");
        }
        DOMConfigurator.configure(lof4jFile);
    }
}
