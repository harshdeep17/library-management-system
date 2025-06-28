package com.education.librarymanagementsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String email;
    private UserRole role;
    private String password;
}
