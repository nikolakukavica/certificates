package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping()
    public List<User> getAll(){
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user){
        return service.addUser(user);
    }

    @RequestMapping(value = "/{email}/{password}", method = RequestMethod.GET)
    public User checkUser(@PathVariable String email, @PathVariable String password){
        return service.checkUser(email,password);
    }

    @RequestMapping(value = "/approve/{user_id}", method = RequestMethod.POST)
    public void approveUser(@PathVariable long user_id){
        service.approveUser(user_id);
    }

    @RequestMapping(value = "/cancel/{user_id}", method = RequestMethod.POST)
    public void deleteUser(@PathVariable long user_id){
        service.deleteUser(user_id);
    }


    @RequestMapping(value = "/unapproved", method = RequestMethod.GET)
    public List<User> getUnapproved(){
        return service.getUnapproved();
    }

}
