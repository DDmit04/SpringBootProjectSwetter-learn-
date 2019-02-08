package com.example.sweater.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.Message;

public interface CommentRepo extends CrudRepository<Comment, Long> {

	List<Comment> findBycommentedMaessage(Message commentedMaessage);
	
}
