package cn.wxxlamp.diary.exception;

/**
 * @author wxxlamp
 * @date 2021/10/02~17:50
 */
public class DiaryException extends RuntimeException{

    public DiaryException(DailyExceptionEnum code, Throwable e) {
        super(code.name(), e);
    }

    public DiaryException(DailyExceptionEnum code) {
        super(code.name());
    }

    public enum DailyExceptionEnum {
        /**
         * 文件打开异常
         */
        FILE_OPEN_ERROR,

        /**
         * 系统异常
         */
        SYS_ERROR,

        /**
         * 未找到配置文件
         */
        SETTING_FILE_NOT_FOUND,
    }
}
