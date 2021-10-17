package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.model.DiaryDate;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import static cn.wxxlamp.diary.constants.SystemConstants.BASE_DIR;
import static cn.wxxlamp.diary.constants.SystemConstants.BASE_PATH;

/**
 * 路径命名方式：2021/08/03
 *
 * @author wxxlamp
 * @date 2021/08/26~23:36
 */
public class PathUtils {

    /**
     * 文件存储根目录, URI 形式
     */
    private static String DIR = null;

    private static final Pattern PATTERN = Pattern.compile("[0-9]*");

    /**
     * 获取用户数据目录
     *
     * @return {@link PathUtils#DIR}
     */
    public static String getDir() {
        if (DIR == null) {
            DIR = PropertyUtils.readValue(BASE_PATH);
            if (DIR == null) {
                try {
                    DIR = Objects.requireNonNull(PathUtils.class.getResource("/")).toURI().toString();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                PropertyUtils.write(BASE_PATH, DIR);
            }
            DIR = DIR + BASE_DIR;
        }
        return DIR;
    }

    /**
     * 通过时间戳来获取文件绝对路径
     *
     * @param timestamp 时间戳
     * @return 绝对URI
     */
    public static String getAbsoluteUri(long timestamp) {
        DiaryDate date = DateUtils.getDate(timestamp);
        return PathUtils.getAbsoluteUri(date);
    }

    /**
     * 通过年月日来获取文件绝对路径
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 绝对URI
     * @deprecated {@link #getAbsoluteUri(DiaryDate)}
     */
    public static String getAbsoluteUri(Integer year, Integer mouth, Integer day) {
        return getDir() + "/" + getRelativePath(year, mouth, day);
    }

    /**
     * 获取绝对路径
     *
     * @param date 日期
     * @return 绝对URI
     */
    public static String getAbsoluteUri(DiaryDate date) {
        return getDir() + "/" + getRelativePath(date);
    }

    /**
     * 通过年月日来获取文件相对路径
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 相对 URI
     * @deprecated {@link #getRelativePath(DiaryDate)}
     */
    public static String getRelativePath(Integer year, Integer mouth, Integer day) {
        return year + "/" + mouth + "/" + day;
    }

    /**
     * 获得相对路径
     *
     * @param date 日期
     * @return 相对路径
     */
    public static String getRelativePath(DiaryDate date) {
        return date.getYear() + "/" + date.getMouth() + "/" + date.getDay();
    }

    /**
     * 通过时间戳来获取文件相对路径
     *
     * @param timestamp 时间戳
     * @return 绝对URI
     */
    public static String getRelativePath(long timestamp) {
        DiaryDate date = DateUtils.getDate(timestamp);
        return PathUtils.getRelativePath(date);
    }

    /**
     * 通过相对路径来获取该目录下的文件名称
     *
     * @param uri 文件URI
     * @return 文件列表
     */
    public static List<Integer> getSubFileName(String uri) {
        File file = new File(UriCache.getUri(uri));
        List<Integer> dateList = new ArrayList<>();
        if (file.listFiles() == null) {
            return dateList;
        }
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            if (PATTERN.matcher(subFile.getName()).matches()) {
                dateList.add(Integer.valueOf(subFile.getName()));
            }
        }
        return dateList;
    }


    public static String buildRandomPath(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random ra = new Random();
            sb.append(ra.nextInt(10));
        }
        return sb.toString();
    }

    public static String absolutePath2Relative(String absolutePath) {
        return absolutePath.substring((PathUtils.getDir() + "/").length());
    }

    public static String relativePath2Absolute(String relativePath) {
        return PathUtils.getDir() + "/" + relativePath;
    }

    public static void setDir(String dir) {
        DIR = dir;
    }
}
