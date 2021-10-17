package cn.wxxlamp.diary.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author wxxlamp
 * @date 2021/10/16~14:32
 */
public class ImgUtils {

    private static final String PNG = ".png";

    private static final String JPG = ".jpg";

    /**
     * 构件imgView，用户上传图片和开启时读取图片
     * @param uri URI,统一传绝对的
     * @param imgPane VBOX
     */
    public static void buildImgView(String uri, VBox imgPane) {
        // TODO 图片尺寸随着窗体动态变化
        Image image = new Image(uri, imgPane.getWidth() <= 0 ? 260 : imgPane.getWidth() * 0.99, 0L, true, true);
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(image.widthProperty());
        imageView.setId(uri);
        // TODO 增加图片详情功能
        imgPane.getChildren().add(imageView);
    }

    public static List<String> buildCopyImg(String relativePath, List<String> originalImgLinks) {
        return originalImgLinks.stream().map(uri -> {
            if (uri.contains(PathUtils.getDir())) {
                return PathUtils.absolutePath2Relative(uri);
            }
            File oldFile = new File(UriCache.getUri(uri));
            String suffix = uri.indexOf(JPG) == uri.length() - JPG.length() ? JPG : PNG;
            String newRelativePath = relativePath + "_" + PathUtils.buildRandomPath(4) +  suffix;
            String newImgUri = PathUtils.relativePath2Absolute(newRelativePath);
            File newFile = new File(FileUtils.createIfNotExit(UriCache.getUri(newImgUri).getPath()));
            FileUtils.copyFile(oldFile, newFile);
            return newRelativePath;
        }).collect(Collectors.toList());
    }

}
