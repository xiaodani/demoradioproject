/*****************************************************************************/
/*!
\file   GenreController.java
\author Loo Chien Ming
\par    email: chienming1990\@gmail
\par    Radio Station Project
\date   19/2/2018
\brief  
  This file contains 4 functions 
    
  Hours spent on this assignment: ~20hours 
  
  Functions include:
  
  1. addNewListener			- Adds new listener into DB
  2. deleteListener			- Delete listener from DB
  3. updateListener			- Update a listener 
  4. viewAllListener		- List all listeners
  
  
  Specific portions that gave you the most trouble: 
  - Learning about java framework and syntax
  
  Uncompleted tasks
  - Deleting genre,songs,listeners,radiostation to affect other DB
  - Implementation of tests
  - Places files into proper folder; eg. Controllers, Repository

*/
/*****************************************************************************/

package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Listener;
import com.example.demo.ListenerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class ListenerController {
	@Autowired // makes it a singleton

	private ListenerRepository myListenerRepository;

	@GetMapping("/addlistener") // Map ONLY GET Requests
	public @ResponseBody String addNewListener (@RequestParam String name) {
		
		Listener myListener = myListenerRepository.findByNameIgnoreCase(name);

		//check if data already exist
		if(myListener == null){
			Listener newListener = new Listener();
			newListener.setName(name);
			myListenerRepository.save(newListener);
			return "Listener " + name + " has been saved";
		}else{
			System.out.println("Listener " + name + " is already in the database");
			return "Listener " + name + " is already in the database";
		}
	}

	@GetMapping("/deletelistener")
	public @ResponseBody String deleteListener (@RequestParam Integer id){
		Long result = myListenerRepository.deleteById(id);
		System.out.println("Listener ID:" + id + " deleted " + result);

		// check from query whether it is succesful
		if(result == 0){
			return "Listener ID:" + id + " failed to delete/not found in the database";
		}

		// WIP: Need to delete listener from other DB
		// TODO
		/*
			
		*/
		return "Listener ID:" + id + " deleted";
	}

	// update genre name
	@GetMapping("/updatelistener")
	public @ResponseBody String updateListener (@RequestParam Integer id, @RequestParam String newname){
		Listener myListener = myListenerRepository.findById(id);

		// check from query whether it is successful
		if(myListener == null){
			return "Listener ID: " + id + " doesn't exist";
		}

		// get old data
		String oldname = myListener.getName();

		// update to new data
		myListener.setName(newname);
		myListenerRepository.save(myListener);
		return "Listener ID: " + id + " changed from " + oldname + " to " + newname;

	}

	// view all genres
	@GetMapping("/viewalllistener")
	public @ResponseBody String viewAllListener(){
		List<Listener> myListnerList = myListenerRepository.findAll();
		if(myListnerList==null || myListnerList.size()==0){
			return "No listeners in database";
		}
		String myListenersOutput = myListnerList.stream()
		.map(Listener::getIDAndName)
		.collect(Collectors.joining("<br>"));

		return myListenersOutput;
	}


}