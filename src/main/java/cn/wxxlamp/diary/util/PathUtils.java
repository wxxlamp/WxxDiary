package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DailyException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public static String getDir() {
        return DIR;
    }

    public static String getPath(Integer year, Integer mouth, Integer day) {
        return year + File.separator + mouth + File.separator + day;
    }

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

    public static String createIfNotExit(String path) {
        File dayFile = new File(path);
        File mouthFolder;
        boolean success = true;
        if (!dayFile.exists()) {
            if (!(mouthFolder = dayFile.getParentFile()).exists()) {
                success = mouthFolder.mkdirs();
            }
            try {
                success = dayFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!success) {
           throw  new DailyException(DailyException.DailyExceptionEnum.FILE_OPEN_ERROR);
        }
        return path;
    }
}
