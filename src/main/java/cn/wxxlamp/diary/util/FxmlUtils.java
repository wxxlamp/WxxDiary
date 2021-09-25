package cn.wxxlamp.diary.util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * @author wxxlamp
 * @date 2021/09/25~16:12
 */
public class FxmlUtils {

    /**
     * 获取程序加载器
     * @param fxml xml文件
     * @return 加载器
     */
    public static FXMLLoader getLoader(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource("/fxml/" + fxml + ".fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader;
    }

    /**
     * 获取xml文件Node
     * @param fxml xml文件
     * @param <T> 对应的RootNodeType
     * @return rootNode
     */
    public static <T> T getNode(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource("/fxml/" + fxml + ".fxml"));
        T node = null;
        try {
            node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

}
