package com.example.sweater.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.FileService;

@Controller
@PropertySource("application.properties")
public class MessageEditController {
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/user-messages/{user}")
	public String userMessages(@AuthenticationPrincipal User currentUser, 
							   @PathVariable User user,
							   @RequestParam(required = false) Message message,
			   				   @ModelAttribute("redirectMessageName") String redirectMessage,
			   				   @ModelAttribute("redirectMessageTypeName") String redirectMessageType,
							   Model model) {
		Set<Message> messages = user.getMessages();
		model.addAttribute("messages", messages);
		model.addAttribute("message", message);
		model.addAttribute("userChannel", user);
		model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
		model.addAttribute("subscribersCount", user.getSubscribers().size());
		model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
		model.addAttribute("isCurrentUser", currentUser.equals(user));
		model.addAttribute("redirectMessage", redirectMessage);
		model.addAttribute("redirectMessageType", redirectMessageType);
		return "userMessages";
	}
	
	@PostMapping("/user-messages/{user}")
	public String updateMessage(@AuthenticationPrincipal User currentUser, 
			   					@PathVariable Long user, 
			   					@RequestParam (required = false) String button, 
			   					@RequestParam Message message, 
			   					@RequestParam String text, 
			   					@RequestParam String tag,
			   					@RequestParam MultipartFile file,
			   					@ModelAttribute("redirectMessageName") String redirectMessage,
			   					@ModelAttribute("redirectMessageTypeName") String redirectMessageType,
			   					RedirectAttributes redirectAttributes) throws IOException {
		if(button.equals("edit")) {
			if(message.getAuthor().equals(currentUser)) {
				if(!StringUtils.isEmpty(text)) {
					message.setText(text);
				} else {
					redirectMessage = "Edit error, Message can not be empty!";
					redirectMessageType = "danger";
					redirectAttributes.addFlashAttribute("redirectMessageTypeName", redirectMessageType);
					redirectAttributes.addFlashAttribute("redirectMessageName", redirectMessage);
					return "redirect:/user-messages/" + user;
				}
				if(!StringUtils.isEmpty(tag)) {
					message.setTag(tag);
				} else {
					message.setTag("noTag");
				}
				fileService.saveFile(message, file);
				messageRepo.save(message);
			}
		}
		if(button.equals("delete")) {
			messageRepo.delete(message);
			redirectMessage = "Message deleted!";
			redirectMessageType = "success";
			redirectAttributes.addFlashAttribute("redirectMessageTypeName", redirectMessageType);
			redirectAttributes.addFlashAttribute("redirectMessageName", redirectMessage);
		} 
		return "redirect:/user-messages/" + user;
	}

}
