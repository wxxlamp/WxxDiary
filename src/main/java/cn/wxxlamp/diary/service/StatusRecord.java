package cn.wxxlamp.diary.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import javafx.scene.control.Tab;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

import static cn.wxxlamp.diary.constants.UiTextConstants.FEELING_MAP;

/**
 * 存储各种状态信息
 * @author wxxlamp
 * @date 2021/12/19~14:57
 */
public class StatusRecord {

    public static final Map<String, Tab> REGISTER_TAB = Maps.newHashMapWithExpectedSize(16);

    /**
     * 是否切换编辑面板
     */
    public static Boolean noSet = true;
    /**
     * 是否刷新目录
     */
    public static boolean flash = true;

    /**
     * 用户心情表
     */
    public static final List<Pair<String, Byte>> FEELING_LIST = ImmutableList.of(
            new Pair<>(FEELING_MAP[0], (byte)0),
            new Pair<>(FEELING_MAP[1], (byte)1),
            new Pair<>(FEELING_MAP[2], (byte)2),
            new Pair<>(FEELING_MAP[3], (byte)3),
            new Pair<>(FEELING_MAP[4], (byte)4),
            new Pair<>(FEELING_MAP[5], (byte)5));

}
