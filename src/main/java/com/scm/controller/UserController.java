package com.scm.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    // private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    // user dashboard page
    @GetMapping("/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    // user profile page
    @GetMapping("/profile")
    public String userProfile(Model model,Authentication authentication) {
        String name = Helper.getEmailOfLoggedinUser(authentication);

        User user = userService.getUSerByEmail(name);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        
        model.addAttribute("loggedInUser", user);
        model.addAttribute(user);

        // logger.info("User logged in: {}",name);
        return "user/profile";
    }

    

    
    
    // user add contact page
    
    // user view contact page

    // user edit contact page

    // user delete contact page

    // user search contact page

    // user logout page


}
