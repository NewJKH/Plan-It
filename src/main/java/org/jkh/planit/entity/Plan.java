package org.jkh.planit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Plan {
    private int scheduleId;
    private final int userId;
    private String title;
    private String contents;
    private final Timestamp createDate;
    private Timestamp modifyDate;

    public Plan(int userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.modifyDate = new Timestamp(System.currentTimeMillis());
    }
}
