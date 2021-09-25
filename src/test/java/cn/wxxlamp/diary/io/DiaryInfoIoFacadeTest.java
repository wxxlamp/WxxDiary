package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.PathUtils;
import org.junit.Test;

import java.io.File;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:44
 */
public class DiaryInfoIoFacadeTest {

    private static final String DIR = System.getProperty("user.dir") + File.separator
            + "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator
            + "wd" + File.separator
            + "data";

    @Test
    public void outputTest() {
        DiaryInfoIoFacade facade = new DiaryInfoIoFacade();
        facade.writeDiaryInfo(buildDiaryInfo(), true);
    }

    @Test
    public void inputTest() {
        DiaryInfoIoFacade facade = new DiaryInfoIoFacade();
        Integer[] date = DateUtils.getYearMouthDay(System.currentTimeMillis());
        System.out.println(facade.readDiaryInfo(DIR + File.separator + PathUtils.getPath(date[0],date[1],date[2])));
    }

    public DiaryInfo buildDiaryInfo() {
        DiaryInfo diaryInfo = new DiaryInfo();
        diaryInfo.setContent("666我的宝贝");
        DiaryMetaInfo metaInfo = new DiaryMetaInfo();
        metaInfo.setCreateTime(System.currentTimeMillis());
        metaInfo.setUpdateTime(System.currentTimeMillis());
        metaInfo.setFeeling((byte)0);
        Integer[] date = DateUtils.getYearMouthDay(System.currentTimeMillis());
        metaInfo.setFilePath(DIR + File.separator + PathUtils.getPath(date[0],date[1],date[2]));
        metaInfo.setId(1);
        metaInfo.setWeather((short)28);
        metaInfo.setWeek((byte)6);
        diaryInfo.setMetaInfo(metaInfo);
        return diaryInfo;
    }
}
