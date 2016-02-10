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
    private final String FILES_INDEX_NAME_KEY = "files.index.name";
    private final String FILES_DOCUMENT_TYPE_KEY = "files.document.type";
    private final String SYNCH_STATUS_INDEX_NAME_KEY = "synch.status.index.name";
    private final String SYNCH_STATUS_TYPE_KEY = "synch.status.type";
    private final String SYNCH_STATUS_ID_KEY = "synch.status.id";
    private final String USERS_INDEX_NAME_KEY = "users.index.name";
    private final String USERS_DOCUMENT_TYPE_KEY= "users.type";

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
    private String filesIndexName;
    private String filesDocumentType;
    private String synchStatusIndexName;
    private String synchStatusType;
    private String synchStatusId;
    private String usersIndexName;
    private String usersDocumentType;


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
        this.filesIndexName = properties.getProperty(FILES_INDEX_NAME_KEY);
        this.filesDocumentType = properties.getProperty(FILES_DOCUMENT_TYPE_KEY);
        this.synchStatusIndexName = properties.getProperty(SYNCH_STATUS_INDEX_NAME_KEY);
        this.synchStatusType = properties.getProperty(SYNCH_STATUS_TYPE_KEY);
        this.synchStatusId = properties.getProperty(SYNCH_STATUS_ID_KEY);
        this.usersIndexName = properties.getProperty(USERS_INDEX_NAME_KEY);
        this.usersDocumentType = properties.getProperty(USERS_DOCUMENT_TYPE_KEY);
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

    public String getFilesIndexName(){
        return this.filesIndexName;
    }

    public String getFilesDocumentType(){
        return this.filesDocumentType;
    }

    public String getSynchStatusIndexName(){
        return this.synchStatusIndexName;
    }

    public String getSynchStatusType(){
        return this.synchStatusType;
    }

    public String getSynchStatusId(){
        return this.synchStatusId;
    }

    public String getUsersDocumentType() {
        return usersDocumentType;
    }

    public String getUsersIndexName() {
        return usersIndexName;
    }
}
