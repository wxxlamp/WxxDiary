package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryInfo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:42
 */
public class DiaryInfoStream {

    Map<String, List<DiaryInfo>> cache = new HashMap<>(8);

    public void write(DiaryInfo diaryInfo) {

    }

    public File read(DiaryInfo diaryInfo) {
        return null;
    }


}
