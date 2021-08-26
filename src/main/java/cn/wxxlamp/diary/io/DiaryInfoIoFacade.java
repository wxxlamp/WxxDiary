package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryInfo;

/**
 * 供外部系统调用
 * @author wxxlamp
 * @date 2021/08/26~23:53
 */
public class DiaryInfoIoFacade {

    private static final DiaryInfoStream STREAM = new DiaryInfoStream(new StringFileIoStream());

    public void writeDiaryInfo(DiaryInfo diaryInfo) {
        String path = diaryInfo.getMetaInfo().getPath();
        STREAM.writeMetaInfo(diaryInfo.getMetaInfo(), path);
        STREAM.writeContentInfo(diaryInfo.getContent(), diaryInfo.getMetaInfo().getFilePath());
    }
}
