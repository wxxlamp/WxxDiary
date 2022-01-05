package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.service.StatusRecord;
import cn.wxxlamp.diary.service.WriterService;
import cn.wxxlamp.diary.util.ImgUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import lombok.Getter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static cn.wxxlamp.diary.constants.UiTextConstants.FEELING_MAP;
import static cn.wxxlamp.diary.service.StatusRecord.FEELING_LIST;

/**
 * @author wxxlamp
 * @date 2021/10/11~22:14
 */
@Getter
public class WriterController implements Initializable {

    @FXML
    private Tab rootTab;

    @FXML
    private HTMLEditor editor;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox editMenu;

    @FXML
    private HBox feelingMenu;

    @FXML
    private VBox imgPane;

    @FXML
    private HBox operatePane;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private ChoiceBox<Pair<String, Byte>> feelingChoice;

    private WriterService writerService;

    /**
     * 每个writeTab都对应一个diaryInfo
     */
    private DiaryInfo diaryInfo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
        initPreWidthAndHeight();
        initComponentSetting();
    }

    public void initController(MainController mainController, String title, DiaryDate date, DiaryInfoIoFacade facade) {
        writerService = new WriterService(mainController, this, facade);
        diaryInfo = writerService.getDiaryInfoNotNull(PathUtils.getAbsoluteUri(date));
        rootTab.setText(title);
        editor.setHtmlText(diaryInfo.getContent());
        // 设置心情栏
        feelingChoice.getItems().addAll(FEELING_LIST);
        Optional.ofNullable(diaryInfo.getMetaInfo().getFeeling()).ifPresent(e -> feelingChoice.setValue(new Pair<>(FEELING_MAP[e], e)));
        // 设置图片
        Optional.ofNullable(diaryInfo.getMetaInfo().getImgLinks()).ifPresent(links ->
                links.forEach(uri -> ImgUtils.buildImgView(PathUtils.relativePath2Absolute(uri), imgPane)));
    }

    @FXML
    public void uploadImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Optional.ofNullable(fileChooser.showOpenDialog(new Stage())).ifPresent(file -> ImgUtils.buildImgView(file.toURI().toString(), imgPane));
    }

    @FXML
    public void closeTab() {
        StatusRecord.REGISTER_TAB.remove(rootTab.getText());
        writerService.removeTab();
    }

    @FXML
    public void saveDiaryByMouse() {
        writerService.saveDaily(diaryInfo);
    }

    private void initComponentSetting() {
        feelingChoice.setConverter(new StringConverter<Pair<String,Byte>>() {
            @Override
            public String toString(Pair<String, Byte> object) {
                return object.getKey();
            }

            @Override
            public Pair<String, Byte> fromString(String string) {
                return null;
            }
        });
    }

    /**
     * 绑定尺寸
     */
    private void initCustomProperties() {
        editor.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.7));
        editMenu.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.3));

        feelingMenu.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.1));
        imgPane.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.8).multiply(0.99));
        imgPane.prefWidthProperty().bind(editMenu.widthProperty().multiply(0.99));

        operatePane.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.1));

        feelingChoice.prefWidthProperty().bind(feelingMenu.widthProperty().multiply(0.5));

        uploadButton.prefHeightProperty().bind(operatePane.heightProperty().multiply(0.5));
        saveButton.prefHeightProperty().bind(operatePane.heightProperty().multiply(0.5));
        uploadButton.prefWidthProperty().bind(operatePane.widthProperty().multiply(0.4));
        saveButton.prefWidthProperty().bind(operatePane.widthProperty().multiply(0.3));
        HBox.setMargin(uploadButton, new Insets(10, 10, 10, 30));
        HBox.setMargin(saveButton, new Insets(10, 30, 10, 10));
    }


    private void initPreWidthAndHeight() {
//        imgPane.setPrefWidth(anchorPane.getWidth() * 0.3);
    }
}
