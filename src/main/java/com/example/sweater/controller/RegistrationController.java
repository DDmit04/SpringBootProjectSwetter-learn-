package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import com.example.sweater.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@PropertySource("application.properties")
public class RegistrationController {

//	private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

	@Autowired
	private UserSevice userSevice;
	
	@Autowired
	private UserRepo userRepo;

//	@Autowired
//	private RestTemplate restTemplate;

	@Value("${recaptcha.secret}")
	private String secret;
	
	@GetMapping("/passwordRecoverSt2/{username}")
	public String passwordRecover2(@PathVariable String username) {
		return "resetPasswordSt2";
	}
	
	@PostMapping("/passwordRecover2/{username}")
	public String recoverPassword2(@PathVariable String username, 
								   @RequestParam String key,
								   @RequestParam("password2") String passwordConfirm, 
								   @RequestParam String password,
								   @RequestParam String button, 
								   Model model) {
		User user = userRepo.findByUsername(username);
		if (!user.getActivationCode().equals(key)) {
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
		if (!user.getActivationCode().equals(key) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(passwordConfirm) || !passwordConfirm.equals(password)) {
			return "resetPassword2";
		}
		userSevice.updateProfilePassword(user, password);
		userSevice.activateUser(user.getActivationCode());
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
		if (user.getActivationCode() != null) {
			model.addAttribute("usernameError", "You already have a key or have not activated your account!");
			return "resetPassword";
		}
		userSevice.sendMessageToChange(user, "password");
		return "redirect:/passwordRecover2/" + username;
	}
	

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(@RequestParam("password2") String passwordConfirm,
		    			  @Valid User user, 
		    			  BindingResult bindingResult,
		    			  Model model) {

//		String url = String.format(CAPTCHA_URL, secret, captchaResponse);
//		CaptchaResponceDto responce = restTemplate.postForObject(url, Collections.emptyList(),
//				CaptchaResponceDto.class);

//		if (!responce.isSuccess()) {
//			model.addAttribute("captchaError", "Fill captcha!");
//		}

		boolean isConfirEmpty = StringUtils.isEmpty(passwordConfirm);
		if (isConfirEmpty) {
			model.addAttribute("password2Error", "Password confirmation is empty!");
		}
		if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
			model.addAttribute("passwordError", "Passwords are different!");
		}
		if (isConfirEmpty || bindingResult.hasErrors()) {
			Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
			model.addAllAttributes(errors);
			return "registration";
		}
		String addUserResult = userSevice.addUser(user);
		if (addUserResult.equals("userError")) {
			model.addAttribute("usernameError", "User exists!");
			return "registration";
		}
		if (addUserResult.equals("emailError")) {
			model.addAttribute("emailError", "Email exists!");
			return "registration";
		}
		return "redirect:/login";
	}

	@GetMapping("/activate/{code}")
	public String activate(Model model, 
						   @PathVariable String code, 
						   HttpServletRequest request) throws ServletException {
		request.logout();
		boolean isActivated = userSevice.activateUser(code);
		if (isActivated) {
			model.addAttribute("messageType", "success");
			model.addAttribute("message", "User successfully activated");
		} else {
			model.addAttribute("messageType", "danger");
			model.addAttribute("message", "Activation code is not found!");
		}
		return "greeting";
	}
	
}
