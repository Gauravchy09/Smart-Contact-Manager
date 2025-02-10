package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if(authentication == null){
            return;
        }
        System.out.println("Adding logged in user information to the model");
        String name = Helper.getEmailOfLoggedinUser(authentication);
        logger.info("user logged in: {}", name);
        User user = userService.getUSerByEmail(name);
        if (user == null) {
            model.addAttribute("loggedInUser", null);
        } else {
            System.out.println(user);
            System.out.println(user.getUsername());
            System.out.println(user.getEmail());

            model.addAttribute("loggedInUser", user);
        }

    }
}
