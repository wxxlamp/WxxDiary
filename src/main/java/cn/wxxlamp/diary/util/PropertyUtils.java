package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DiaryException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author wxxlamp
 * @date 2021/10/17~17:40
 */
public class PropertyUtils {

    private final static Properties PROPERTIES = new Properties();

    public static String readValue(String key) {

        try {
            PROPERTIES.load(PropertyUtils.class.getClassLoader().getResourceAsStream("setting.properties"));
            return PROPERTIES.getProperty(key);
        } catch (IOException e) {
            throw  new DiaryException(DiaryException.DailyExceptionEnum.SYS_ERROR);
        }
    }

    public static void write(String key, String value) {
        try {
            PROPERTIES.setProperty(key, value);
            PROPERTIES.store(new FileWriter(Objects.requireNonNull(PropertyUtils.class.getResource("/setting.properties")).toURI().getPath()), null);
        } catch (URISyntaxException | IOException e) {
            throw new DiaryException(DiaryException.DailyExceptionEnum.SYS_ERROR);
        }

    }
}
