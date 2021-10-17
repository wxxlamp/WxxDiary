package cn.wxxlamp.diary;

import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PathUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.Objects;

import static cn.wxxlamp.diary.constants.SystemConstants.MAIN;

/**
 * @author wxxlamp
 * @date 2021/08/28~23:51
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage = new Stage();
        stage.setTitle("wxx diary");
        Scene scene = new Scene(FxmlUtils.getNode(MAIN));
        stage.setScene(scene);
        stage.setResizable(false);
        try {
            stage.getIcons().add(new Image(PathUtils.class.getResource("/img/favicon.ico").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
