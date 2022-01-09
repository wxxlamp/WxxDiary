package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DiaryException;
import com.google.common.collect.Lists;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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
    public static void enZip(String[] srcUri, String outDir) throws DiaryException {

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

    /**
     * 解压文件/文件夹
     * @param srcFile 要解压的文件
     * @param destUri 目标地址的uri
     * @return 解压成功则为true
     */
    public static boolean deZip(File srcFile, String destUri) throws IOException {
        if (!srcFile.exists() || !srcFile.getName().endsWith(".zip")) {
            return false;
        }
        boolean flag = false;
        ZipFile zf = new ZipFile(srcFile);
        Enumeration<? extends  ZipEntry> entries = zf.entries();
        ZipEntry entry;
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            System.out.println("解压" + entry.getName());
            String dirPath = UriCache.getUri(destUri).getPath() + File.separator + entry.getName();
            if (entry.isDirectory()) {
                File dir = new File(dirPath);
                flag = dir.exists() || dir.mkdirs();
            } else {
                // 将压缩文件内容写入到这个文件中
                try(InputStream is = zf.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(
                            FileUtils.createIfNotExit(dirPath))) {
                    int count;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((count = is.read(buf)) != -1) {
                        fos.write(buf, 0, count);
                    }
                } catch (IOException e) {
                    throw new DiaryException(DiaryException.DailyExceptionEnum.COMPRESS, e);
                }
            }
        }
        return flag;
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
