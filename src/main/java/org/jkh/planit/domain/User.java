package org.jkh.planit.domain;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {
    private final int userId;
    private String userPwHash;
    private String username;
    private final Timestamp createAt;

    @Email
    private String email;
}
