package com.briup.crmcommon.Exception;


import com.briup.crmcommon.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @Author lining
 * @Date 2022/10/25
 *  * 异常处理器根据aop实现
 *  * 进行全局异常处理器 处理 controller + interceptor的异常
 *  * 验证token不通过抛出异常后 由这个类（全局异常处理器）处理 返回同一格式
 *  * 4.使用的时候需要创建对象交给spring管理 @bean @compont @Component ！！！！
 *  * 5.设置拦截器的拦截规则！！！！！
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception ex){
        //当异常为Service层抛出自定义的类型ServiceException
        if(ex instanceof ServiceException){
            ex.printStackTrace();
            return Result.failure(((ServiceException) ex).getResultCode());
        }
        ex.printStackTrace();
        //当异常为运行时异常，直接返回用户错误的原因，
        return Result.failure(1,ex.getMessage());
    }

}
