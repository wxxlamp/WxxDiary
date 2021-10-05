package cn.wxxlamp.diary.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:31
 */
public class DateUtils {

    public static Integer[] getYearMouthDay(Long timestamp) {
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return new Integer[]{localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()};
    }

    public static byte getWeek(Long timestamp) {
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return (byte) localDate.getDayOfWeek().getValue();
    }
}
