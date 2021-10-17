package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.util.PathUtils;
import cn.wxxlamp.diary.util.UriCache;

/**
 * 供外部系统调用
 * @author wxxlamp
 * @date 2021/08/26~23:53
 */
public class DiaryInfoIoFacade {

    private static final DiaryInfoStream STREAM = new DiaryInfoStream(new StringFileIoStream());

    public void writeDiaryInfo(DiaryInfo diaryInfo, Boolean persistence) {
        String filePath = UriCache.getUri(PathUtils.getAbsoluteUri(diaryInfo.getMetaInfo().getCreateTime())).getPath();
        STREAM.writeDiaryInfo(diaryInfo, filePath, persistence);
    }

    public DiaryInfo readDiaryInfo(String fileUri) {
        return STREAM.readDiaryInfo(UriCache.getUri(fileUri).getPath());
    }
}
