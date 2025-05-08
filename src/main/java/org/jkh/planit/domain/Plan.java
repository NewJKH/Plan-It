package org.jkh.planit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Plan {
    private final int scheduleId;
    private final int userId;
    private String title;
    private String contents;
    private final Date createDate;
    private Date modifyDate;

}
