package cn.wxxlamp.diary.util;

/**
 * @author wxxlamp
 * @date 2021/10/04~23:24
 */
public class StringUtils {

    public static String subString(String origin, String endStr) {
        int index = origin.indexOf(endStr);
        return origin.substring(0, index);
    }
}
