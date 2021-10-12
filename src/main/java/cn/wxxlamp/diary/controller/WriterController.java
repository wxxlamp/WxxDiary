package cn.wxxlamp.diary.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import lombok.Getter;

import java.net.URL;
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
    private ScrollPane imgPane;

    @FXML
    private HBox operatePane;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private ChoiceBox<String> feelingChoice;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
    }

    /**
     * 绑定尺寸
     */
    private void initCustomProperties() {
        editor.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.7));
        editMenu.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.3));

        feelingMenu.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.1));
        imgPane.prefHeightProperty().bind(editMenu.heightProperty().multiply(0.8));
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
