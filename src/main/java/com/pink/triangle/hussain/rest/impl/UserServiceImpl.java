package com.pink.triangle.hussain.rest.impl;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.elastic.client.ElasticClient;
import com.pink.triangle.hussain.elastic.model.User;
import com.pink.triangle.hussain.rest.iface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Hussain on 10/02/2016.
 */
@Path("/users")
public class UserServiceImpl implements UserService{
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private String usersIndexName;
    private String usersDocumentType;


    public UserServiceImpl(){
        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();
        usersIndexName = applicationConfig.getUsersIndexName();
        usersDocumentType = applicationConfig.getUsersDocumentType();
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user){
        boolean userExists = ElasticClient.doesUserExits(usersIndexName, usersDocumentType,
                new String[]{"emailAddress","password"},
                new String[]{user.getEmailAddress(), user.getPassword()});
        if(userExists)
        {
            return Response.ok().build();
        }
        else
        {
            return Response.status(600).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registerUser(User user){
        String message = String.format("Registering a new user with the email %s",user.getEmailAddress());
        LOG.info(message);
        ElasticClient.saveItem(usersIndexName,usersDocumentType,user);
    }
}
