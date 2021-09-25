package cn.wxxlamp.diary;

import cn.wxxlamp.diary.util.FxmlUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        Scene scene = new Scene(FxmlUtils.getNode("main"));
        stage.setScene(scene);
        stage.show();
    }


}
