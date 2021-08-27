package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryInfo;
import cn.wxxlamp.diary.DiaryMetaInfo;

/**
 * 供外部系统调用
 * @author wxxlamp
 * @date 2021/08/26~23:53
 */
public class DiaryInfoIoFacade {

    private static final DiaryInfoStream STREAM = new DiaryInfoStream(new StringFileIoStream());

    public void writeDiaryInfo(DiaryInfo diaryInfo) {
        String metaPath = diaryInfo.getMetaInfo().getMetaPath();
        STREAM.writeMetaInfo(diaryInfo.getMetaInfo(), metaPath);
        STREAM.writeContentInfo(diaryInfo.getContent(), diaryInfo.getMetaInfo().getFilePath());
    }

    public DiaryInfo readDiaryInfo(String filePath) {
        DiaryMetaInfo diaryMetaInfo = STREAM.readMetaInfo(filePath);
        String content = STREAM.readContentInfo(filePath);
        return DiaryInfo.builder().metaInfo(diaryMetaInfo).content(content).build();
    }
}
