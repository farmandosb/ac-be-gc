package com.armandocode.gclouddemo.user;

import com.armandocode.gclouddemo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


public class UserController {
	@Autowired
	DatastoreTemplate datastoreTemplate;
	
	@GetMapping("/users")
	public String showUsersList(Model model) {
		Iterable<Userapp> listUsers = this.datastoreTemplate.findAll(Userapp.class);
		List<Userapp> result = new ArrayList<>();
		result.forEach(result::add);
		model.addAttribute("listUsers", result);
		System.out.println("inside userapp controller");
		return "users";
	}
	@GetMapping("/users/new")
	public String showCreateNewUserForm(Model model) {
		Iterable<Role> listRoles = this.datastoreTemplate.findAll(Role.class);
		List<Role> result = new ArrayList<>();
		result.forEach(result::add);
		model.addAttribute("listRoles",result);
		model.addAttribute("user", new Userapp());
		
		return "user_form";
	}
}
