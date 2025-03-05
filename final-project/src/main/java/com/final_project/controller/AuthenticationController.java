package com.final_project.controller;

import com.final_project.dto.request.AuthenticationRequest;
import com.final_project.dto.response.ApiResponse;
import com.final_project.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ApiResponse<Map<String, Boolean>> login(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);
        // Trả về JSON có format { "code": 200, "result": { "authentication": true } }
        return new ApiResponse<>(200, null, Map.of("authentication", result));
    }
}
