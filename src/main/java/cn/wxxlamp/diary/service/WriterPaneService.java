package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.constants.UiText;
import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.google.common.collect.Maps;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.util.Pair;

import java.io.File;
import java.util.Comparator;
import java.util.Map;

/**
 * @author wxxlamp
 * @date 2021/10/05~10:37
 */
public class WriterPaneService {

    private final DiaryInfoIoFacade facade = new DiaryInfoIoFacade();
    private static final Map<String, Tab> REGISTER_TAB = Maps.newHashMap();
    private Boolean noSet = true;
    private final TabPane tabPane = new TabPane();

    /**
     * 为日记创建一个tab
     * @param date 日期
     * @param writerPane 写入模块
     * @param writerButton 创建按钮
     * @return left=tab, right=是否存在
     */
    public Pair<Tab, Boolean> newTab(DiaryDate date, BorderPane writerPane, JFXButton writerButton) {
        DiaryInfo diaryInfo = getDiaryInfoNotNull(PathUtils.getAbsolutePath(date));
        String title = PathUtils.getRelativePath(date);
        Tab tab = REGISTER_TAB.get(title);
        boolean registeredTab = true;
        if (tab == null) {
            registeredTab = false;
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
            REGISTER_TAB.put(title, tab);
        }
        return new Pair<>(tab, registeredTab);
    }

    public void setTabPane(DiaryDate date, BorderPane writerPane, JFXButton writerButton){
        Pair<Tab, Boolean> pair = newTab(date, writerPane, writerButton);
        if (!pair.getValue()) {
            tabPane.getTabs().add(pair.getKey());
        }
        String title = PathUtils.getRelativePath(date);
        tabPane.getSelectionModel().select(REGISTER_TAB.get(title));
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

    public TreeItem<String> getDiaryDir() {
        TreeItem<String> rootItem = new TreeItem<>(UiText.DIR);
        PathUtils.getSubFileName(PathUtils.getDir()).stream().sorted(Comparator.reverseOrder()).forEach(y -> {
            TreeItem<String> yearItem = new TreeItem<>(y + UiText.YEAR);
            PathUtils.getSubFileName(PathUtils.getDir() + File.separator + y).stream().sorted(Comparator.reverseOrder()).forEach(m -> {
                TreeItem<String> mouthItem = new TreeItem<>(m + UiText.MOUTH);
                PathUtils.getSubFileName(PathUtils.getDir() + File.separator + y + File.separator + m).stream().sorted(Comparator.reverseOrder()).forEach(d -> {
                    TreeItem<String> dayItem = new TreeItem<>(d + UiText.DAY);
                    mouthItem.getChildren().add(dayItem);
                });
                yearItem.getChildren().add(mouthItem);
            });
            rootItem.getChildren().add(yearItem);
        });
        return rootItem;
    }
}
