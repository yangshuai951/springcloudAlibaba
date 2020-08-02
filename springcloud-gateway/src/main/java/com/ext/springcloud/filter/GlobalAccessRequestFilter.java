package com.ext.springcloud.filter;

import com.ext.springcloud.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@Slf4j
@Component
public class GlobalAccessRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //从请求头中取出token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        //未携带token
        if (token == null || token.isEmpty()) {
            // 封装错误信息
            Map<String, Object> responseData = Maps.newHashMap();
            responseData.put("code", 401);
            responseData.put("message", "非法请求");
            responseData.put("cause", "Token is empty");
            try {
                // 将信息转换为 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] data = objectMapper.writeValueAsBytes(responseData);

                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

                // 输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(data);
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                log.error("{}", e);
            }
        }

        //取出token包含的身份(用JWT转换)
        Claims claims = JwtUtil.checkJWT(token);
        if (claims.isEmpty()) {
            // 封装错误信息
            Map<String, Object> responseData = Maps.newHashMap();
            responseData.put("code", 401);
            responseData.put("message", "无效请求");
            responseData.put("cause", "invalid token");
            try {
                // 将信息转换为 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] data = objectMapper.writeValueAsBytes(responseData);

                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

                // 输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(data);
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                log.error("{}", e);
            }
        }

//        // 2. 重写StripPrefix(获取真实的URL)
//        addOriginalRequestUrl(exchange, exchange.getRequest().getURI());
//        String rawPath = exchange.getRequest().getURI().getRawPath();
//        String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/")).skip(1L).collect(Collectors.joining("/"));
//        ServerHttpRequest newRequest = exchange.getRequest().mutate().path(newPath).build();
//        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
//
//        //将现在的request，添加当前身份
//        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization-UserName", userName).build();
//        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
