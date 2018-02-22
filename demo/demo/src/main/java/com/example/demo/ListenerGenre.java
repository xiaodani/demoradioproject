package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "listenergenre")
public class ListenerGenre {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer genreid;
	
	private Integer priority;

    public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
    }
    
    public Integer getGenreID() {
		return genreid;
	}

	public void setGenreID(Integer genreid) {
		this.genreid = genreid;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
