package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DiaryException;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩&解压缩工具类
 * @author wxxlamp
 * @date 2022/01/09~15:18
 */
public class ZipUtils {

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * @param srcUri 压缩文件夹URI
     * @param outDir 压缩文件输出流
     * @throws DiaryException 压缩失败会抛出运行时异常
     */
    public static void toZip(String[] srcUri, String outDir) throws DiaryException {

        long start = System.currentTimeMillis();
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outDir))) {

            List<File> sourceFileList = Lists.newArrayListWithExpectedSize(srcUri.length);
            for (String dir : srcUri) {
                File sourceFile = new File(UriCache.getUri(dir));
                sourceFileList.add(sourceFile);
            }
            compress(sourceFileList, zos, null);
            long end = System.currentTimeMillis();
            System.out.println("compress finished, and " + (end - start) + " ms");
        } catch (Exception e) {
            throw new DiaryException(DiaryException.DailyExceptionEnum.COMPRESS);
        }
    }

    private static void compress(List<File> sourceFileList, ZipOutputStream zos, String parentDirName) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        for (File sourceFile : sourceFileList) {
            String name = parentDirName == null ? sourceFile.getName() : parentDirName + File.separator + sourceFile.getName();
            if (sourceFile.isFile()) {
                try(FileInputStream in = new FileInputStream(sourceFile)) {
                    zos.putNextEntry(new ZipEntry(name));
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                    }
                    zos.closeEntry();
                }
            } else {
                File[] files = sourceFile.listFiles();
                if (files == null || files.length == 0) {
                    zos.putNextEntry(new ZipEntry(name));
                    zos.closeEntry();
                } else {
                    compress(Arrays.asList(files), zos, name);
                }
            }
        }
    }
}
