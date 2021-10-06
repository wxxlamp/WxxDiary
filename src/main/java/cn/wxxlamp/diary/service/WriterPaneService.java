package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.google.common.collect.Lists;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;

import java.util.List;

import static cn.wxxlamp.diary.constants.FxmlComponents.WRITER_PANE;

/**
 * @author wxxlamp
 * @date 2021/10/05~10:37
 */
public class WriterPaneService {

    private final DiaryInfoIoFacade facade = new DiaryInfoIoFacade();
    private static final List<String> REGISTER_TAB = Lists.newArrayList();
    private Boolean noSet = true;

    public Tab newTab(String path, BorderPane writerPane, JFXButton writerButton) {
        DiaryInfo diaryInfo = getDiaryInfoNotNull(path);
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
            AnchorPane.setBottomAnchor(editor, 0D);
            AnchorPane.setTopAnchor(editor, 0D);
            AnchorPane.setLeftAnchor(editor, 0D);
            AnchorPane.setRightAnchor(editor, 0D);
            tab = new Tab(title);
            tab.setContent(pane);
            tab.setOnCloseRequest(e -> {
                REGISTER_TAB.remove(((Tab)e.getSource()).getText());
                if (REGISTER_TAB.size() == 0) {
                    writerPane.setCenter(writerButton);
                    noSet = true;
                }

            });
            REGISTER_TAB.add(title);
        }
        return tab;
    }

    public void setTabPane(String path, BorderPane writerPane, JFXButton writerButton){
        FXMLLoader loader = FxmlUtils.getLoader(WRITER_PANE);
        TabPane tabPane = loader.getRoot();
        Tab tab;
        if (((tab = newTab(path, writerPane, writerButton)) != null)) {
            tabPane.getTabs().add(tab);
        }
        if (noSet) {
            writerPane.setCenter(tabPane);
            noSet = false;
        }
    }
    private DiaryInfo getDiaryInfoNotNull(String path) {
        DiaryInfo diaryInfo = facade.readDiaryInfo(path);
        if (diaryInfo == null) {
            diaryInfo = DiaryInfo.builder()
                    .metaInfo(DiaryMetaInfo.builder()
                            .week(DateUtils.getWeek(System.currentTimeMillis()))
                            .createTime(System.currentTimeMillis())
                            .updateTime(System.currentTimeMillis())
                            .filePath(PathUtils.getAbsolutePath(System.currentTimeMillis()))
                            .build())
                    .build();
        }
        return diaryInfo;
    }
}
