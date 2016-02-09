package com.pink.triangle.hussain.rest;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.elastic.client.ElasticClient;
import com.pink.triangle.hussain.elastic.model.SynchStatus;
import com.pink.triangle.hussain.elastic.model.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Hussain on 08/02/2016.
 */
@Path("/tasking")
public class Tasking {
    private String synchStatusIndexName;
    private String synchStatusType;
    private String synchStatusId;

    public Tasking(){
        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();

        synchStatusIndexName = applicationConfig.getSynchStatusIndexName();
        synchStatusType = applicationConfig.getSynchStatusType();
        synchStatusId = applicationConfig.getSynchStatusId();
    }

    @GET
    @Path("/synchStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public SynchStatus getSynchStatus(){
        SynchStatus synchStatus = (SynchStatus) ElasticClient.getItem(synchStatusIndexName,synchStatusType,
                synchStatusId, SynchStatus.class);
        return synchStatus;
    }

    @POST
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    public void test(Test test){
        System.out.println(test.getName());
    }
}
