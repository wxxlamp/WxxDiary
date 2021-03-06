package cn.wxxlamp.diary.model;

import lombok.*;

/**
 * 日记附加信息，和日记文件
 * @author wxxlamp
 * @date 2021/08/24~00:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class DiaryInfo {

    private DiaryMetaInfo metaInfo;

    private String content;
}
