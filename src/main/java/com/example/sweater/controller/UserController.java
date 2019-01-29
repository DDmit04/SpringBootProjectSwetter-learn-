package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.repos.UserRepo;
import com.example.sweater.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
    @Autowired
    private UserSevice userSevice;
    
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
	private MessageRepo messageRepo;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userSevice.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userSevice.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }
    
    @GetMapping("changeEmail")
    public String getResetMail(Model model) {
       return "changeEmail";
    }
    
    @PostMapping("changeEmail")
    public String resetEmail(
    		@AuthenticationPrincipal User user,
         	@RequestParam String email,
         	@RequestParam String key,
         	@RequestParam String button,
            Model model) {
    	
    	if(button.equals("sendKey")) {
    		if(user.getActivationCode() != null) {
        		model.addAttribute("keyError", "You already have a key or have not activated your account!");
    			return "changeEmail";
        	}
    		userSevice.sendMessageToChange(user, "email");
    		return "redirect:/user/changeEmail";
    	}
    	boolean isWrongKey = StringUtils.isEmpty(key) || !user.getActivationCode().equals(key);
    	if(isWrongKey) {
    		model.addAttribute("keyError", "Wrong key!");
    	}
    	if(email.equals(user.getEmail())) {
			model.addAttribute("emailError", "This is current email!");
		}
    	if(StringUtils.isEmpty(email)) {
			model.addAttribute("emailError", "Email can not be empty!");
		}
    	if(isWrongKey || StringUtils.isEmpty(email) || email.equals(user.getEmail())) {
			return "changeEmail";
    	}
    	userSevice.updateProfileEmail(user, email);
        model.addAttribute("message", "Your data seccesfuly apdated!");
    	return "changeEmail";
    }

    @PostMapping("profile")
    public String updateProfilePassword(
            @AuthenticationPrincipal User user,
            @RequestParam String currentPassword,
            @RequestParam("password2") String passwordConfirm,
            @RequestParam String password,
            @Valid User usr,
			BindingResult bindingResult,
            Model model
    ) {
    	boolean isConfirEmpty = StringUtils.isEmpty(passwordConfirm);
    	boolean isMainPasswordEmpty = StringUtils.isEmpty(currentPassword);
    	boolean isMainPasswordCorrect = passwordEncoder.matches(currentPassword, usr.getPassword());
    	if (isMainPasswordEmpty) {
			model.addAttribute("currentPasswordError", "Current password can not be empty!");
		}
		if (isConfirEmpty) {
			model.addAttribute("password2Error", "Password confirmation can not be empty!");
		}
		if (user.getPassword() != null && !usr.getPassword().equals(passwordConfirm)) {
			model.addAttribute("passwordError", "Passwords are different!");
		}
		if(isMainPasswordCorrect) {
			model.addAttribute("currentPasswordError", "wrong password!");
		}
		if (isConfirEmpty || bindingResult.hasErrors() || isMainPasswordCorrect || isMainPasswordEmpty) {
			Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
			model.addAllAttributes(errors);
			return "profile";
		}
        userSevice.updateProfilePassword(user, password);    
        model.addAttribute("message", "Your data seccesfuly apdated!");
        return "profile";
    }
    
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
		if("subscriptions".equals(type)) {
			model.addAttribute("users", user.getSubscriptions());
		} else {
			model.addAttribute("users", user.getSubscribers());
		}
    	return "subscriptions";
    }
    
    @GetMapping("subMessages/{user}")
    public String subMessages(
    		@PathVariable User user,
    		Model model) {
		Iterable<Message> messagesRepo = messageRepo.findAll();
		Iterator<Message> iterator = messagesRepo.iterator();
		while (iterator.hasNext()) {
			Message message = iterator.next();
			if (!user.getSubscriptions().contains(message.getAuthor())) {
				iterator.remove();
			}
		}
		model.addAttribute("messages", messagesRepo);
    	return "subscriptionsMessages";
    }
    
}