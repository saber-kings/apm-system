package cn.saberking.jvav.apm.web.exception.handler;

import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.InvalidExtensionException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

/**
 * @author Saber污妖王
 * TODO: 公共异常处理类
 * @version 0.0.1
 * @date 2020/3/24
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 系统异常，请稍候再试
     *
     * @param e Exception
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(Exception.class)
    public DataResult<Object> handleException(Exception e) {
        log.error("Exception,{}", e.getLocalizedMessage(), e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_ERROR);
    }
    /**
     * 处理自定义业务异常
     *
     * @param e BusinessException
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(BusinessException.class)
    public DataResult<Object> businessException(BusinessException e) {
        log.error("Exception,{}", e.getLocalizedMessage());
        return DataResult.getResult(e.getCode(), e.getDefaultMessage());
    }

    /**
     * 处理validation 框架异常
     *
     * @param e MethodArgumentNotValidException
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}",
                e.getBindingResult().getAllErrors(), e.getLocalizedMessage());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }

    private DataResult<Object> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgArr = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgArr[i] = error.getDefaultMessage();
            log.info("msg={}", msgArr[i]);
            i++;
        }
        return DataResult.getResult(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), msgArr[0]);
    }

    /**
     * 上传文件大小超出范围异常
     *
     * @param e MaxUploadSizeExceededException
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public DataResult<Object> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException, {}, {}", e, e.getLocalizedMessage());
        return DataResult.getResult(BaseResponseCode.UPLOAD_FILE_TOO_LARGE);
    }

    /**
     * 处理文件上传扩展名异常
     *
     * @param e InvalidExtensionException
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(InvalidExtensionException.class)
    public DataResult<Object> invalidExtensionException(InvalidExtensionException e) {
        log.error("InvalidExtensionException, {}, {}", e, e.getLocalizedMessage());
        return DataResult.getResult(BaseResponseCode.FILE_TYPE_ERROR);
    }

    /**
     * 处理数据转换异常
     *
     * @param e HttpMessageNotReadableException
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public DataResult<Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("MaxUploadSizeExceededException, {}, {}", e, e.getLocalizedMessage());
        return DataResult.getResult(602, "传入数据异常");
    }

    /**
     * 处理客户端突然断开连接异常
     *
     * @param e ClientAbortException
     * @return com.saber.king.utils.DataResult
     */
    @ExceptionHandler(ClientAbortException.class)
    public DataResult<Object> clientAbortException(ClientAbortException e) {
        log.error("客户端突然断开连接,ClientAbortException,{}", e.getLocalizedMessage());
        return DataResult.getResult(BaseResponseCode.SYSTEM_ERROR);
    }

}

