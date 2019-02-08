package com.example.sweater.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.CommentRepo;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.CommentService;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private MessageRepo messageRepo;

	@GetMapping("{message}")
	public String comments(
			   @PathVariable Message message,
			   @AuthenticationPrincipal User user, 
			   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
	           Model model) {
		Page<MessageDto> messageForComment = messageRepo.findOne(pageable, user, message.getId()); 
		Iterable<Comment> page = commentService.commentList(message);
		model.addAttribute("pages", messageForComment);
		model.addAttribute("comments", page);
		model.addAttribute("url", message);
		return "comments";
	}
	
	@PostMapping("{message}")
	public String addComment(@PathVariable Message message, 
							 @AuthenticationPrincipal User user, 
							 @Valid Comment comment, 
							 BindingResult bindingResult,
							 @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
					         Model model) {
		comment.setCommentedMaessage(message);
		commentRepo.save(comment);
		Page<MessageDto> messageForComment = messageRepo.findOne(pageable, user, message.getId()); 
		Iterable<Comment> page = commentService.commentList(message);
		model.addAttribute("pages", messageForComment);
		model.addAttribute("comment", comment);
		model.addAttribute("comments", page);
		model.addAttribute("url", message);
		return "comments";
	}
	
}