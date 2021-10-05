package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import com.alibaba.fastjson.JSONObject;

/**
 * 本类负责初步抽象
 * @author wxxlamp
 * @date 2021/08/22~22:42
 */
class DiaryInfoStream {

    private final StringFileIoStream ioStream;

    private static final String SPLIT = "|";
    private static final String SPLIT_REGX = "\\|";

    public DiaryInfoStream(StringFileIoStream ioStream) {
        this.ioStream = ioStream;
    }

    public void writeDiaryInfo(DiaryInfo diaryInfo, String filePath, Boolean persistence) {
        DiaryInfoCache.putDiaryInfo(filePath, diaryInfo);
        if (persistence) {
            String outputString = diaryInfo.getMetaInfo().toString()
                    + SPLIT
                    + diaryInfo.getContent();
            ioStream.write(outputString, filePath);
        }
    }

    public DiaryInfo readDiaryInfo(String filePath) {
        DiaryInfo diaryInfo = DiaryInfoCache.getDiaryInfo(filePath);
        if (diaryInfo == null) {
            String[] inputString = ioStream.read(filePath).split(SPLIT_REGX);
            String metaInfoJson = inputString[0];
            String content = inputString[1];
            diaryInfo = DiaryInfo.builder()
                    .metaInfo(JSONObject.parseObject(metaInfoJson, DiaryMetaInfo.class))
                    .content(content).build();
            DiaryInfoCache.putDiaryInfo(filePath, diaryInfo);
        }
        return diaryInfo;
    }

}
