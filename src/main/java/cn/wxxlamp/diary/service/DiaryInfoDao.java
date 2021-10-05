package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryInfo;

/**
 * 外部操作层
 * @author wxxlamp
 * @date 2021/10/05~12:52
 */
public class DiaryInfoDao {

    private DiaryInfoIoFacade diaryInfoIoFacade = new DiaryInfoIoFacade();

    public boolean insert(String content) {
        return true;
    }

    public boolean insertOrUpdate(DiaryInfo diaryInfo) {
        return true;
    }

    public DiaryInfo query(String path) {
        return diaryInfoIoFacade.readDiaryInfo(path);
    }
}
