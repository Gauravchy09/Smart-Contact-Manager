package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.User;
import com.scm.repositories.UserRepo;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token){
        System.out.println("email token");
        User user = userRepo.findByemailToken(token).orElse(null);
        if(user != null) {
            // user fetched successfully
            if(user.getEmailToken().equals(token) ){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                return "success_page";
            }
            return "error_page";
        }
        return "Something went wrong, please try again";
    }
}
