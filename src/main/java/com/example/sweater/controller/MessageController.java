package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.GestMessageDto;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.FileService;
import com.example.sweater.service.MessageService;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

@Controller
public class MessageController {
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FileService fileService;

	@GetMapping(value = {"/home", "/"})
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/allMessages")
	public String main(@AuthenticationPrincipal User user, 
					   @RequestParam(required = false, defaultValue = "") String filter, 
					   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
			           Model model) {
		Page<MessageDto> page = messageService.messageList(pageable, filter, user);
		model.addAttribute("pages", page);
		model.addAttribute("url", "/allMessages");
		model.addAttribute("filter", filter);
		return "allMessages";
	}
	
	@GetMapping("/allMessagesGest")
	public String mainGest(@RequestParam(required = false, defaultValue = "") String filter, 
					   	   @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
			               Model model) {
		Page<GestMessageDto> pageGest = messageService.messageListForGest(pageable, filter);
		model.addAttribute("pages", pageGest);
		model.addAttribute("url", "/allMessagesGest");
		model.addAttribute("filter", filter);
		return "allMessages";
	}

	@PostMapping("/allMessages")
	public String add(@AuthenticationPrincipal User user, 
					  @PageableDefault (sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
					  @RequestParam(required = false, defaultValue = "") String filter, 
					  @RequestParam String button,
					  @Valid Message message, 
					  BindingResult bindingResult,
			          Model model, 
			          @RequestParam("file") MultipartFile file) throws IOException {
		if(user.getActivationCode() != null) {
			model.addAttribute("activationCodeError", "You have not activated account, pleace check your Email");
			return "allMessages";
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
			message.setTag("noTag");
		}
		model.addAttribute("filter", filter);
		Page<MessageDto> page = messageService.messageList(pageable, filter, user);
		model.addAttribute("pages", page);
		model.addAttribute("url", "/allMessages");
		return bindingResult.hasErrors() ? "/allMessages" : "redirect:/allMessages";
	}
	
	@GetMapping("/messages/{message}/like")
	public String like(
					   @AuthenticationPrincipal User currentUser, 
					   @PathVariable Message message,
					   RedirectAttributes redirectAttributes,
					   @RequestHeader(required = false) String referer) {
		Set<User> likes = message.getLikes();
		if(likes.contains(currentUser)) {
			likes.remove(currentUser);
		} else {
			likes.add(currentUser);
		}
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
			.entrySet()
			.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
		return "redirect:" + components.getPath();
	}
}