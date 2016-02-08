package com.pink.triangle.hussain.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Hussain on 08/02/2016.
 */
@Path("/test")
public class Test {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test(){
        return "Hello";
    }
}
