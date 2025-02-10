package com.scm.config;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import com.scm.services.impl.SecurityCustomDetailsService;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // TO DO: add more security config here.

    // user Create and login usign java code with in memory services

    // @Bean
    // public UserDetailsService userDetailsSerivce(){

    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()
    //     .username("admin123")
    //     .password("admin123")
    //     .roles("ADMIN", "USER")
    //     .build();
        
    //     UserDetails user2 = User
    //     .withUsername("user123")
    //     .password("user123")
    //     .build();

    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);
    //     return inMemoryUserDetailsManager;
    // }
    
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Autowired
    private SecurityCustomDetailsService userDetailsService;

    @Autowired
    private AuthFailureHandler authFailureHandler;


    @Bean
    public AuthenticationProvider authenticationProvider(){
     
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //User details service ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        //Configuration
        // httpSecurity.authorizeHttpRequests(authorize->{
        //     authorize.requestMatchers("/home","/register","/services").
        //     permitAll();
        // });

        // urls configure kia hai ki kaun se public range ka hai aur kon sa private
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //login page ka url
        // agar hame kuch bhi change karna hua to ham yaha aayenge: form login se related
        // httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/profile");
            // formLogin.successForwardUrl("/user/dashboard");
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.failureHandler(authFailureHandler);
        });

        httpSecurity.csrf(AbstractHttpConfigurer :: disable);
        
        httpSecurity.logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
        });

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }    

}
