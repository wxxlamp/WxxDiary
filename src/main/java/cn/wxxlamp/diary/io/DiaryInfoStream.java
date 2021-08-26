package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryInfo;
import cn.wxxlamp.diary.DiaryMetaInfo;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.alibaba.fastjson.JSON;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:42
 */
class DiaryInfoStream {

    private final StringFileIoStream ioStream;

    public DiaryInfoStream(StringFileIoStream ioStream) {
        this.ioStream = ioStream;
    }

    public void writeContentInfo(String content, String path) {
        ioStream.write(content, path);
    }

    public String readContentInfo(String path) {
        return ioStream.read(path);
    }

    public DiaryMetaInfo readMetaInfo(String path) {
        DiaryMetaInfo metaInfo = DiaryInfoCache.getMetaInfo(path);
        if (metaInfo == null) {
            String metaInfoListJson = ioStream.read(path);
            metaInfo = JSON.parseObject(metaInfoListJson, DiaryMetaInfo.class);
        }
        return metaInfo;
    }

    public void writeMetaInfo(DiaryMetaInfo metaInfo, String path) {
        this.writeMetaInfo(metaInfo, path, false);
    }

    public void writeMetaInfo(DiaryMetaInfo metaInfo, String path, Boolean persistence) {
        DiaryInfoCache.put(path, metaInfo);
        if (persistence) {
            StringBuilder sb = new StringBuilder();
            DiaryInfoCache.listMouthMetaInfo(PathUtils.getMouthPathFromWholePath(path)).forEach(sb::append);
            ioStream.write(sb.toString(), path);
        }
    }

}
