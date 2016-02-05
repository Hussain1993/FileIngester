package com.pink.triangle.hussain.config;

import java.util.Properties;

/**
 * Created by Hussain on 03/02/2016.
 */
public class ApplicationConfig {
    private final String ELASTIC_PROTOCOL_KEY = "elastic.protocol";
    private final String ELASTIC_PORT_KEY = "elastic.port";
    private final String ELASTIC_HOSTNAME_KEY = "elastic.hostname";
    private final String HTTP_READ_TIMEOUT_KEY = "http.read.timeout";
    private final String HTTP_CONNECTION_TIMEOUT = "http.connection.timeout";
    private final String HTTP_MAX_TOTAL_CONNECTION = "http.max.total.connections";
    private final String HTTP_MAX_TOTAL_CONNECTION_PER_ROUTE = "http.max.total.connection.per.route";
    private final String LANDING_DIR_KEY = "landing.dir";
    private final String WORKING_DIR_KEY = "working.dir";
    private final String ERRORS_DIR_KEY = "error.dir";

    private String elasticProtocol;
    private String elasticPort;
    private String elasticHostname;
    private String httpReadTimeout;
    private String httpConnectionTimeout;
    private String httpMaxTotalConnections;
    private String httpMaxTotalConnectionPerRoute;
    private String landingDir;
    private String workingDir;
    private String errorDir;


    public ApplicationConfig(final Properties properties){
        this.elasticProtocol = properties.getProperty(ELASTIC_PROTOCOL_KEY);
        this.elasticPort = properties.getProperty(ELASTIC_PORT_KEY);
        this.elasticHostname = properties.getProperty(ELASTIC_HOSTNAME_KEY);
        this.httpReadTimeout = properties.getProperty(HTTP_READ_TIMEOUT_KEY);
        this.httpConnectionTimeout = properties.getProperty(HTTP_CONNECTION_TIMEOUT);
        this.httpMaxTotalConnections = properties.getProperty(HTTP_MAX_TOTAL_CONNECTION);
        this.httpMaxTotalConnectionPerRoute = properties.getProperty(HTTP_MAX_TOTAL_CONNECTION_PER_ROUTE);
        this.landingDir = properties.getProperty(LANDING_DIR_KEY);
        this.workingDir = properties.getProperty(WORKING_DIR_KEY);
        this.errorDir = properties.getProperty(ERRORS_DIR_KEY);
    }

    public String getElasticPort(){
        return this.elasticPort;
    }

    public String getElasticProtocol(){
        return this.elasticProtocol;
    }

    public String getElasticHostname(){
        return this.elasticHostname;
    }

    public String getHttpReadTimeout(){
        return this.httpReadTimeout;
    }

    public String getHttpConnectionTimeout(){
        return this.httpConnectionTimeout;
    }

    public String getHttpMaxTotalConnections(){
        return this.httpMaxTotalConnections;
    }

    public String getHttpMaxTotalConnectionPerRoute(){
        return this.httpMaxTotalConnectionPerRoute;
    }

    public String getLandingDir(){
        return this.landingDir;
    }

    public String getWorkingDir(){
        return this.workingDir;
    }

    public String getErrorDir(){
        return this.errorDir;
    }
}
