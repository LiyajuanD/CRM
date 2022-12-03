package com.briup.crmgateway.filter;


import com.alibaba.fastjson.JSON;
import com.briup.crmgateway.utils.CodeStatus;
import com.briup.crmgateway.utils.JwtTokenUtils;
import com.briup.crmgateway.utils.JwtUtil;
import com.briup.crmgateway.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Description: jwt拦截器
 * @Author: GX Cui
 * @Date 下午2:12 2022/11/17
 */
@Slf4j
@Configuration
public class JwtAuthCheckFilter1 {
    private static final String AUTH_TOKEN_URL = "/login";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String FROM_SOURCE = "from-source";

    @Resource
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Bean
    @Order(-101)
    public GlobalFilter jwtAuthGlobalFilter() {
        return (exchange, chain) -> {

            // renren-fast 自带了 token 认证，所以 Gateway 不需要做登录认证了，跳过 token 验证，转发所有请求。
//            boolean flag = true;
//            if(flag) {
//                return chain.filter(exchange);
//            }

            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            ServerHttpRequest.Builder mutate = serverHttpRequest.mutate();
            String requestUrl = serverHttpRequest.getURI().getPath();
            //输出当前请求的路径
            System.out.println(serverHttpRequest.getPath());

            // 跳过对登录请求的 token 检查。因为登录请求是没有 token 的，是来申请 token 的。

            System.out.println("======"+requestUrl);
            if(AUTH_TOKEN_URL.equals(requestUrl)  // 如果是登录请求，直接转发下去
                    || "/swagger-ui.html".equals(requestUrl)  // 如果是swagger页面
                    || "/v2/api-docs".equals(requestUrl))  // 如果是文档路径
                    {   // 如果是文档路径
                return chain.filter(exchange);
            }

            // 从 HTTP 请求头中获取 JWT 令牌
            String token = getToken(serverHttpRequest);

            System.out.println("网关获取到的token为："+token);
            if (StringUtils.isEmpty(token)) {
                return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_MISSION);
            }
            /*
                新增的操作：
               根据token解析出userid后去redis数据库查找，若已经有登出的操作，即数据库中已经删除了对应的token，这个token就不可用，需要重新登录获取新的token
             */
            //从请求头获取token
            String userId = JwtUtil.getUserId(token);
            //根据token解析出userid后去redis数据库查找，若已经
            assert userId != null;
            String s = redisTemplate.opsForValue().get(userId);
            if(s==null){
                return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_MISSION);
            }
            //如果数据库存在就继续验证
            boolean isTrue = JwtUtil.checkSign(token);

            if(isTrue){
                return chain.filter(exchange.mutate().request(mutate.build()).build());
            }
            return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_CHECK_INFO_FAILED);
        };
    }


    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("token");
        // 如果前端设置了令牌前缀，则裁剪掉前缀（观察是否有前缀）
//        if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer"))
//        {
//            token = token.replaceFirst("Bearer", StringUtils.EMPTY);
//        }
        return token;
    }



    /**
     * 将 JWT 鉴权失败的消息响应给客户端
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ServerHttpResponse serverHttpResponse, CodeStatus codeStatus) {
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        Result responseResult = Result.failure(codeStatus.getCode(), codeStatus.getMsg());

        DataBuffer dataBuffer = serverHttpResponse.bufferFactory()
                .wrap(JSON.toJSONStringWithDateFormat(responseResult, JSON.DEFFAULT_DATE_FORMAT)
                        .getBytes(StandardCharsets.UTF_8));
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }
}
