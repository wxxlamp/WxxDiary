package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.model.DiaryInfo;

/**
 * 供外部系统调用
 * @author wxxlamp
 * @date 2021/08/26~23:53
 */
public class DiaryInfoIoFacade {

    private static final DiaryInfoStream STREAM = new DiaryInfoStream(new StringFileIoStream());

    public void writeDiaryInfo(DiaryInfo diaryInfo, Boolean persistence) {
        String filePath = diaryInfo.getMetaInfo().getFilePath();
        STREAM.writeDiaryInfo(diaryInfo, filePath, persistence);
    }

    public DiaryInfo readDiaryInfo(String filePath) {
        return STREAM.readDiaryInfo(filePath);
    }
}
