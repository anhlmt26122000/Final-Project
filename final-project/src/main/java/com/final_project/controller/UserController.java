package com.final_project.controller;

import com.final_project.dto.UserCreationRequest;
import com.final_project.dto.UserUpdateRequest;
import com.final_project.entity.User;
import com.final_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody @Valid UserCreationRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{username}")
    User getUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }

    @PutMapping("/{userID}")
    User updateUser(@PathVariable("userID") String userID, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userID, request);
    }

    @DeleteMapping("/{userID}")
    String deleteUser(@PathVariable("userID") String userID){
        userService.deleteUser(userID);
        return "User deleted";
    }
}
