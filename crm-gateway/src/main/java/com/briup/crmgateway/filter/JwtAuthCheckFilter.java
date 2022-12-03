package com.briup.crmgateway.filter;


import com.alibaba.fastjson.JSON;
import com.briup.crmgateway.utils.CodeStatus;
import com.briup.crmgateway.utils.JwtTokenUtils;
import com.briup.crmgateway.utils.JwtUtil;
import com.briup.crmgateway.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: jwt拦截器
 * @Author: GX Cui
 * @Date 下午2:12 2022/11/17
 */
/*@Slf4j
@Configuration*/
public class JwtAuthCheckFilter {
    private static final String AUTH_TOKEN_URL = "/login";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String FROM_SOURCE = "from-source";

    @Resource
    private JwtTokenUtils jwtTokenUtil;

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
                    || "/v2/api-docs".equals(requestUrl)){   // 如果是文档路径
                return chain.filter(exchange);
            }

            // 从 HTTP 请求头中获取 JWT 令牌
            String token = getToken(serverHttpRequest);
            if (StringUtils.isEmpty(token)) {
                return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_MISSION);
            }
            // 对Token解签名，并验证Token是否过期

            boolean isTrue = JwtUtil.checkSign(token);
            if(isTrue){
                return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_INVALID);
            }

            boolean isJwtNotValid = jwtTokenUtil.isTokenExpired(token);
            if(isJwtNotValid){
                return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_INVALID);
            }
            // 验证 token 里面的 userId 是否为空
            String userId = jwtTokenUtil.getUserIdFromToken(token);
            String username = jwtTokenUtil.getUserNameFromToken(token);
            if (StringUtils.isEmpty(userId)) {
                return unauthorizedResponse(exchange, serverHttpResponse, CodeStatus.TOKEN_CHECK_INFO_FAILED);
            }
            // 设置用户信息到请求，如果没问题，将userName和userId添加到请求头中
            addHeader(mutate, USER_ID, userId);
            addHeader(mutate, USER_NAME, username);
            // 内部请求来源参数清除
            removeHeader(mutate, FROM_SOURCE);
            return chain.filter(exchange.mutate().request(mutate.build()).build());
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
//    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ServerHttpResponse serverHttpResponse, ResponseCodeEnum responseCodeEnum) {
//        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
//        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
//        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//        ResponseResult responseResult = ResponseResult.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
//        DataBuffer dataBuffer = serverHttpResponse.bufferFactory()
//                .wrap(JSON.toJSONStringWithDateFormat(responseResult, JSON.DEFFAULT_DATE_FORMAT)
//                        .getBytes(StandardCharsets.UTF_8));
//        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
//    }

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
