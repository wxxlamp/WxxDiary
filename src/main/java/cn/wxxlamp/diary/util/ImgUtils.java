package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DiaryException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author wxxlamp
 * @date 2021/10/16~14:32
 */
public class ImgUtils {

    private static final String PNG = ".png";

    private static final String JPG = ".jpg";

    public static void buildImgView(String uri, VBox imgPane) {
        // TODO 图片尺寸随着窗体动态变化
        Image image = new Image(uri, imgPane.getWidth() <= 0 ? 260 : imgPane.getWidth() * 0.99, 0L, true, true);
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(image.widthProperty());
        imageView.setId(uri);
        // TODO 增加图片详情功能
        imgPane.getChildren().add(imageView);
    }

    public static List<String> buildCopyImg(String ownerPath, List<String> originalImgLinks) {
        return originalImgLinks.stream().map(uri -> {
            File oldFile;
            try {
                oldFile = new File(new URI(uri));
            } catch (URISyntaxException e) {
                throw new DiaryException(DiaryException.DailyExceptionEnum.SYS_ERROR, e);
            }
            String suffix = uri.indexOf(JPG) == uri.length() - JPG.length() ? JPG : PNG;
            String newImgPath = ownerPath + "_" + PathUtils.buildRandomPath(4) +  suffix;
            copyFile(oldFile, new File(newImgPath));
            return newImgPath;
        }).collect(Collectors.toList());
    }

    private static void copyFile(File oldFile, File newFile) {

        try(InputStream is = new FileInputStream(oldFile);
            OutputStream os =  new FileOutputStream(newFile);
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos =  new BufferedOutputStream(os)) {

            byte[]buffer = new byte[2048];
            int count = bis.read(buffer);
            while(count != -1){
                //使用缓冲流写数据
                bos.write(buffer,0,count);
                //刷新
                bos.flush();
                count = bis.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
