package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupView(User user){
        return "signup";
    }

    @PostMapping("/signup")
    public String register(User user, Model model){
        String signupError = null;
        if(userService.isUsernameAvailable(user.getUsername())){
            int a = userService.creatUser(user);
            if(a<0){
                signupError = "Sign up error. Please try again.";
            }
            if (signupError == null) {
                model.addAttribute("signupSuccess", true);
            } else {
                model.addAttribute("signupError", signupError);
            }
        }else{
            signupError="this username already exists";
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }
}
