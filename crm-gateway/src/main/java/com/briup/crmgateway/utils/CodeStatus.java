package com.briup.crmgateway.utils;

/**
 * 针对crm项目中出现的问题进行统计
 * 1）网络层面的异常
 * 2）本地服务器的异常
 */
public enum CodeStatus {

    /*以10 开头的为 网络层面的异常*/
    USER_NOT_EXIST(10001,"用户不存在"),
    USER_EXISTED(10002,"用户已存在"),
    DATA_NOT_EXIST(10003,"请求的数据不存在"),
    DATA_EXISTED(10004,"数据已存在，不允许重复添加"),
    TOKEN_INVALID(10005, "token 已过期或验证不正确！"),
    TOKEN_SIGNATURE_INVALID(10006, "无效的签名"),
    TOKEN_EXPIRED(10007, "token 已过期"),
    TOKEN_MISSION(10008, "token 缺失,用户未登录请登录"),
    TOKEN_CHECK_INFO_FAILED(10009, "token 信息验证失败"),
    REFRESH_TOKEN_INVALID(100010, "refreshToken 无效"),


    /*以20 开头的为 服务器的异常 */
    SQL_EXCEPTION(20001,"sql出现异常");




    private int code; // 异常编号
    private String msg; // 异常信息
    private long time; // 异常时间

    CodeStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
        this.time = System.currentTimeMillis();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public long getTime() {
        return time;
    }
}
