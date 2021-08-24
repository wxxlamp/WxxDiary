package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryInfo;
import cn.wxxlamp.diary.DiaryMetaInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:42
 */
public class DiaryInfoStream {



    public void write(DiaryInfo diaryInfo, Boolean metaInfo, Boolean content) {
        try (FileWriter writer = new FileWriter(diaryInfo.getMetaInfo().getFilePath())){
            writer.write(diaryInfo.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
