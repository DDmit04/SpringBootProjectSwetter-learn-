package com.example.sweater.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import com.example.sweater.domain.Comment;

public interface CommentRepo extends CrudRepository<Comment, Long> {
	
}
