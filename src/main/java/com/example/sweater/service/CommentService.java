package com.example.sweater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.Message;
import com.example.sweater.repos.CommentRepo;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;

	public Iterable<Comment> commentList(Message commentedMaessage) {
		return commentRepo.findBycommentedMaessage(commentedMaessage);
	}
	
}
