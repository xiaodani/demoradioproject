package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Song;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> findAll();
    
    Song findByNameIgnoreCase(String name);

    Song findById(Integer id);

    List<Song> findByGenreid(Integer id);

    @Modifying
    @Transactional(rollbackFor = Exception.class) 
    Long deleteByName(String name);

    @Modifying
    @Transactional(rollbackFor = Exception.class) 
    Long deleteById(Integer id);

    

}