package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.constants.SystemConstants;
import cn.wxxlamp.diary.constants.UiTextConstants;
import cn.wxxlamp.diary.controller.MainController;
import cn.wxxlamp.diary.controller.WriterController;
import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.util.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.wxxlamp.diary.constants.UiTextConstants.FEELING_MAP;

/**
 * @author wxxlamp
 * @date 2021/10/05~10:37
 */
public class MainService {

    private final MainController mainComponent;

    private final DiaryInfoIoFacade facade;

    public MainService(MainController component) {
        this.mainComponent = component;
        facade = new DiaryInfoIoFacade();
    }

    /**
     * 为主展示页面创建tab
     * @param date 根据时间创建
     */
    public void setTabPane(DiaryDate date) {
        TabPane tabPane = mainComponent.getTabPane();
        BorderPane writerPane = mainComponent.getWritePane();

        Pair<Tab, Boolean> pair = newTab(date);
        if (!pair.getValue()) {
            tabPane.getTabs().add(pair.getKey());
        }
        String title = PathUtils.getRelativePath(date);
        tabPane.getSelectionModel().select(StatusRecord.REGISTER_TAB.get(title));
        if (StatusRecord.noSet) {
            writerPane.setCenter(tabPane);
            StatusRecord.noSet = false;
        }
    }

    /**
     * 为日记创建一个tab
     * @param date 日期
     * @return left=tab, right=是否存在
     */
    private Pair<Tab, Boolean> newTab(DiaryDate date) {
        String title = PathUtils.getRelativePath(date);
        Tab tab = StatusRecord.REGISTER_TAB.get(title);
        boolean registeredTab = true;
        if (tab == null) {
            tab = buildNewTab(title, date);
            registeredTab = false;
            StatusRecord.REGISTER_TAB.put(title, tab);
        }
        return new Pair<>(tab, registeredTab);
    }

    private Tab buildNewTab(String title, DiaryDate date) {

        FXMLLoader tabLoader = FxmlUtils.getLoader(SystemConstants.WRITER, false);
        Tab tab = tabLoader.getRoot();
        WriterController writerComponent = tabLoader.getController();
        writerComponent.initController(mainComponent, title, date, facade);

        return tab;
    }
}
