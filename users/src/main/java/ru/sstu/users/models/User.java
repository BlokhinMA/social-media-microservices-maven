package ru.sstu.users.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private int id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String password;
    private String role;

}
