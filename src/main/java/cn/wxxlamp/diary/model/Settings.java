package cn.wxxlamp.diary.model;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

/**
 * @author wxxlamp
 * @date 2021/10/16~20:49
 */
@Data
@Builder
public class Settings {

    /**
     * 数据存储的基础路径
     */
    private String basePath;

    /**
     * 加解密秘钥
     */
    private String crypt;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
