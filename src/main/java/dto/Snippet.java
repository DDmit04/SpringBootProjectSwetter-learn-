//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.sweater.controller.ControllerUtills;
//import com.example.sweater.domain.User;

//import java.io.IOException;

//import java.util.Map;

//import javax.validation.Valid;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.sweater.controller.ControllerUtills;
//import com.example.sweater.domain.User;

//package dto;
//
//public class Snippet {
//	 @GetMapping(value={"profile" , "profile/email", "profile/password"})
//	    public String getProfile(Model model, @AuthenticationPrincipal User user) {
//	        model.addAttribute("username", user.getUsername());
//	        model.addAttribute("email", user.getEmail());
//	        return "profile";
//	    }
//	
//	    @PostMapping("profile/email")
//	    public String updateProfile(
//	            @AuthenticationPrincipal User user,
//	            @RequestParam String email,
//	            @Valid User usr,
//				BindingResult bindingResult,
//	            Model model
//	    ) {
//			if (bindingResult.hasErrors()) {
//				Map<String, String> errors = ControllerUtills.getErrors(bindingResult);
//				model.addAllAttributes(errors);
//				return "profile";
//			}
//	        userSevice.updateProfileEmail(user, email);
//	        if(!bindingResult.hasErrors()) {
//	        	model.addAttribute("message", "Your data seccesfuly apdated!");
//	    	}
//	        return "profile";
//	    }
//	
//	    @PostMapping("profile/password")
//	    public String updateProfilePassword(
//	            @AuthenticationPrincipal User user,
//	            @RequestParam("password2") String passwordConfirm,
//	            @RequestParam String password,
//	            @Valid User usr,
//				BindingResult bindingResult,
//	            Model model
//	    ) {
//	    	boolean isConfirEmpty = StringUtils.isEmpty(passwordConfirm);
//	    	//boolean 
//			if (isConfirEmpty) {
//				model.addAttribute("password2Error", "Password confirmation is empty!");
//			}
//			if (user.getPassword() != null && !usr.getPassword().equals(passwordConfirm)) {
//				model.addAttribute("passwordError", "Passwords are different!");
//			}
//			if (isConfirEmpty || bindingResult.hasErrors()) {
//				Map<String, String> errors = ControllerUtills.getErrors(bindingResult);
//				model.addAllAttributes(errors);
//				return "profile";
//			}
//	        userSevice.updateProfilePassword(user, password);
//	        if(!bindingResult.hasErrors()) {
//	        	model.addAttribute("message", "Your data seccesfuly apdated!");
//	    	}
//	        return "profile";
//	    }
//	}
//	
//}
//





//@GetMapping("profile")
//    public String getProfile(Model model, @AuthenticationPrincipal User user) {
//        model.addAttribute("username", user.getUsername());
//        model.addAttribute("email", user.getEmail());
//        return "profile";
//    }
//
//    @PostMapping(value = {"profile"})
//    public String updateProfile(
//    		@ModelAttribute
//    		@AuthenticationPrincipal User usr,
//            @RequestParam("password2") String passwordConfirm,
//            @RequestParam String password,
//            @RequestParam String email,
//            @Valid User user, 
//			BindingResult bindingResult,
//            Model model
//    ) {
//    	boolean isConfirEmpty = StringUtils.isEmpty(passwordConfirm);
//		if (isConfirEmpty) {
//			model.addAttribute("password2Error", "Password confirmation is empty!");
//		}
//		if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
//			model.addAttribute("passwordError", "Passwords are different!");
//		}
//		if (isConfirEmpty || bindingResult.hasErrors()) {
//			Map<String, String> errors = ControllerUtills.getErrors(bindingResult);
//			model.addAllAttributes(errors);
//			return "profile";
//		}
//        userSevice.updateProfile(usr, password, email);
//    	model.addAttribute("message", "Your data seccesfuly apdated!");
//        return "profile";
//		}
//    
//}




//@PostMapping("/user-messages/{user}")
//    @RequestMapping(value = "/user-messages/{user}", method = RequestMethod.PATCH)
//	public String updateMessage(@AuthenticationPrincipal User currentUser, 
//			   					@PathVariable User user, 
//			   					@RequestParam Message message, 
//			   					@RequestParam ("text") String text, 
//			   					@RequestParam("tag") String tag,
//			   					@RequestParam("file") MultipartFile file,
//			   					Model model) throws IOException {
//		if(message.getAuthor().equals(currentUser)) {
//			if(!StringUtils.isEmpty(text)) {
//				message.setText(text);
//			}
//			if(!StringUtils.isEmpty(tag)) {
//				message.setTag(tag);
//			}
//			fileService.saveFile(message, file);
//			messageRepo.save(message);
//		}
//		Set<Message> messages = user.getMessages();
//		model.addAttribute("messages", messages);
//		model.addAttribute("message", message);
//		model.addAttribute("isCurrentUser", currentUser.equals(user));
//		return "userMessages";
//	}
//	
////	@PostMapping("/user-messages/{user}/delete")
//    @RequestMapping(value = "/user-messages/{user}", method = RequestMethod.PUT)
//	public String deliteMessage(@AuthenticationPrincipal User currentUser, 
//			   					@PathVariable User user, 
//			   					@RequestParam Message message,
//			   					Model model) {
//		messageRepo.delete(message);
//		Set<Message> messages = user.getMessages();
//		model.addAttribute("messages", messages);
//		model.addAttribute("message", message);
//		model.addAttribute("isCurrentUser", currentUser.equals(user));
//		return "userMessages";
//	}




// @RequestMapping(value = "profile", method = RequestMethod.PUT)
//    public String updateProfileEmail(
//            @AuthenticationPrincipal User user,
//            @RequestParam String email,
//            @Valid User usr,
//			BindingResult bindingResult,
//            Model model
//            ) {
//		if (bindingResult.hasErrors()) {
//			Map<String, String> errors = ControllerUtills.getErrors(bindingResult);
//			model.addAllAttributes(errors);
//			return "profile";
//		}
//		if(email.equals(user.getEmail())) {
//			model.addAttribute("emailError", "Email is not changed!");
//			return "profile";
//		}
//        userSevice.updateProfileEmail(user, email);
//        model.addAttribute("message", "Your data seccesfuly apdated!");
//        return "profile";
//    }
//
//    @RequestMapping(value = "profile", method = RequestMethod.PATCH)
//    public String updateProfilePassword(
//            @AuthenticationPrincipal User user,
//            @RequestParam String currentPassword,
//            @RequestParam("password2") String passwordConfirm,
//            @RequestParam String password,
//            @Valid User usr,
//			BindingResult bindingResult,
//            Model model
//    ) {
//    	boolean isConfirEmpty = StringUtils.isEmpty(passwordConfirm);
//    	boolean isMainPasswordEmpty = StringUtils.isEmpty(currentPassword);
//    	boolean isMainPasswordCorrect = passwordEncoder.matches(currentPassword, user.getPassword());
//    	if (isMainPasswordEmpty) {
//			model.addAttribute("currentPasswordError", "Current password can not be empty!");
//		}
//		if (isConfirEmpty) {
//			model.addAttribute("password2Error", "Password confirmation can not be empty!");
//		}
//		if (user.getPassword() != null && !usr.getPassword().equals(passwordConfirm)) {
//			model.addAttribute("passwordError", "Passwords are different!");
//		}
//		if(!isMainPasswordCorrect) {
//			model.addAttribute("currentPasswordError", "wrong password!");
//		}
//		if (isConfirEmpty || bindingResult.hasErrors() || !isMainPasswordCorrect || isMainPasswordEmpty) {
//			Map<String, String> errors = ControllerUtills.getErrors(bindingResult);
//			model.addAllAttributes(errors);
//			return "profile";
//		}
//        userSevice.updateProfilePassword(user, password);    
//        model.addAttribute("message", "Your data seccesfuly apdated!");
//        return "profile";
//    }
//}