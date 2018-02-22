/*****************************************************************************/
/*!
\file   GenreController.java
\author Loo Chien Ming
\par    email: chienming1990\@gmail
\par    Radio Station Project
\date   19/2/2018
\brief  
  This file contains 11 functions 
    
  Hours spent on this assignment: ~20hours 
  
  Functions include:
  
  1. formatSongDetail					- Helper function: Format song details for output
  2. formatSongDetails					- Helper function: Format songs' details for output
  3. getSongDetails						- Helper function: Get Genre info about a song 
  4. listStringToListIntegerUnique		- Helper function: Change a list of string to list of unique integer
  5. ErrorMessageFetchingGenre			- Helper function: Print error message when fetching genreid
  6. ErrorMessageFetchingGenreList		- Helper function: Print error message when fetching a list of genreid
  7. allSongInfo						- View all songs
  8. songInfo							- View song info 
  9. addNewSong							- Add a song
  10. deleteSong						- Delete a song
  11. updateSong						- Update a song; change its genre and name
  
  
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Song;
import com.example.demo.SongRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class SongController {
	@Autowired 
	private SongRepository mySongRepository;
	
	@Autowired
	private GenreRepository myGenreRepository;

	// helper function to format output for song info
	private String formatSongDetail(Song mySong, Genre genre){
		return "Song Name: " + mySong.getName() + "<br>" + 
				"Genre: " + genre.getName() + "<br>";
	}

	// helper function to format output for song info
	private String formatSongDetails(Song mySong, Map<Integer, String> genreStr){
		return "Song Name: " + mySong.getName() + "<br>" + 
				"Genre: " + genreStr.get(mySong.getGenreID()) + "<br>";
	}

	// helper function to get for song info
	private String getSongDetails(Song mySong){
		// get genre data from DB
		Genre mySongGenre = myGenreRepository.findById(mySong.getID());

		// if problem fetching genre from DB
		if(mySongGenre == null){
			return ErrorMessageFetchingGenre(mySong.getID());
		}
		//String songGenres = mygenrelist.stream()
		//.map(Genre::getName)
		//.collect(Collectors.joining(", "));

		return formatSongDetail(mySong, mySongGenre);
	}
	private List<Integer> listStringToListIntegerUnique(List<String> genreIdStrList){
		List<Integer> genreIdIntList = new ArrayList<>();
		for (String genreIdStr : genreIdStrList) {
			Integer genreId = Integer.parseInt(genreIdStr);
			if(!genreIdIntList.contains(genreId)){
				genreIdIntList.add(genreId);
			}
		}
		return genreIdIntList;
	}
	private String ErrorMessageFetchingGenre(Integer genreId){
		String output = "GenreID: " + genreId;

		System.out.println("Error fetching genre from DB");
		System.out.println(output);

		return "Error fetching genre from DB<br>" + output;
	}

	private String ErrorMessageFetchingGenreList(List<Integer> genreIdIntList){
		System.out.println("Error fetching genrelist from DB");
		genreIdIntList.stream()
		.map(genid -> "GenreID: " + Integer.toString(genid))
		.forEach(System.out::println);

		String output = genreIdIntList.stream()
		.map(genid -> "GenreID: " + Integer.toString(genid))
		.collect(Collectors.joining("<br>"));

		return "Error fetching genre from DB<br>" + output;
		
	}

	@GetMapping("/viewallsong") 
	public @ResponseBody String allSongInfo() {
		// this requires multiple calls to DB?
		/*
		String output = mySongList.stream()
		.map(ele -> this.getSongDetails(ele))
		.collect(Collectors.joining("<br>"));
		*/

		// get song data from DB
		List<Song> mySongList = mySongRepository.findAll();
	
		// if no songs found
		if(mySongList==null || mySongList.size()==0){
			return "No song in database";
		}

		// prepare to get all genreid from songs
		List<Integer> genreIdIntList = new ArrayList<>();
		for (Song mySong : mySongList) {
			Integer songGenreId = mySong.getGenreID();
			
			if(!genreIdIntList.contains(songGenreId)){
				genreIdIntList.add(songGenreId);
			}
			
		}

		// get genre data from DB
		List<Genre> mygenrelist = myGenreRepository.findByIdIn(genreIdIntList);

		if(mygenrelist.size() != genreIdIntList.size()){
			return ErrorMessageFetchingGenreList(genreIdIntList);
		}

		// populate the genrelist with its id and name
		Map<Integer, String> myGenreData = new HashMap<Integer, String>();
		for (Genre genre : mygenrelist) {
			myGenreData.put(genre.getID(), genre.getName());
		}

		// format the output data
		String output = "";
		for (Song mySong : mySongList) {
			output += formatSongDetails(mySong, myGenreData);
		}
		return output;
	}
	@GetMapping("/songinfo") 
	public @ResponseBody String songInfo(@RequestParam Integer id) {
		Song mySong = mySongRepository.findById(id);
		if(mySong == null){
			System.out.println("Song ID: " + id + " not found");
			return "Song ID: " + id + " not found";
		}else{
			String output = this.getSongDetails(mySong);
			return output;
		}
	}

	@GetMapping("/addsong") 
	public @ResponseBody String addNewSong (@RequestParam String name, @RequestParam Integer genreid) {
		Song mysong = mySongRepository.findByNameIgnoreCase(name);

		// get all genre data
		Genre myGenre = myGenreRepository.findById(genreid);

		// if problem fetching genre from DB
		if(myGenre == null){
			return ErrorMessageFetchingGenre(genreid);
		}

		//check if data already exist
		if(mysong == null){
			Song newsong = new Song();
			newsong.setName(name);
			newsong.setGenreID(genreid);
			mySongRepository.save(newsong);
			return "Song " + name + " has been saved";
		}else{
			System.out.println("Song " + name + " is already in the database");
			return "Song " + name + " is already in the database";
		}
	}

	@GetMapping("/deletesong")
	public @ResponseBody String deleteSong (@RequestParam Integer id){
		Long result = mySongRepository.deleteById(id);
		System.out.println("Song ID:" + id + " deleted " + result);

		// check from query whether it is succesful
		if(result == 1){
			return "Song ID:" + id + " deleted";
		}else{
			return "Song ID:" + id + " failed to delete/not found in the database";
		}

		// WIP: Need to delete genre from other DB
	}

	// update song details
	@GetMapping("/updatesong")
	public @ResponseBody String updateSong (@RequestParam Integer id, @RequestParam String newname, @RequestParam Integer newgenreid){
		Song mySong = mySongRepository.findById(id);

		// check from query whether it is successful
		if(mySong == null){
			return "Song ID: " + id + " doesn't exist";
		}

		// get genre data
		Genre newGenre = myGenreRepository.findById(newgenreid);

		// if problem fetching genre from DB
		if(newGenre == null){
			return ErrorMessageFetchingGenre(newgenreid);
		}

		Integer oldGenreId = myGenreRepository.findById(mySong.getGenreID()).getID();
		String oldname = mySong.getName();

		mySong.setName(newname);
		mySong.setGenreID(newgenreid);
		mySongRepository.save(mySong);

		return "Song ID: " + id + " changed from <br>" + 
		oldname + "<br>" +
		oldGenreId + "<br>" +
		" to " + "<br>" +
		newname + "<br>" +
		newgenreid + "<br>";
	}

}