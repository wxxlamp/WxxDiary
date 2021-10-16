package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.model.DiaryInfo;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxxlamp
 * @date 2021/08/24~22:52
 */
class DiaryInfoCache {

    private static final Map<String, DiaryInfo> DIARY_INFO_CACHE = Maps.newHashMapWithExpectedSize(16);


    public static DiaryInfo getDiaryInfo(String path) {
        return DIARY_INFO_CACHE.get(path);
    }

    public static void putDiaryInfo(String path, DiaryInfo diaryInfo) {
        DIARY_INFO_CACHE.put(path, diaryInfo);
    }
}
