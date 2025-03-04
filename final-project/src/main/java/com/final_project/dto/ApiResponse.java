package com.final_project.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ApiResponse <T> {
    int code =200;
    String message;
    T result;

}
