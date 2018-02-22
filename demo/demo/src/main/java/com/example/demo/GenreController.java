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
  
  1. addNewGenre			- Adds new genre into DB
  2. deleteGenre			- Delete genre from DB
  3. updateGenre			- Update a genre 
  4. viewAllGenre			- List all genres
  
  
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

import com.example.demo.Genre;
import com.example.demo.GenreRepository;
import com.example.demo.SongRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class GenreController {
	@Autowired // makes it a singleton
	private GenreRepository myGenreRepository;

	@Autowired // makes it a singleton
	private SongRepository mySongRepository;

	@Autowired // makes it a singleton
	private ListenerGenreRepository myListenerGenreRepository;

	@GetMapping("/addgenre") // Map ONLY GET Requests
	public @ResponseBody String addNewGenre (@RequestParam String name) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		Genre mygenre = myGenreRepository.findByNameIgnoreCase(name);

		//check if data already exist
		if(mygenre == null){
			Genre newgenre = new Genre();
			newgenre.setName(name);
			myGenreRepository.save(newgenre);
			return "Genre " + name + " has been saved";
		}else{
			System.out.println("Genre " + name + " is already in the database");
			return "Genre " + name + " is already in the database";
		}
	}

	@GetMapping("/deletegenre")
	public @ResponseBody String deleteGenre (@RequestParam Integer id){
		Long result = myGenreRepository.deleteById(id);
		System.out.println("Genre ID:" + id + " deleted " + result);

		// check from query whether it is succesful
		if(result == 0){
			return "Genre ID:" + id + " failed to delete/not found in the database";
		}

		// WIP: Need to delete genre from other DB
		List<Song> mySongList = mySongRepository.findByGenreid(id);
		for(Song mySong: mySongList){
			mySong.setGenreID(0);
			mySongRepository.save(mySong);
			System.out.println("Song ID: " + mySong.getID() + " Name:" + mySong.getName() + " change to genreid 0");
		}

		// TODO: format priority after deletion?
		List<ListenerGenre> myListenerGenreList = myListenerGenreRepository.findByGenreid(id);
		for(ListenerGenre myListenerGenre: myListenerGenreList){
			myListenerGenre.setGenreID(0);
			myListenerGenreRepository.save(myListenerGenre);
		}
		
		return "Genre ID:" + id + " deleted";
	}

	// update genre name
	@GetMapping("/updategenre")
	public @ResponseBody String updateGenre (@RequestParam Integer id, @RequestParam String newname){
		Genre mygenre = myGenreRepository.findById(id);

		// check from query whether it is successful
		if(mygenre == null){
			System.out.println("Genre ID: " + id + " doesn't exist");
			return "Genre ID: " + id + " doesn't exist";
		}

		// get old data
		String oldname = mygenre.getName();

		// update to new data
		mygenre.setName(newname);
		myGenreRepository.save(mygenre);
		System.out.println("Genre ID: " + id + " changed from " + oldname + " to " + newname);
		return "Genre ID: " + id + " changed from " + oldname + " to " + newname;

	}

	// view all genres
	@GetMapping("/viewallgenre")
	public @ResponseBody String viewAllGenre(){
		List<Genre> myGenreList = myGenreRepository.findAll();

		if(myGenreList==null || myGenreList.size() == 0){
			return "No genre in database";
		}
		String myGenresOutput = myGenreList.stream()
		.map(Genre::getIDAndName)
		.collect(Collectors.joining("<br>"));

		return myGenresOutput;
	}

}