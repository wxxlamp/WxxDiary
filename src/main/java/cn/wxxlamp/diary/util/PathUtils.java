package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.model.DiaryDate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 路径命名方式：2021/08/03
 * @author wxxlamp
 * @date 2021/08/26~23:36
 */
public class PathUtils {

    private static final String DIR = System.getProperty("user.dir") + File.separator
            + "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator
            + "wd" + File.separator
            + "data";

    private static final Pattern PATTERN = Pattern.compile("[0-9]*");

    /**
     * 获取用户数据目录
     * @return {@link PathUtils#DIR}
     */
    public static String getDir() {
        return DIR;
    }

    /**
     * 通过时间戳来获取文件绝对路径
     * @param timestamp 时间戳
     * @return 绝对路径
     */
    public static String getAbsolutePath(long timestamp) {
        DiaryDate date = DateUtils.getDate(timestamp);
        return PathUtils.getAbsolutePath(date);
    }

    /**
     * 通过年月日来获取文件绝对路径
     * @param year 年
     * @param mouth 月
     * @param day 日
     * @return 绝对路径
     * @deprecated {@link #getAbsolutePath(DiaryDate)}
     */
    public static String getAbsolutePath(Integer year, Integer mouth, Integer day) {
        return getDir() + File.separator + getRelativePath(year, mouth, day);
    }

    /**
     * 获取绝对路径
     * @param date 日期
     * @return 绝对路径
     */
    public static String getAbsolutePath(DiaryDate date) {
        return getDir() + File.separator + getRelativePath(date);
    }

    /**
     * 通过年月日来获取文件相对路径
     * @param year 年
     * @param mouth 月
     * @param day 日
     * @return 相对路径
     * @deprecated {@link #getRelativePath(DiaryDate)}
     */
    public static String getRelativePath(Integer year, Integer mouth, Integer day) {
        return year + File.separator + mouth + File.separator + day;
    }

    /**
     * 获得相对路径
     * @param date 日期
     * @return 相对路径
     */
    public static String getRelativePath(DiaryDate date) {
        return date.getYear() + File.separator + date.getMouth() + File.separator + date.getDay();
    }

    /**
     * 通过相对路径来获取该目录下的文件名称
     * @param path 文件名称
     * @return 文件列表
     */
    public static List<Integer> getSubFileName(String path) {
        File file = new File(path);
        List<Integer> dateList = new ArrayList<>();
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            if (PATTERN.matcher(subFile.getName()).matches()){
                dateList.add(Integer.valueOf(subFile.getName()));
            }
        }
        return dateList;
    }


    public static String buildRandomPath(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            Random ra =new Random();
            sb.append(ra.nextInt(10));
        }
        return sb.toString();
    }
}
