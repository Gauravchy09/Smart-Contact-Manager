package com.scm.services.impl;

import java.util.List;
import java.util.Optional;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.EmailService;
import com.scm.services.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired(required = true)
    private PasswordEncoder passwordEncoder;

    @Autowired(required = true)
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());
        
        String emailtoken = UUID.randomUUID().toString();
        user.setEmailToken(emailtoken);
        
        User userSaved = userRepo.save(user);
        String emailLink = Helper.getLinkForEmailVerification(emailtoken);
        
        emailService.sendEmail(userSaved.getEmail(), "Verify Account : Smart Contact Manager", emailLink);
        
        return userSaved;

    }

    @Override
    public Optional<User> getUserById(String id) {
        return this.userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User user1 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // updation of user1
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAbout(user.getAbout());
        user1.setProfilePic(user.getProfilePic());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
        user1.setProviderUserId(user.getProviderUserId());

       User save = userRepo.save(user1);
       return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
       
        User user1 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        this.userRepo.delete(user1);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user1 = userRepo.findById(userId).orElse(null);
        return (user1 != null ? true : false );
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return (user != null ? true : false );
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    @Override
    public User getUSerByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
