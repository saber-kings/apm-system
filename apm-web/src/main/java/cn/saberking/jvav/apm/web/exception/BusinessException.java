package cn.saberking.jvav.apm.web.exception;

import cn.saberking.jvav.apm.web.exception.code.ResponseCodeInterface;
import lombok.Getter;

/**
 * @author Saber污妖王
 * TODO: 自定义业务异常类
 * @version 0.0.1
 * @date 2020/3/24
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 异常 code
     */
    private final int code;

    /**
     * 异常提示
     */
    private final String defaultMessage;

    public BusinessException(int code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    /**
     * 构造函数
     *
     * @param code 异常码
     */
    public BusinessException(ResponseCodeInterface code) {
        this(code.getCode(), code.getMsg());
    }
}
