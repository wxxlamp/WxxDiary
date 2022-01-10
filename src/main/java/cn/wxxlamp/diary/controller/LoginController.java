package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.App;
import cn.wxxlamp.diary.constants.SystemConstants;
import cn.wxxlamp.diary.util.EncryptUtils;
import cn.wxxlamp.diary.util.FxmlUtils;
import cn.wxxlamp.diary.util.PropertyUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static cn.wxxlamp.diary.constants.SystemConstants.MAIN;

/**
 * @author wxxlamp
 * @date 2022/01/04~23:21
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton goButton;

    @FXML
    private JFXTextField pwdText;

    private String originEncryptPwd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if ((originEncryptPwd = PropertyUtils.readValue(SystemConstants.CRYPT)) != null) {
            goButton.setText("CHECK");
        }
    }

    @FXML
    public void checkPwd() {
        if (originEncryptPwd != null && !Objects.equals(pwdText.getText(),
                EncryptUtils.decryptWithKey(pwdText.getText(), originEncryptPwd))) {
            pwdText.setText("pwd is error");
            pwdText.setFocusColor(Paint.valueOf("red"));
        } else {
            // 第一次使用
            if (originEncryptPwd == null) {
                if (pwdText.getText() == null) {
                    return;
                }
                PropertyUtils.write(SystemConstants.CRYPT,
                        EncryptUtils.encryptWithKey(pwdText.getText(), pwdText.getText()));
            }
            App.STAGE.setScene(new Scene(FxmlUtils.getNode(MAIN)));
            App.STAGE.centerOnScreen();
        }
    }
}
