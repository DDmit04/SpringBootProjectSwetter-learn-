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
import com.example.sweater.repos.CommentRepo;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private CommentRepo commentRepo;

	@GetMapping("{message}")
	public String comments(
			   @PathVariable Long message,
			   @AuthenticationPrincipal User user, 
			   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
	           Model model) {
		Iterable<Comment> page = commentRepo.findAll();
		model.addAttribute("comments", page);
		model.addAttribute("url", message);
		return "comments";
	}
	
//	@PostMapping("{message}/comments")
//	public String addComment(@PathVariable Message message, 
//							 @AuthenticationPrincipal User user, 
//							 @Valid Comment comment, 
//							 BindingResult bindingResult,
//					         Model model, 
//							 String text) {
//		comment.setCommentAuthor(user);
//		commentRepo.save(comment);
//		return "commentList";
//	}
	
}
