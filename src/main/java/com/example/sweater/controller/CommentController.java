package com.example.sweater.controller;

import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CommentDto;
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

	@GetMapping("/{message}")
	public String comments(
			   @PathVariable Message message,
			   @AuthenticationPrincipal User user, 
			   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
			   @ModelAttribute("redirectMessageName") String redirectMessage,
 			   @ModelAttribute("redirectMessageTypeName") String redirectMessageType,
	           Model model) {
		Page<MessageDto> commentedMessage = messageRepo.findOne(pageable, user, message.getId()); 
		Page<CommentDto> commentsPage = commentService.commentList(pageable, message, user);
		model.addAttribute("redirectMessage", redirectMessage);
		model.addAttribute("redirectMessageType", redirectMessageType);
		model.addAttribute("messagesPage", commentedMessage);
		model.addAttribute("commentCount", message.getComments().size());
		model.addAttribute("comments", commentsPage);
		return "comments";
	}
	
	@PostMapping("/{message}")
	public String addComment(@PathVariable Message message, 
							 @AuthenticationPrincipal User user, 
							 @Valid Comment comment, 
							 BindingResult bindingResult,
							 @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
					         Model model) {
		Page<MessageDto> commentedMessage = messageRepo.findOne(pageable, user, message.getId()); 
		model.addAttribute("messagesPage", commentedMessage);
		Page<CommentDto> commentsPage = commentService.commentList(pageable, message, user);
		model.addAttribute("comments", commentsPage);
		model.addAttribute("commentCount", message.getComments().size());
		if(bindingResult.hasErrors()) {
			Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
			model.addAllAttributes(errorsMap);
			return "comments";
		} else {
			comment.setCommentedMessage(message);
			comment.setCommentAuthor(user);
			commentRepo.save(comment);
		}
		return "redirect:/comments/" + message.getId();
	}
	
	@GetMapping("/{message}/edit/{comment}")
	public String editComments(
			   @PathVariable Message message,
			   @PathVariable Comment comment,
			   @AuthenticationPrincipal User user, 
			   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
	           Model model) {
		Page<MessageDto> commentedMessage = messageRepo.findOne(pageable, user, message.getId()); 
		Page<CommentDto> commentsPage = commentService.commentList(pageable, message, user);
		model.addAttribute("comment", comment);
		model.addAttribute("message", message);
		model.addAttribute("messagesPage", commentedMessage);
		model.addAttribute("commentCount", message.getComments().size());
		model.addAttribute("comments", commentsPage);
		return "comments";
	}
	
	@PostMapping("/{message}/edit/{comment}")
	public String editComments(@PathVariable Message message, 
							   @PathVariable Comment comment,
							   @RequestParam String text,
							   @RequestParam (required = false) String button,
			 				   @AuthenticationPrincipal User user, 
			 				   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
			 				   @ModelAttribute("redirectMessageName") String redirectMessage,
			 				   @ModelAttribute("redirectMessageTypeName") String redirectMessageType,
			   				   RedirectAttributes redirectAttributes,
			 				   Model model) {
		model.addAttribute("comment", comment);
		model.addAttribute("message", message);
		Page<MessageDto> commentedMessage = messageRepo.findOne(pageable, user, message.getId()); 
		Page<CommentDto> commentsPage = commentService.commentList(pageable, message, user);
		model.addAttribute("messagesPage", commentedMessage);
		model.addAttribute("commentCount", message.getComments().size());
		model.addAttribute("comments", commentsPage);
		if(button.equals("edit")) {
			if(StringUtils.isEmpty(text) || text.equals(" ")) {
				model.addAttribute("textError", "comment can not be emty!");
				return "comments";
			} else {
				comment.setText(text);
				redirectMessage = "Comment edited!";
				redirectMessageType = "success";
				redirectAttributes.addFlashAttribute("redirectMessageTypeName", redirectMessageType);
				redirectAttributes.addFlashAttribute("redirectMessageName", redirectMessage);
			}
		}
		if(button.equals("delete")) {
			commentRepo.delete(comment);
			message.getComments().remove(comment);
			redirectMessage = "Comment deleted!";
			redirectMessageType = "success";
			redirectAttributes.addFlashAttribute("redirectMessageTypeName", redirectMessageType);
			redirectAttributes.addFlashAttribute("redirectMessageName", redirectMessage);
		}
		return "redirect:/comments/" + message.getId();
	}
	
	@GetMapping("/{comment}/plus")
	public String plus(
					   @AuthenticationPrincipal User user, 
					   @PathVariable Comment comment,
					   RedirectAttributes redirectAttributes,
					   @RequestHeader(required = false) String referer) {
		Set<User> pluses = comment.getCommentPluses();
		if(pluses.contains(user)) {
			pluses.remove(user);
		} else {
			pluses.add(user);
		}
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
			.entrySet()
			.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
		return "redirect:" + components.getPath();
	}
	
}
