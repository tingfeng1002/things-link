package com.thingslink.config;

import com.thingslink.ThingsLinkException;
import com.thingslink.result.Result;
import com.thingslink.result.ResultCode;
import com.thingslink.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 *
 * @author wang xiao
 * date 2022/12/19
 */
@ControllerAdvice
public class ThingsExceptionHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(ThingsExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = ThingsLinkException.class)
    public ResponseEntity<Result<Void>> thingsLinkException(ThingsLinkException e) {
        Result<Void> jsonResult = Result.error(e);
        LOGGER.error(ExceptionUtil.getExceptionMsg(e));
        return ResponseEntity.ok(jsonResult);
    }


    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Result<Void>> runtimeExceptionHandler(RuntimeException e) {
        Result<Void> jsonResult = Result.error(ResultCode.UNKNOWN_ERROR.getCode(), e.getMessage());
        LOGGER.error(ExceptionUtil.getExceptionMsg(e));
        return ResponseEntity.ok(jsonResult);
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> validationErrorHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors()
                .stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage).orElse("");
        Result<Void> jsonResult = Result.error(ResultCode.PARAM_ERROR.getCode(), message);
        LOGGER.error(ExceptionUtil.getExceptionMsg(e));
        return ResponseEntity.ok(jsonResult);
    }


    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Result<Void>> parameterTypeException(HttpMessageConversionException e) {
        String message = e.getCause().getLocalizedMessage();
        Result<Void> jsonResult = Result.error(ResultCode.PARAM_ERROR.getCode(), message);
        LOGGER.error(ExceptionUtil.getExceptionMsg(e));
        return ResponseEntity.ok(jsonResult);
    }
}
