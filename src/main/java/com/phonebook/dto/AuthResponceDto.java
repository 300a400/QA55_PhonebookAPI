package com.phonebook.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class AuthResponceDto {

    private String token;
}
