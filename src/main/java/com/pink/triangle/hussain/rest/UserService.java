package com.pink.triangle.hussain.rest;

import com.pink.triangle.hussain.elastic.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by Hussain on 10/02/2016.
 */
@Path("/users")
public class UserService {


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public void login(User user){
        System.out.println(user.getEmailAddress());
        System.out.println(user.getPassword());
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registerUser(User user){
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmailAddress());
        System.out.println(user.getPassword());
    }
}
