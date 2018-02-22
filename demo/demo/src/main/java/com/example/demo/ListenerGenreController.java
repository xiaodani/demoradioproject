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
  
  1. GetListenerNameAndGenre			- Helper function: Gets listnername and genre from DB
  2. viewAllListenerGenre				- View all listener favourite genre

  
  
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

import com.example.demo.ListenerGenre;
import com.example.demo.ListenerGenreRepository;
import com.example.demo.Listener;
import com.example.demo.ListenerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class ListenerGenreController {
	@Autowired // makes it a singleton
	private ListenerGenreRepository myListenerGenreRepository;

	@Autowired
	private ListenerRepository myListenerRepository;

	@Autowired
	private GenreRepository myGenreRepository;

	private String GetListenerNameAndGenre(Integer listenerId, Integer genreid){
		Listener myListener = myListenerRepository.findById(listenerId);
		Genre myGenre = myGenreRepository.findById(genreid);

		if(myListener==null){
			return "Error: Failed to find listener id:" + listenerId;
		}
		if(myGenre==null){
			return "Error: Failed to find genre id:" + genreid;
		}

		return myListener.getName() + " " + myGenre.getName();
		
	}
	
	// view all
	@GetMapping("/viewalllistenergenre")
	public @ResponseBody String viewAllListenerGenre(){
	List<ListenerGenre> myListnerGenreList = myListenerGenreRepository.findAll();
		if(myListnerGenreList==null || myListnerGenreList.size()==0){
			return "No listeners favourites in database";
		}

		//TODO
		//improve DB calls
		String testoutput = myListnerGenreList.stream()
		.map(ele -> this.GetListenerNameAndGenre(ele.getID(), ele.getGenreID()) + " Priority" + ele.getPriority())
		.collect(Collectors.joining("<br>"));

		return testoutput;
	}
}