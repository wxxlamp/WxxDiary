package cn.wxxlamp.diary;

import lombok.Data;

import java.io.File;

/**
 * @author wxxlamp
 * @date 2021/08/22~22:22
 */
@Data
public class DiaryInfo {

    private Integer id;

    /**
     * 创建时间戳，可以拿到很多东西
     */
    private Long createTime;

    private Long updateTime;

    private Byte week;

    /**
     * 1,2,3,4,5
     * 蓝瘦，差，一般，爽，起飞
     */
    private Byte feeling;

    private Short weather;

    private String links;

    private File content;
}
