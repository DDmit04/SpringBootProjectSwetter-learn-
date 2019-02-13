package com.example.sweater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CommentDto;
import com.example.sweater.repos.CommentRepo;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;

	public Page<CommentDto> commentList(Pageable pageable, Message commentedMaessage, User user) {
		return commentRepo.findBycommentedMessage(pageable, commentedMaessage, user);
	}
	
}
