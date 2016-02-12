package com.pink.triangle.hussain.rest.iface;

import com.pink.triangle.hussain.elastic.model.User;

import javax.ws.rs.core.Response;

/**
 * Created by Hussain on 12/02/2016.
 */
public interface UserService {

    Response login(User user);
    void registerUser(User user);
}
