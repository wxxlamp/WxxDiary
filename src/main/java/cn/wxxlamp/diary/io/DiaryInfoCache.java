package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryMetaInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxxlamp
 * @date 2021/08/24~22:52
 */
public class DiaryInfoCache {

    private static final Map<String, DiaryMetaInfo> META_INFO_CACHE = new HashMap<>(16);

    public static DiaryMetaInfo getMetaInfo(String path) {
        return META_INFO_CACHE.get(path);
    }

    public static void put(String path, DiaryMetaInfo metaInfo) {
        META_INFO_CACHE.put(path, metaInfo);
    }
}
