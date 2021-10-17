package cn.wxxlamp.diary.util;

import cn.wxxlamp.diary.exception.DiaryException;
import com.google.common.collect.Maps;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author wxxlamp
 * @date 2021/10/17~16:05
 */
public class UriCache {

    private static final Map<String, URI> CACHE = Maps.newHashMapWithExpectedSize(10);

    public static URI getUri(String uri) {
        URI ans = CACHE.get(uri);
        if (ans == null) {
            try {
                ans = new URI(uri);
                CACHE.put(uri, ans);
            } catch (URISyntaxException e) {
                throw new DiaryException(DiaryException.DailyExceptionEnum.SYS_ERROR, e);
            }
        }
        return ans;
    }
}
