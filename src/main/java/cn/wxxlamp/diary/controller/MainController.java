package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.constants.SystemConstants;
import cn.wxxlamp.diary.constants.UiTextConstants;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.service.MainService;
import cn.wxxlamp.diary.service.event.EventPublisher;
import cn.wxxlamp.diary.util.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * @author wxxlamp
 * @date 2021/09/25~16:35
 */
@Getter
public class MainController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton writeButton;

    @FXML
    private BorderPane writePane;

    @FXML
    private TreeView<String> dirTree;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox dirMenu;

    @FXML
    private AnchorPane settingPane;

    @FXML
    private MenuBar settingMenu;

    @FXML
    private MenuItem changeUrlItem;

    @FXML
    private MenuItem changeCryptKey;

    /**
     * FXML不允許单个的tabPane存在，所以这个只能手动new，然后再放到位置上
     */
    private final TabPane tabPane = new TabPane();

    private final MainService mainService = new MainService(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
        initDir();
    }

    @FXML
    private void onTreeViewClicked() {
        TreeItem<String> selectedItem = dirTree.getSelectionModel().getSelectedItem();
        // 触到箭头则是null
        if (selectedItem == null) {
            return;
        }
        if (selectedItem.getValue().endsWith(UiTextConstants.DAY)) {
            TreeItem<String> mouthItem = selectedItem.getParent();
            TreeItem<String> yearItem = mouthItem.getParent();
            DiaryDate date = DiaryDate.builder()
                    .year(Integer.valueOf(Objects.requireNonNull(StringUtils.subString(yearItem.getValue(), UiTextConstants.YEAR))))
                    .mouth(Integer.valueOf(Objects.requireNonNull(StringUtils.subString(mouthItem.getValue(), UiTextConstants.MOUTH))))
                    .day(Integer.valueOf(Objects.requireNonNull(StringUtils.subString(selectedItem.getValue(), UiTextConstants.DAY))))
                    .build();
            mainService.setTabPane(date);
        }
    }

    @FXML
    public void write() {
        mainService.setTabPane(DateUtils.getDate(System.currentTimeMillis()));
    }

    @FXML
    public void changeUrl() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("【注意】更换路径后将索引不到原来的日记");
        Optional.ofNullable(directoryChooser.showDialog(new Stage())).ifPresent(dir -> {
            PropertyUtils.write(SystemConstants.BASE_PATH, dir.toURI().toString());
            PathUtils.setDir(null);
            EventPublisher.dirUpdatePublisher(dirTree);
        });
    }

    @FXML
    public void changeCrypt() {
        // TODO
        Stage stage = new Stage();
        stage.setTitle("change crypt key");
        stage.show();
    }

    @FXML
    public void fileExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择导出路径");
        Optional.ofNullable(directoryChooser.showDialog(new Stage())).ifPresent(dir -> {
            String basePath = PropertyUtils.readValue(SystemConstants.BASE_PATH);
            ZipUtils.enZip(new String[]{basePath + "wd"}, dir.getPath() + File.separator + "wd.zip");
        });
    }

    @FXML
    public void fileImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ZIP", "*.zip")
        );
        Optional.ofNullable(fileChooser.showOpenDialog(new Stage())).ifPresent(file -> {
                String basePath = PropertyUtils.readValue(SystemConstants.BASE_PATH);
                try {
                    ZipUtils.deZip(file, basePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EventPublisher.dirUpdatePublisher(dirTree);
            });

    }

    /**
     * 绑定尺寸
     */
    private void initCustomProperties() {
        dirMenu.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.2));
        writePane.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.8));

        settingPane.prefHeightProperty().bind(dirMenu.heightProperty().multiply(0.05));
        dirTree.prefHeightProperty().bind(dirMenu.heightProperty().multiply(0.95));

        settingMenu.prefWidthProperty().bind(settingPane.widthProperty().multiply(0.4));
        datePicker.prefWidthProperty().bind(settingPane.widthProperty().multiply(0.6));

        writeButton.prefWidthProperty().bind(writePane.widthProperty().multiply(0.2));
        writeButton.prefHeightProperty().bind(writePane.heightProperty().multiply(0.06));
    }

    /**
     * 加载日记
     */
    private void initDir() {
        EventPublisher.dirUpdatePublisher(dirTree);
    }
}
