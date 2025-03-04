package com.final_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN_ERROR(9999, "Unknown error"),
    USERNAME_EXISTED(1001, "Username already existed"),
    USERNAME_NULL(1002, "Username cannot be null"),
    PASSWORD_AT_LEAST_8(1003, "Password must be at least 8 characters"),
    EMAIL_NULL(1004, "Email cannot be null"),
    FIRST_NAME_NULL(1005, "First name cannot be null"),
    LAST_NAME_NULL(1006, "Last name cannot be null")
    ;

    private int code;
    private String message;

}
