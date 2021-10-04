package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.PathUtils;
import cn.wxxlamp.diary.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author wxxlamp
 * @date 2021/08/28~22:46
 */
public class UtilTest {

    @Test
    public void DateUtilTest() {
        System.out.println(Arrays.toString(DateUtils.getYearMouthDay(System.currentTimeMillis())));
    }

    @Test
    public void DirUtilTest() {
        PathUtils.getSubFileName(PathUtils.getDir()).forEach(y -> {
            System.out.print(y + "年");
            PathUtils.getSubFileName(PathUtils.getDir() + File.separator + y).forEach(m -> {
                System.out.print(m + "月");
                PathUtils.getSubFileName(PathUtils.getDir() + File.separator + y + File.separator + m).forEach(d -> System.out.println(d + "天"));
            });
        });
    }

    @Test
    public void StringUtilTest() {
        String end = "DAY";
        String begin = "3ishfasdfhsfd";
        Assert.assertEquals(begin, StringUtils.subString(begin+end, end));
    }
}
