package com.liaoin.microservice.provider.circle.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liaoin.microservice.bean.dto.backstage.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 三化融合项目推送服务拦截器
 *
 * @author 张权立
 * @date 2018/7/12 10:42
 */
public class FusionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 1.获取token信息
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        String token = serverHttpRequest.getServletRequest().getParameter("token");
        // 2.获取Redis中的数据
        String json = redisTemplate.boundValueOps("USER_SESSION" + ":" + token).get();
        if (json == null) {
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }
        // 3.解析json数据
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO user = objectMapper.readValue(json, UserDTO.class);
        attributes.put("user", user);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        super.afterHandshake(request,response,wsHandler,exception);
    }
}
