package com.pink.triangle.hussain.rest;

import com.pink.triangle.hussain.elastic.client.ElasticClient;
import com.pink.triangle.hussain.elastic.model.SynchStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Hussain on 08/02/2016.
 */
@Path("/tasking")
public class Tasking {

    @GET
    @Path("/synchStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public SynchStatus getSynchStatus(){
        SynchStatus synchStatus = (SynchStatus) ElasticClient.getItem("synch_status","synch-status","synch-id",
                SynchStatus.class);
        return synchStatus;
    }
}
