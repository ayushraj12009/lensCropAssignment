package com.LensCorp.LensCorp.Services;


import com.LensCorp.LensCorp.Model.User;
import com.LensCorp.LensCorp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }
}