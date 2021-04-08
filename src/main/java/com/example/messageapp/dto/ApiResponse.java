package com.example.messageapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * uniformity in the response
 * @param <T>
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    public boolean success = true; // true , false
    public T data; // generics
    public String errorCode; // fail
    public String errorMessage; // fail
    public String message; // success message

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
