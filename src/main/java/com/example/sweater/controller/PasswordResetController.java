package com.example.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import com.example.sweater.service.UserSevice;

@Controller
public class PasswordResetController {
	
	@Autowired
    private UserSevice userSevice;
	
	@Autowired
	private UserRepo userRepo;

	@GetMapping("/passwordRecoverSt2/{username}")
	public String passwordRecover2(@PathVariable String username) {
		return "resetPasswordSt2";
	}
	
	@PostMapping("/passwordRecoverSt2/{username}")
	public String recoverPassword2(@PathVariable String username, 
								   @RequestParam String key,
								   @RequestParam("password2") String passwordConfirm, 
								   @RequestParam String password,
								   @RequestParam String button, 
								   Model model) {
		User user = userRepo.findByUsername(username);
		if (!user.getPasswordResetCode().equals(key)) {
			model.addAttribute("keyError", "wrong key!");
		}
		if (StringUtils.isEmpty(password)) {
			model.addAttribute("PasswordError", "password can not be empty!");
		}
		if (!passwordConfirm.equals(password)) {
			model.addAttribute("passwordError", "Passwords are different!");
		}
		if (StringUtils.isEmpty(passwordConfirm)) {
			model.addAttribute("password2Error", "Password confirmation can not be empty!");
		}
		if (!user.getPasswordResetCode().equals(key) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(passwordConfirm) || !passwordConfirm.equals(password)
				|| !userSevice.activatePassword(user.getPasswordResetCode())) {
			return "resetPasswordSt2";
		}
		userSevice.updateProfilePassword(user, password);
		userSevice.activatePassword(key);
		return "login";
	}
	
	@GetMapping("/passwordRecover")
	public String passwordRecover() {
		return "resetPassword";
	}
	
	@PostMapping("/passwordRecover")
	public String recoverPassword(@RequestParam String username, 
								  Model model) {
		if (StringUtils.isEmpty(username)) {
			model.addAttribute("usernameError", "username can not be emty!");
			return "resetPassword";
		}
		User user = userRepo.findByUsername(username);
		if(user == null) {
			model.addAttribute("usernameError", "User not exist!");
			return "resetPassword";
		}
		if(user.getActivationCode() != null) {
			model.addAttribute("usernameError", "You have not activated your account!");
			return "resetPassword";
		}
		userSevice.sendMessageToChange(user, "password");
		return "redirect:/passwordRecoverSt2/" + username;
	}
	
}
