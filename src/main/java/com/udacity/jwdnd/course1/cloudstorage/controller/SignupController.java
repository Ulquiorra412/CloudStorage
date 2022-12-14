package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String view(@ModelAttribute("signupForm") User user, Model model, RedirectAttributes redirectAttributes) {
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("signupForm") User user, Model model,
                  RedirectAttributes redirectAttributes) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The user name already exists.";
        }

        if (signupError == null) {
            int rowAdded = userService.createUser(user);
            if (rowAdded < 0)
                signupError = "There was an error signing you up. Please try again.";
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            try {Thread.currentThread().sleep(1000);}
            catch (InterruptedException interruptedException) {};
            return "redirect:login";
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }
    }
}
