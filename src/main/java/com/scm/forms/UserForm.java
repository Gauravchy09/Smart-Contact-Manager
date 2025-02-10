package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 Character is required")
    private String name;
    @Email(message = "Invalid Email Address")   
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min =  8, message = "Min 8 Character is required")
    private String password;
    @Size(max =  1000)
    private String about;
    @Size(min=10 , max = 10, message = "Invalid Phone Number")  
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}
