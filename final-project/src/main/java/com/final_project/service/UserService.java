package com.final_project.service;

import com.final_project.dto.UserCreationRequest;
import com.final_project.dto.UserUpdateRequest;
import com.final_project.entity.User;
import com.final_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(()->
                new RuntimeException("User not found"));
    }

    public User updateUser(String userID, UserUpdateRequest request){
        User user = userRepository.findById(userID).orElseThrow(()->
                new RuntimeException("User not found"));
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        return userRepository.save(user);
    }


    public void deleteUser(String userID){
        userRepository.deleteById(userID);
    }
}
