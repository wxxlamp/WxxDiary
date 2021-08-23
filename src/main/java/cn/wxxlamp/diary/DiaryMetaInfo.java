package cn.wxxlamp.diary;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

/**
 * 每个月的目录中只有一个meta-info.json文件，里面是一个list集合
 * @author wxxlamp
 * @date 2021/08/22~22:22
 */
@Data
public class DiaryMetaInfo {

    private Integer id;

    /**
     * 创建时间戳，可以拿到很多东西
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 星期
     */
    private Byte week;

    /**
     * 当日心情
     * 1,2,3,4,5
     * 蓝瘦，差，一般，爽，起飞
     */
    private Byte feeling;

    /**
     * 天气
     */
    private Short weather;

    /**
     * 图片链接
     */
    private List<String> imgLinks;

    /**
     * 文本路径
     */
    private String filePath;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
