package com.briup.crmcommon.Intercepter;


import com.briup.crmcommon.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 * 拦截器：implements HandlerInterceptor
 * 根据业务要求
 * 拦截除了登录的其他方法，登录了之后就会生成token，将token携带在请求头上，获取请求头的token数据，验证通过后才能执行操作
 */
@Component//这个时候定义为对象交给spring管理，在配置类里面添加拦截器的时候不用手动new了
public class JwtInterception implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求已拦截");
        //判断请求是否合法，然后访问controller
        //通过用户请求报文获取到用户发送的token字符串  token放在请求头里面
        String token = request.getHeader("token");
        if(token==null){ //这时候系统未登录
            throw new RuntimeException("用户未登录 ，请先登录");
        }else{
            //当用户提供了token
            //4.验证通过。访问controller
            if(JwtUtil.checkSign(token)){
                /*
                这里是实现咨询模块实现的
                实现验证通过后把token存到threadlocal里面
                 */
                Map<String,Object> map = new HashMap<>();
                map.put("userId",JwtUtil.getUserId(token));

                return true;
            }else {
                throw new RuntimeException("token已失效 ，请重新登录");
            }
        }
    }
}
