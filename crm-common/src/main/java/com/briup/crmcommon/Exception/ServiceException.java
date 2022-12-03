package com.briup.crmcommon.Exception;


import com.briup.crmcommon.utils.ResultCode;

/**
 * 当service层抛出业务异常信息时，使用该异常对象
 * @Author lining
 * @Date 2022/10/25
 */
public class ServiceException extends RuntimeException{

    //设置属性表示异常的原因
    private ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
    //为了在全局异常处理器中获取异常原因和异常状态码
    public ResultCode getResultCode() {
        return resultCode;
    }
}
