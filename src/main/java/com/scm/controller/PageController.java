package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "Gaurav Choudhary");
        model.addAttribute("city", "Darbhanga");
        System.out.println("home page is loading...");
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        System.out.println( "about page is loading..." );
        return "about";
    }
    
    @GetMapping("/service")
    public String service(){
        System.out.println( "service page is loading..." );
        return "service";
    }

    @GetMapping("/contact")
    public String contact(){
        System.out.println( "contact page is loading..." );
        return "contact";
    }

    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String login(){
        System.out.println( "login page is loading..." );
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        System.out.println( "register page is loading..." );
        // sending a blank data to the form
        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);
        return "register";
    }


    // processing register
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,HttpSession session){
        System.out.println("Processing form");
        // fetch data from form
        System.out.println(userForm);
        // validate form data
        if(rBindingResult.hasErrors()){
            return "register";
        }

        // save to db
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("null");
        user.setEnabled(false);
        userService.saveUser(user);
        System.out.println("user saved");

        // message "rgistered successfully"
        Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();
        
        session.setAttribute("message",message);

        // redirect to login page
        return "redirect:/register";

    }
}
