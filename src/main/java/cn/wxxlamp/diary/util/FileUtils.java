package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DiaryException;

import java.io.*;

/**
 * @author wxxlamp
 * @date 2021/10/16~20:44
 */
public class FileUtils {

    /**
     * 如果该path下的目录不存在，则创建目录以及对应文件
     * @param path 目录
     * @return path
     */
    public static File createIfNotExit(String path) {
        File subFile = new File(path), parentFile;
        boolean success = true;
        if (!subFile.exists()) {
            if (!(parentFile = subFile.getParentFile()).exists()) {
                success = parentFile.mkdirs();
            }
            try {
                success = subFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!success) {
            throw  new DiaryException(DiaryException.DailyExceptionEnum.FILE_OPEN_ERROR);
        }
        return subFile;
    }

    public static void copyFile(File oldFile, File newFile) {

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
