package com.example.sweater.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.UserSevice;

@Controller
@RequestMapping("/user")
public class SubscribsionsController {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private UserSevice userSevice;

	@GetMapping("subscribe/{user}")
	public String subscribe(
			@AuthenticationPrincipal User currentUser, 
			@PathVariable User user) {
		userSevice.subscribe(currentUser, user);
		return "redirect:/user-messages/" + user.getId();
	}

	@GetMapping("unsubscribe/{user}")
	public String unsubscribe(
			@AuthenticationPrincipal User currentUser, 
			@PathVariable User user) {
		userSevice.unSubscribe(currentUser, user);
		return "redirect:/user-messages/" + user.getId();
	}

	@GetMapping("{type}/{user}/list")
	public String userListType(
			@PathVariable String type, 
			@PathVariable User user, 
			Model model) {
		model.addAttribute("userChannel", user);
		model.addAttribute("type", type);
		if ("subscriptions".equals(type)) {
			model.addAttribute("users", user.getSubscriptions());
		} else {
			model.addAttribute("users", user.getSubscribers());
		}
		return "subscriptions";
	}

	@GetMapping("subMessages/{user}")
	public String subMessages(
			@PathVariable User user,
			@PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, 
			Model model) {
		Page<Message> page = messageRepo.findAll(pageable);
		Iterator<Message> iterator = page.iterator();
		while (iterator.hasNext()) {
			Message message = iterator.next();
			if (!user.getSubscriptions().contains(message.getAuthor())) {
				iterator.remove();
			}
		}
		model.addAttribute("url", user.getId());
		model.addAttribute("pages", page);
		return "subscriptionsMessages";
	}

}
