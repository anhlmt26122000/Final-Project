package com.final_project.service;

import com.final_project.dto.request.UserCreationRequest;
import com.final_project.dto.request.UserUpdateRequest;
import com.final_project.entity.User;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
import com.final_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        user.setUsername(request.getUsername());
        //encrypt password
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(5);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

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
                new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public User updateUser(String userID, UserUpdateRequest request){
        User user = userRepository.findById(userID).orElseThrow(()->
                new AppException(ErrorCode.USER_NOT_EXISTED));

        //encrypt password
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(5);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        return userRepository.save(user);
    }


    public void deleteUser(String userID){
        userRepository.deleteById(userID);
    }
    public Page<User> getUsersWithPagination(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
