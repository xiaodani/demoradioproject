package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Service
public interface ListenerRepository extends CrudRepository<Listener, Long> {

    List<Listener> findAll();

    Listener findByNameIgnoreCase(String name);

    Listener findById(Integer id);

    List<Listener> findByIdIn(List<Integer> id);

    @Modifying
    @Transactional(rollbackFor = Exception.class) 
    Long deleteById(Integer name);

}