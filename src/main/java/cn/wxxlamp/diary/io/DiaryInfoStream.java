package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.exception.DiaryException;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.model.Settings;
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
        String inputStream;
        if (diaryInfo == null && (inputStream = ioStream.read(filePath)) != null) {
            String[] inputString = inputStream.split(SPLIT_REGX);
            String metaInfoJson = inputString[0];
            String content = inputString[1];
            diaryInfo = DiaryInfo.builder()
                    .metaInfo(JSONObject.parseObject(metaInfoJson, DiaryMetaInfo.class))
                    .content(content).build();
            DiaryInfoCache.putDiaryInfo(filePath, diaryInfo);
        }
        return diaryInfo;
    }

    public Settings readSettings(String filePath) {
        String inputStream;
        if ((inputStream = ioStream.read(filePath)) != null) {
            return JSONObject.parseObject(inputStream, Settings.class);
        }
        throw new DiaryException(DiaryException.DailyExceptionEnum.SETTING_FILE_NOT_FOUND);
    }

    public void writeSettings(Settings settings, String path) {
        ioStream.write(settings.toString(), path);
    }

}
