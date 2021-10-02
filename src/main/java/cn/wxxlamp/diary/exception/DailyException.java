package cn.wxxlamp.diary.exception;

/**
 * @author wxxlamp
 * @date 2021/10/02~17:50
 */
public class DailyException extends RuntimeException{

    public DailyException(DailyExceptionEnum code) {
        super(code.name());
    }

    public enum DailyExceptionEnum {
        /**
         * 文件打開異常
         */
        FILE_OPEN_ERROR
    }
}
