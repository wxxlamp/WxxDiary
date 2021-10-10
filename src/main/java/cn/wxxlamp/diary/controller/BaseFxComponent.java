package cn.wxxlamp.diary.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * 统一管理所有UI控件：
 * <li>fxml</li>
 * <li>硬编码</li>
 * @author wxxlamp
 * @date 2021/10/10~23:18
 */
public class BaseFxComponent {

    @FXML
    protected JFXButton writerButton;

    @FXML
    protected BorderPane writerPane;

    @FXML
    protected TreeView<String> treeView;

    @FXML
    protected ScrollPane scrollPane;

    @FXML
    protected DatePicker datePicker;

    @FXML
    protected VBox menuBox;

    protected final TabPane tabPane = new TabPane();

    public JFXButton getWriterButton() {
        return writerButton;
    }

    public void setWriterButton(JFXButton writerButton) {
        this.writerButton = writerButton;
    }

    public BorderPane getWriterPane() {
        return writerPane;
    }

    public void setWriterPane(BorderPane writerPane) {
        this.writerPane = writerPane;
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }

    public void setTreeView(TreeView<String> treeView) {
        this.treeView = treeView;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public VBox getMenuBox() {
        return menuBox;
    }

    public void setMenuBox(VBox menuBox) {
        this.menuBox = menuBox;
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
