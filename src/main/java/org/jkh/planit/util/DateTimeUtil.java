package org.jkh.planit.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtil {
    private DateTimeUtil(){}
    public static Timestamp toTimestamp(String date){
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Timestamp.valueOf(localDateTime);
    }
}
