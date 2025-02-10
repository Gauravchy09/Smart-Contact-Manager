package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;


@Service
public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact>getAll();

    Contact getById(String id);

    void delete(String id);

    Page<Contact> searchByName(String nameKeyword,int size,int page,String sortBy,String direction,User user);

    Page<Contact> searchByPhone(String phoneKeyword,int size,int page,String sortBy,String direction, User user);

    Page<Contact> searchByemail(String emailKeyword,int size,int page,String sortBy,String direction, User user);


    List<Contact>getByUserId(String userId);
    Page<Contact>getByUser(User user, int page, int size, String sort, String direction);

   
}
