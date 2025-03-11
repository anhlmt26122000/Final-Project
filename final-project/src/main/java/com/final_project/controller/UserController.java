package com.final_project.controller;

import com.final_project.dto.response.ApiResponse;
import com.final_project.dto.request.UserCreationRequest;
import com.final_project.dto.request.UserUpdateRequest;
import com.final_project.entity.User;
import com.final_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
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

    @GetMapping("/page")
    public ApiResponse<Page<User>> getUsersWithPagination(Pageable pageable) {
        Page<User> userPage = userService.getUsersWithPagination(pageable);
        return new ApiResponse<>(200, "User list retrieved successfully", userPage);
    }
}
