package com.holun.servicebase.exceptionhandler;

import com.holun.commonutils.ExceptionUtil;
import com.holun.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * 使用 @ControllerAdvice注解 实现异常处理，只需要定义类，添加该注解即可
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常处理,发生任何异常都会执行该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));  //将异常信息输出到日志文件
        e.printStackTrace();  //打印异常堆栈跟踪信息
        return Result.error().message("执行了全局异常处理..");
    }

    //特定异常处理（发生指定的异常后，执行该方法）
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public Result error(ArithmeticException e) {
        log.error(ExceptionUtil.getMessage(e));  //将异常信息输出到日志文件
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常处理（发生自定义的异常，执行该方法）
    @ExceptionHandler(GuliException.class)
    @ResponseBody //为了返回数据
    public Result error(GuliException e) {
        log.error(ExceptionUtil.getMessage(e));  //将异常信息输出到日志文件
        e.printStackTrace();

        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
