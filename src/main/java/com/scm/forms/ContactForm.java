package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "name is required")
    private String name;
    @Email(message = "invalid email address")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$",message = "invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;
    private MultipartFile contactImage;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedInLink;
    private String picture;
}
