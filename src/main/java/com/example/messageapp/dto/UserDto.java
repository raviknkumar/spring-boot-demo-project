package com.example.messageapp.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // unknown field other than those fields present inside dto, they are neglected
public class UserDto {

    private Integer id;
    private String username;
    private String password;
}
