package cn.wxxlamp.diary.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import lombok.Getter;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author wxxlamp
 * @date 2021/10/11~22:14
 */
@Getter
public class WriterController implements Initializable {

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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
        initComponentSetting();
    }

    @FXML
    public void uploadImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Optional.ofNullable(fileChooser.showOpenDialog(new Stage())).ifPresent(file -> {
            Image image = new Image(file.toURI().toString(), imgPane.getWidth(), 0L, true, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(imgPane.getWidth());
            imageView.setId(file.toURI().toString());
            imgPane.getChildren().add(imageView);
        });
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
        imgPane.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.8));
        imgPane.prefWidthProperty().bind(editMenu.widthProperty());
        operatePane.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.1));

        feelingChoice.prefWidthProperty().bind(feelingMenu.widthProperty().multiply(0.5));

        uploadButton.prefHeightProperty().bind(operatePane.heightProperty().multiply(0.5));
        saveButton.prefHeightProperty().bind(operatePane.heightProperty().multiply(0.5));
        uploadButton.prefWidthProperty().bind(operatePane.widthProperty().multiply(0.3));
        saveButton.prefWidthProperty().bind(operatePane.widthProperty().multiply(0.3));
        HBox.setMargin(uploadButton, new Insets(10, 10, 10, 30));
        HBox.setMargin(saveButton, new Insets(10, 30, 10, 10));
    }
}
