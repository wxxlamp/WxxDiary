package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.constants.UiText;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.service.WriterPaneService;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.StringUtils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.net.URL;
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

    private final TabPane tabPane = new TabPane();

    private final WriterPaneService writerPaneService = new WriterPaneService(this);

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
        if (selectedItem.getValue().endsWith(UiText.DAY)) {
            TreeItem<String> mouthItem = selectedItem.getParent();
            TreeItem<String> yearItem = mouthItem.getParent();
            DiaryDate date = DiaryDate.builder()
                    .year(Integer.valueOf(StringUtils.subString(yearItem.getValue(), UiText.YEAR)))
                    .mouth(Integer.valueOf(StringUtils.subString(mouthItem.getValue(), UiText.MOUTH)))
                    .day(Integer.valueOf(StringUtils.subString(selectedItem.getValue(), UiText.DAY)))
                    .build();
            writerPaneService.setTabPane(date);
        }
    }

    @FXML
    public void write() {
        writerPaneService.setTabPane(DateUtils.getDate(System.currentTimeMillis()));
    }

    @FXML
    public void changeUrl() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Optional.ofNullable(directoryChooser.showDialog(new Stage())).ifPresent(dir -> {

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
        dirTree.setRoot(writerPaneService.getDiaryDir());
    }
}
