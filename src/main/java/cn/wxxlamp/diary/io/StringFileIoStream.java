package cn.wxxlamp.diary.io;

import java.io.*;

/**
 * @author wxxlamp
 * @date 2021/08/24~23:00
 */
public class StringFileIoStream {

    /**
     * 将字符串信息写入path中
     * @param content 字符串内容
     * @param path 文件路径
     */
    public void write(String content, String path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从目录中获取字符串
     * @return 月份目录下的元信息
     */
    public String read(String path) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String str;
            while((str=bufferedReader.readLine())!=null){
                sb.append(str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
