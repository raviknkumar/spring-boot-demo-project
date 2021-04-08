package com.example.messageapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterDto {

    private Integer id;
    private Integer username;
    private Integer mobileNumber;
}
