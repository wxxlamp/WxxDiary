package cn.wxxlamp.diary.util;

/**
 * 路径命名方式：2021/08/03
 * @author wxxlamp
 * @date 2021/08/26~23:36
 */
public class PathUtils {

    public static String getMouthPathFromWholePath(String path) {
        // FIXME
        return path.substring(0, 2);
    }

    public static String getPath(Integer year, Integer mouth, Integer day) {
        // FIXME
        return year + "/" + mouth + "/" + day;
    }

    public static String getPath(Integer year, Integer mouth) {
        // FIXME
        return year + "/" + mouth;
    }
}
