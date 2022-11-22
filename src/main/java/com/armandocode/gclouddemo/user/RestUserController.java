package com.armandocode.gclouddemo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RestUserController {
    @Autowired
    DatastoreTemplate datastoreTemplate;
	
	@GetMapping("/users")
    public Iterable<Userapp> getUsers() {
        return this.datastoreTemplate.findAll(Userapp.class);
    }

    @PostMapping("/users/new")
    void addUser(@RequestBody Userapp user) {
        this.datastoreTemplate.save(user);
    }
    
}
