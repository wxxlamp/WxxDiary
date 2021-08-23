package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.DiaryMetaInfo;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:52
 */
public class DiaryUtils {

    public static String toString(DiaryMetaInfo diaryMetaInfo) {
        return JSON.toJSONString(diaryMetaInfo);
    }

    public static String toString(List<String> diaryInfoList) {
        return JSON.toJSONString(diaryInfoList);
    }
}
