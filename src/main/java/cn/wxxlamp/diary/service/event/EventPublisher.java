package cn.wxxlamp.diary.service.event;

import com.google.common.eventbus.EventBus;
import javafx.scene.control.TreeView;

/**
 * @author wxxlamp
 * @date 2021/12/19~16:28
 */
public class EventPublisher {

    public static EventBus eventBus = new EventBus();

    public static void dirUpdatePublisher(TreeView<String> dirTree) {
        eventBus.register(new EventListener());
        eventBus.post(dirTree);
    }
}
