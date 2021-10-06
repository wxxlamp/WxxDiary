package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.service.WriterPaneService;
import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PathUtils;
import cn.wxxlamp.diary.util.StringUtils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static cn.wxxlamp.diary.constants.FxmlComponents.WRITER_PANE;
import static cn.wxxlamp.diary.constants.FxmlComponents.WRITER_TAB;

/**
 * @author wxxlamp
 * @date 2021/09/25~16:35
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton writerButton;

    @FXML
    private BorderPane writerPane;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox menuBox;

    private final WriterPaneService writerPaneService = new WriterPaneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
        initDir();
    }

    /**
     * 绑定尺寸
     */
    private void initCustomProperties() {
        writerButton.prefWidthProperty().bind(writerPane.widthProperty().multiply(0.2));
        writerButton.prefHeightProperty().bind(writerPane.heightProperty().multiply(0.06));
        datePicker.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.06));
        datePicker.prefWidthProperty().bind(menuBox.widthProperty());
        treeView.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.54));
        scrollPane.prefHeightProperty().bind(menuBox.heightProperty().multiply(0.40));
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

    @FXML
    private void onTreeViewClicked() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        // 触到箭头则是null
        if (selectedItem == null) {
            return;
        }
        if (selectedItem.getValue().endsWith("号")) {
            TreeItem<String> mouthItem = selectedItem.getParent();
            TreeItem<String> yearItem = mouthItem.getParent();
            String path = PathUtils.getAbsolutePath(Integer.valueOf(StringUtils.subString(yearItem.getValue(), "年")),
                    Integer.valueOf(StringUtils.subString(mouthItem.getValue(), "月")),
                    Integer.valueOf(StringUtils.subString(selectedItem.getValue(), "号")));
            writerPaneService.setTabPane(path, writerPane, writerButton);
        }
    }

    @FXML
    public void write() {
//        FXMLLoader loader = FxmlUtils.getLoader(WRITER_PANE);
//        TabPane tabPane = loader.getRoot();
//        tabPane.getTabs().add(writerPaneService.newTab());
//        writerPane.setCenter(loader.getRoot());
        writerPaneService.setTabPane(PathUtils.getAbsolutePath(System.currentTimeMillis()), writerPane, writerButton);
//        writerButton.setDisable(true);
//        writerButton.setOpacity(0);
    }
}
