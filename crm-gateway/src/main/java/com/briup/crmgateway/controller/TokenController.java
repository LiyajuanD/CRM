package com.briup.crmgateway.controller;

import com.briup.crmgateway.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/*
 * @author dell
 */
/*@Controller
@RequestMapping("/gateway")
public class TokenController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public void getToken(){
        String url="http://system/auto/system/login";
        String token = restTemplate.getForObject(url, String.class);

        String userId = JwtUtil.getUserId(token);
        if(token !=null && userId!=null) {
            //redisTemplate.opsForValue().set(userId, token);
            System.out.println(userId+"  sa"+token);
        }
    }
}*/
