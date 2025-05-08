package org.jkh.planit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class User {
    private final int userId;
    private String userPw;
    private String username;
    private final Date joinDate;

}
