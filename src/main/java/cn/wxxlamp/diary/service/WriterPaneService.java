package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.google.common.collect.Lists;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

import java.util.List;

/**
 * @author wxxlamp
 * @date 2021/10/05~10:37
 */
public class WriterPaneService {

    private final DiaryInfoIoFacade facade = new DiaryInfoIoFacade();
    private static final List<String> REGISTER_TAB = Lists.newArrayList();

    public Tab newTab(String path, boolean p) {
        DiaryInfo diaryInfo = facade.readDiaryInfo(path);
        Integer[] date = DateUtils.getYearMouthDay(diaryInfo.getMetaInfo().getCreateTime());
        String title = PathUtils.getPath(date[0], date[1], date[2]);
        Tab tab = null;
        if (!REGISTER_TAB.contains(title)) {
            HTMLEditor editor = new HTMLEditor();
            editor.setHtmlText(diaryInfo.getContent());
            editor.setOnKeyPressed(e -> {
                if (e.isControlDown() & e.getCode() == KeyCode.S) {
                    diaryInfo.setContent(editor.getHtmlText());
                    diaryInfo.getMetaInfo().setUpdateTime(System.currentTimeMillis());
                    facade.writeDiaryInfo(diaryInfo, true);
                }
            });
            AnchorPane pane = new AnchorPane(editor);
            tab = new Tab(title);
            tab.setContent(pane);
            tab.setOnCloseRequest(e -> REGISTER_TAB.remove(((Tab)e.getSource()).getText()));
            REGISTER_TAB.add(title);
        }
        return tab;
    }

    public Tab newTab(String title) {
        Tab tab = null;
        if (!REGISTER_TAB.contains(title)) {
            HTMLEditor editor = new HTMLEditor();
            editor.setOnKeyPressed(e -> {
                if (e.isControlDown() & e.getCode() == KeyCode.S) {
                    facade.writeDiaryInfo(DiaryInfo.builder()
                            .content(editor.getHtmlText())
                            .metaInfo(DiaryMetaInfo.builder()
                                    .week(DateUtils.getWeek(System.currentTimeMillis()))
                                    .createTime(System.currentTimeMillis())
                                    .updateTime(System.currentTimeMillis())
                                    .filePath(PathUtils.getAbsolutePath(System.currentTimeMillis()))
                                    .build())
                            .build(), true);
                }
            });
            AnchorPane pane = new AnchorPane(editor);
            tab = new Tab(title);
            tab.setContent(pane);
            tab.setOnCloseRequest(e -> REGISTER_TAB.remove(((Tab)e.getSource()).getText()));
            REGISTER_TAB.add(title);
        }
        return tab;
    }
}
