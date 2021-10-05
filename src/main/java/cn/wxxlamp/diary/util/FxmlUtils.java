package cn.wxxlamp.diary.util;

import com.google.common.collect.Maps;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Map;

/**
 * @author wxxlamp
 * @date 2021/09/25~16:12
 */
public class FxmlUtils {

    private static final Map<String, FXMLLoader> CACHE = Maps.newHashMap();
    /**
     * 获取程序加载器
     * @param fxml xml文件
     * @return 加载器
     */
    public static FXMLLoader getLoader(String fxml) {
        getNode(fxml);
        return CACHE.get(fxml);
    }

    /**
     * 获取xml文件Node
     * @param fxml xml文件
     * @param <T> 对应的RootNodeType
     * @return rootNode
     */
    public static <T> T getNode(String fxml) {
        FXMLLoader fxmlLoader = CACHE.get(fxml);
        T node = null;
        if (fxmlLoader == null) {
            fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource("/fxml/" + fxml + ".fxml"));
            try {
                node = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CACHE.put(fxml, fxmlLoader);
        }
        return node;
    }

}
