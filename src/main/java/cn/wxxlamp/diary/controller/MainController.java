package cn.wxxlamp.diary.controller;

import cn.wxxlamp.diary.constants.UiText;
import cn.wxxlamp.diary.model.DiaryDate;
import cn.wxxlamp.diary.service.WriterPaneService;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author wxxlamp
 * @date 2021/09/25~16:35
 */
public class MainController extends BaseFxComponent implements Initializable {

    private final WriterPaneService writerPaneService = new WriterPaneService(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCustomProperties();
        initDir();
    }

    @FXML
    private void onTreeViewClicked() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
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
        treeView.setRoot(writerPaneService.getDiaryDir());
    }
}
