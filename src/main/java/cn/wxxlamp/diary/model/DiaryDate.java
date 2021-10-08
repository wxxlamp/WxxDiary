package cn.wxxlamp.diary.model;

import lombok.*;

/**
 * 日记的具体日期，前端的渲染
 * @author wxxlamp
 * @date 2021/10/08~23:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DiaryDate {

    private Integer year;

    private Integer mouth;

    private Integer day;
}
