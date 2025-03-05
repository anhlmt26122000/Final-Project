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
    LAST_NAME_NULL(1006, "Last name cannot be null"),
    USER_NOT_EXISTED(1007, "User not existed"),
    //CATEGORY
    CATEGORYNAME_NULL(1008,"Category name cannot be null"),
    CATEGORY_EXISTED(1009,"Category already existed"),
    CATEGORY_NOT_EXISTED(1010,"Category not existed"),
    PRODUCTNAME_NULL(1010,"Product name cannot be null")
    ;

    private int code;
    private String message;

}
