package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.model.DiaryDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:31
 */
public class DateUtils {

    /**
     * 获取年月日
     * @param timestamp 时间戳
     * @return 年月日
     * @deprecated {@link #getDate(Long)}
     */
    public static Integer[] getYearMouthDay(Long timestamp) {
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return new Integer[]{localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()};
    }

    public static byte getWeek(Long timestamp) {
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return (byte) localDate.getDayOfWeek().getValue();
    }

    public static DiaryDate getDate(Long timestamp) {
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return DiaryDate.builder()
                .year(localDate.getYear())
                .mouth(localDate.getMonthValue())
                .day(localDate.getDayOfMonth())
                .build();
    }
}
