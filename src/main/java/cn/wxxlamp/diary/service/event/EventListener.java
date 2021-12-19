package cn.wxxlamp.diary.service.event;

import cn.wxxlamp.diary.constants.UiTextConstants;
import cn.wxxlamp.diary.controller.MainController;
import cn.wxxlamp.diary.util.PathUtils;
import com.google.common.annotations.Beta;
import com.google.common.eventbus.Subscribe;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Comparator;

/**
 * @author wxxlamp
 * @date 2021/12/19~16:22
 */
public class EventListener {

    @Subscribe
    public void dirUpdateListener(TreeView<String> dirTree) {
        dirTree.setRoot(getDiaryDir());
    }

    /**
     * 获取日记目录树
     * @return 日记树
     */
    public TreeItem<String> getDiaryDir() {
        TreeItem<String> rootItem = new TreeItem<>(UiTextConstants.DIR);
        PathUtils.getSubFileName(PathUtils.getDir()).stream().sorted(Comparator.reverseOrder()).forEach(y -> {
            TreeItem<String> yearItem = new TreeItem<>(y + UiTextConstants.YEAR);
            PathUtils.getSubFileName(PathUtils.getDir() + "/" + y).stream().sorted(Comparator.reverseOrder()).forEach(m -> {
                TreeItem<String> mouthItem = new TreeItem<>(m + UiTextConstants.MOUTH);
                PathUtils.getSubFileName(PathUtils.getDir() + "/" + y + "/" + m).stream().sorted(Comparator.reverseOrder()).forEach(d -> {
                    TreeItem<String> dayItem = new TreeItem<>(d + UiTextConstants.DAY);
                    mouthItem.getChildren().add(dayItem);
                });
                yearItem.getChildren().add(mouthItem);
            });
            rootItem.getChildren().add(yearItem);
        });
        return rootItem;
    }
}
