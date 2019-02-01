package com.example.sweater.repos;

import org.springframework.stereotype.Repository; 
import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {

    Page<Message> findAll(Pageable pageable);
    Page<Message> findByTag(String tag, Pageable pageable);
    Page<Message> findByAuthor(User author, Pageable pageable);
    
}
