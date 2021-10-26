package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;


    public int creatUser(User user){
        //a random number generator
        SecureRandom random = new SecureRandom();
        //get encoded salt
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        int a = userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
        return a;
    }

    public boolean isUsernameAvailable(String username){

        return (userMapper.findByName(username)==null);
    }

    public User findByName(String username){
        return userMapper.findByName(username);
    }


}
