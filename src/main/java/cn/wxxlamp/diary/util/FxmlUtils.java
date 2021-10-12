package cn.wxxlamp.diary.util;

import com.google.common.collect.Maps;
import javafx.fxml.FXMLLoader;
import lombok.Builder;
import lombok.Data;

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
    public static FXMLLoader getLoader(String fxml, boolean needCache) {
        if (needCache) {
            getNode(fxml);
            return CACHE.get(fxml);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource("/fxml/" + fxml + ".fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fxmlLoader;
        }
    }

    /**
     * 获取xml文件Node
     * @param fxml xml文件
     * @param <T> 对应的RootNodeType
     * @return rootNode
     */
    public static <T> T getNode(String fxml) {
        return getNode(fxml, true);
    }

    /**
     * 获取xml文件Node
     * @param fxml xml文件
     * @param <T> 对应的RootNodeType
     * @param needCache 是否需要缓存，如果不需要缓存，则会重新加载xml文件，同时，不会将该文件写入已有缓存
     * @return rootNode
     */
    public static <T> T getNode(String fxml, boolean needCache) {
        FXMLLoader fxmlLoader = CACHE.get(fxml);
        T node = null;
        if (fxmlLoader == null || !needCache) {
            fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource("/fxml/" + fxml + ".fxml"));
            try {
                node = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (needCache) {
                CACHE.put(fxml, fxmlLoader);
            }
        }
        return node;
    }

}
