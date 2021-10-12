package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.constants.FxmlNames;
import cn.wxxlamp.diary.constants.UiText;
import cn.wxxlamp.diary.controller.MainController;
import cn.wxxlamp.diary.controller.WriterController;
import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.google.common.collect.Maps;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
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

    private final MainController mainComponent;

    private final DiaryInfoIoFacade facade;

    private static final Map<String, Tab> REGISTER_TAB = Maps.newHashMap();
    /**
     * 是否切换编辑面板
     */
    private Boolean noSet = true;
    /**
     * 是否刷新目录
     */
    private boolean flash = true;

    public WriterPaneService(MainController component) {
        this.mainComponent = component;
        facade = new DiaryInfoIoFacade();
    }

    /**
     * 为日记创建一个tab
     * @param date 日期
     * @return left=tab, right=是否存在
     */
    public Pair<Tab, Boolean> newTab(DiaryDate date) {
        BorderPane writerPane = mainComponent.getWritePane();
        JFXButton writerButton = mainComponent.getWriteButton();
        TreeView<String> treeView = mainComponent.getDir();

        DiaryInfo diaryInfo = getDiaryInfoNotNull(PathUtils.getAbsolutePath(date));
        String title = PathUtils.getRelativePath(date);
        Tab tab = REGISTER_TAB.get(title);
        boolean registeredTab = true;
        if (tab == null) {
            FXMLLoader tabLoader = FxmlUtils.getLoader(FxmlNames.WRITER, false);
            tab = tabLoader.getRoot();
            tab.setText(title);
            tab.setOnCloseRequest(e -> {
                REGISTER_TAB.remove(((Tab)e.getSource()).getText());
                if (REGISTER_TAB.size() == 0) {
                    writerPane.setCenter(writerButton);
                    noSet = true;
                }
            });
            WriterController writerComponent = tabLoader.getController();
            registeredTab = false;
            HTMLEditor editor = writerComponent.getEditor();
            editor.setHtmlText(diaryInfo.getContent());
            editor.setOnKeyPressed(e -> {
                if (e.isControlDown() & e.getCode() == KeyCode.S) {
                    diaryInfo.setContent(editor.getHtmlText());
                    diaryInfo.getMetaInfo().setUpdateTime(System.currentTimeMillis());
                    facade.writeDiaryInfo(diaryInfo, true);
                    // 如果是当天的第一次保存，则刷新目录
                    if (date.equals(DateUtils.getDate(System.currentTimeMillis())) && flash) {
                        flash = false;
                        treeView.setRoot(getDiaryDir());
//                        treeView.getSelectionModel().select();
                    }
                }
            });
            REGISTER_TAB.put(title, tab);
        }
        return new Pair<>(tab, registeredTab);
    }

    public void setTabPane(DiaryDate date){
        TabPane tabPane = mainComponent.getTabPane();
        BorderPane writerPane = mainComponent.getWritePane();

        Pair<Tab, Boolean> pair = newTab(date);
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
