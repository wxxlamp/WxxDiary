package cn.wxxlamp.diary.model;

import com.alibaba.fastjson.JSON;
import lombok.*;

import java.util.List;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class DiaryMetaInfo {

    /**
     * 第几篇文章
     */
    private Integer id;

    /**
     * 创建时间戳，拿到几号，就是文件名
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
     * wd/data/2021/08/23
     */
    private String filePath;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
