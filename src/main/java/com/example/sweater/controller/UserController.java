package com.example.sweater.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.service.UserSevice;

@Controller
@RequestMapping("/user")
public class UserController {
	
    @Autowired
    private UserSevice userSevice;
    
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
    public String userEditForm(
    		@PathVariable User user, 
    		Model model) {
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
    public String getProfile(
    		@AuthenticationPrincipal User user,
    		Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }
    
    @GetMapping("/changeEmail")
    public String getResetMail() {
       return "changeEmail";
    }
    
    @PostMapping("/changeEmail")
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
}