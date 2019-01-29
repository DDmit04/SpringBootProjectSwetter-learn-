package com.example.sweater.repos;

import org.springframework.stereotype.Repository; 
import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);
    
}
