package com.helvetica.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;

}
