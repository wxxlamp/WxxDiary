package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private JFXButton writerButton;

    @FXML
    private AnchorPane writerPane;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox menuBox;

    @FXML
    public void write() {
        FXMLLoader loader = FxmlUtils.getLoader(WRITER_PANE);
        WriterPaneController writerPaneController = loader.getController();
        writerPaneController.getTab().setText("今日日记");
        writerPane.getChildren().add(loader.getRoot());
        writerButton.setDisable(true);
        writerButton.setOpacity(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
        initDir();

    }

    /**
     * 绑定尺寸
     */
    private void initCustomProperties() {
//        menuBox.prefWidthProperty().bind(writerButton.widthProperty().divide(7).multiply(3));
        writerButton.prefWidthProperty().bind(menuBox.widthProperty());
        writerButton.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.06));
        datePicker.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.06));
        datePicker.prefWidthProperty().bind(menuBox.widthProperty());
        treeView.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.54));
        scrollPane.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.34));
    }

    /**
     * 加载日记
     */
    private void initDir() {
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
