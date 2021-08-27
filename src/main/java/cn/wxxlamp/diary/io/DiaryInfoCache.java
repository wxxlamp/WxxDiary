package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryMetaInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wxxlamp
 * @date 2021/08/24~22:52
 */
class DiaryInfoCache {

    /**
     * TODO 把v升级为 {@link cn.wxxlamp.diary.DiaryInfo}
     */
    private static final Map<String, DiaryMetaInfo> META_INFO_CACHE = new HashMap<>(16);

    public static DiaryMetaInfo getMetaInfo(String path) {
        return META_INFO_CACHE.get(path);
    }

    public static void putMetaInfo(String path, DiaryMetaInfo metaInfo) {
        META_INFO_CACHE.put(path, metaInfo);
    }

    public static List<DiaryMetaInfo> listMouthMetaInfo(String path) {
        return META_INFO_CACHE.keySet().stream()
                .filter(e -> e.contains(path))
                .map(META_INFO_CACHE::get)
                .collect(Collectors.toList());
    }
}
