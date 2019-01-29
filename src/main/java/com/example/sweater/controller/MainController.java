package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

@Controller
@PropertySource("application.properties")
public class MainController {
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private FileService fileService;

	@GetMapping(value = {"/home", "/"})
	public String greeting(Map<String, Object> model) {
		return "greeting";
	}

	@GetMapping("/main")
	public String main(@RequestParam(required = false, defaultValue = "") String filter, 
			           Model model) {
		Iterable<Message> messages = messageRepo.findAll();
		if (filter != null && !filter.isEmpty()) {
			messages = messageRepo.findByTag(filter);
		} else {
			messages = messageRepo.findAll();
		}
		model.addAttribute("messages", messages);
		model.addAttribute("filter", filter);
		return "main";
	}

	@PostMapping("/main")
	public String add(@AuthenticationPrincipal User user, 
					  @Valid Message message, 
					  BindingResult bindingResult,
			          Model model, 
			          @RequestParam("file") MultipartFile file) throws IOException {
		if(user.getActivationCode() != null) {
			model.addAttribute("activationCodeError", "You have not activated account, pleace check your Email");
			return "/main";
		}
		message.setAuthor(user);
		if (bindingResult.hasErrors()) {
			Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
			model.addAllAttributes(errorsMap);
			model.addAttribute("message", message);
		} else {
			fileService.saveFile(message, file);
			model.addAttribute("message", null);
			messageRepo.save(message);
		} 
		if(StringUtils.isEmpty(message.getTag())) {
			message.setTag("no tag");
		}
		Iterable<Message> messages = messageRepo.findAll();
		model.addAttribute("messages", messages);
		return bindingResult.hasErrors() ? "/main" : "redirect:/main";
	}
}