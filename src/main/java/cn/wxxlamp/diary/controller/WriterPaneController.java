package cn.wxxlamp.diary.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.HTMLEditor;

/**
 * @author wxxlamp
 * @date 2021/08/28~23:50
 */
public class WriterPaneController {

    @FXML
    private HTMLEditor editor;

    @FXML
    private Tab tab;

    @FXML
    private TabPane tabPane;

    public HTMLEditor getEditor() {
        return editor;
    }

    public Tab getTab() {
        return tab;
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
