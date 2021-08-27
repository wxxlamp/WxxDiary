package cn.wxxlamp.diary.io;

import cn.wxxlamp.diary.DiaryMetaInfo;
import cn.wxxlamp.diary.util.PathUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * 本类负责初步抽象
 * @author wxxlamp
 * @date 2021/08/22~22:42
 */
class DiaryInfoStream {

    private final StringFileIoStream ioStream;

    public DiaryInfoStream(StringFileIoStream ioStream) {
        this.ioStream = ioStream;
    }

    public void writeContentInfo(String content, String filePath) {
        ioStream.write(content, filePath);
    }

    public String readContentInfo(String filePath) {
        return ioStream.read(filePath);
    }

    public DiaryMetaInfo readMetaInfo(String filePath) {
        DiaryMetaInfo metaInfo = DiaryInfoCache.getMetaInfo(filePath);
        if (metaInfo == null) {
            String metaInfoListJson = ioStream.read(PathUtils.getMetaPathFromFilePath(filePath));
            List<DiaryMetaInfo> metaInfoList = JSONObject.parseObject(metaInfoListJson, new TypeReference<List<DiaryMetaInfo>>(){});
            for (DiaryMetaInfo diaryMetaInfo : metaInfoList) {
                String subFilePath = diaryMetaInfo.getFilePath();
                if (filePath.equals(subFilePath)) {
                    metaInfo = diaryMetaInfo;
                }
                DiaryInfoCache.putMetaInfo(subFilePath, diaryMetaInfo);
            }
        }
        return metaInfo;
    }

    public void writeMetaInfo(DiaryMetaInfo metaInfo, String filePath) {
        this.writeMetaInfo(metaInfo, filePath, false);
    }

    public void writeMetaInfo(DiaryMetaInfo metaInfo, String filePath, Boolean persistence) {
        DiaryInfoCache.putMetaInfo(filePath, metaInfo);
        if (persistence) {
            StringBuilder sb = new StringBuilder();
            String metaPath = PathUtils.getMetaPathFromFilePath(filePath);
            DiaryInfoCache.listMouthMetaInfo(metaPath).forEach(sb::append);
            ioStream.write(sb.toString(), metaPath);
        }
    }

}
