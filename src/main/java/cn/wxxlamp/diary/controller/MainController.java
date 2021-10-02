package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static cn.wxxlamp.diary.constants.FxmlComponents.WRITER_PANE;

/**
 * @author wxxlamp
 * @date 2021/09/25~16:35
 */
public class MainController implements Initializable {

    @FXML
    JFXButton writerButton;

    @FXML
    AnchorPane writerPane;

    @FXML
    TreeView<String> treeView;

    @FXML
    public void write() {
        FXMLLoader loader = FxmlUtils.getLoader(WRITER_PANE);
        WriterPaneController writerPaneController = loader.getController();
        writerPaneController.getTab().setText("今日日记");
        writerPane.getChildren().add(loader.getRoot());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 加载日记记录
        TreeItem<String> rootItem = new TreeItem<>("ROOT");
        PathUtils.getSubFileName(PathUtils.getDir()).forEach(y -> {
            TreeItem<String> yearItem = new TreeItem<>(y + "年");
            PathUtils.getSubFileName(PathUtils.getDir() + File.separator + y).forEach(m -> {
                TreeItem<String> mouthItem = new TreeItem<>(m + "月");
                PathUtils.getSubFileName(PathUtils.getDir() + File.separator + y + File.separator + m).forEach(d -> {
                    TreeItem<String> dayItem = new TreeItem<>(d + "号");
                    mouthItem.getChildren().add(dayItem);
                });
                yearItem.getChildren().add(mouthItem);
            });
            rootItem.getChildren().add(yearItem);
        });
        treeView.setRoot(rootItem);
    }
}
