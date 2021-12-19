package cn.wxxlamp.diary.service;

import cn.wxxlamp.diary.controller.MainController;
import cn.wxxlamp.diary.controller.WriterController;
import cn.wxxlamp.diary.io.DiaryInfoIoFacade;
import cn.wxxlamp.diary.model.DiaryInfo;
import cn.wxxlamp.diary.model.DiaryMetaInfo;
import cn.wxxlamp.diary.service.event.EventPublisher;
import cn.wxxlamp.diary.util.DateUtils;
import cn.wxxlamp.diary.util.ImgUtils;
import cn.wxxlamp.diary.util.PathUtils;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wxxlamp
 * @date 2021/12/19~15:03
 */
public class WriterService {

    private final MainController mainComponent;

    private final WriterController writerComponent;

    private final DiaryInfoIoFacade facade;

    public WriterService(MainController mainComponent, WriterController writerComponent,
                         DiaryInfoIoFacade facade) {
        this.mainComponent = mainComponent;
        this.writerComponent = writerComponent;
        this.facade = facade;
    }

    public DiaryInfo getDiaryInfoNotNull(String uri) {
        DiaryInfo diaryInfo = facade.readDiaryInfo(uri);
        if (diaryInfo == null) {
            diaryInfo = DiaryInfo.builder()
                    .metaInfo(DiaryMetaInfo.builder()
                            .week(DateUtils.getWeek(System.currentTimeMillis()))
                            .createTime(System.currentTimeMillis())
                            .updateTime(System.currentTimeMillis())
                            .filePath(PathUtils.getRelativePath(System.currentTimeMillis()))
                            .build())
                    .build();
        }
        return diaryInfo;
    }

    public void removeTab() {
        BorderPane writerPane = mainComponent.getWritePane();
        JFXButton writerButton = mainComponent.getWriteButton();
        if (StatusRecord.REGISTER_TAB.size() == 0) {
            writerPane.setCenter(writerButton);
            StatusRecord.noSet = true;
        }
    }

    public void saveDaily(DiaryInfo diaryInfo) {
        HTMLEditor editor = writerComponent.getEditor();
        ChoiceBox<Pair<String, Byte>> feelingChoice = writerComponent.getFeelingChoice();
        VBox imgPane = writerComponent.getImgPane();
        TreeView<String> dirTree = mainComponent.getDirTree();

        diaryInfo.setContent(editor.getHtmlText());
        diaryInfo.getMetaInfo().setUpdateTime(System.currentTimeMillis());
        Optional.ofNullable(feelingChoice.getValue()).ifPresent(choice -> diaryInfo.getMetaInfo().setFeeling(choice.getValue()));
        List<String> newImgLinks = ImgUtils.buildCopyImg(diaryInfo.getMetaInfo().getFilePath(),
                imgPane.getChildren().stream().map(Node::getId).collect(Collectors.toList()));
        diaryInfo.getMetaInfo().setImgLinks(newImgLinks);
        facade.writeDiaryInfo(diaryInfo, true);
        // 如果是当天的第一次保存，则刷新目录
        if (DateUtils.getDate(diaryInfo.getMetaInfo().getCreateTime())
                .equals(DateUtils.getDate(System.currentTimeMillis())) && StatusRecord.flash) {
            StatusRecord.flash = false;
            EventPublisher.dirUpdatePublisher(dirTree);
        }

    }
}
