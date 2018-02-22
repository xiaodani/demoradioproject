package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "song")
public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	private String name;
	
	private Integer genreid;

    public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGenreID() {
		return genreid;
	}

	public void setGenreID(Integer genreid) {
		this.genreid = genreid;
	}

}
