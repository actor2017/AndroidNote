package com.liaoin.microservice.provider.circle.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liaoin.microservice.bean.dto.backstage.UserDTO;
import com.liaoin.microservice.bean.entity.circle.CircleDiscussion;
import com.liaoin.microservice.bean.entity.circle.CircleLike;
import com.liaoin.microservice.bean.entity.circle.PushMessage;
import com.liaoin.microservice.bean.entity.circle.RegionIdAndUserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 三化融合项目推送服务
 *
 * @author 张权立
 * @date 2018/7/12 10:29
 */
public class FusionWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(FusionWebSocketHandler.class);
    private static int onlineCount = 0;
    private static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, WebSocketSession>> regionMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<RegionIdAndUserId, CopyOnWriteArrayList<PushMessage>> pushMessageMap = new ConcurrentHashMap<>();

    /**
     * 给某个区域内的连接用户发送消息
     *
     * @param regionId 区域编号
     * @param message  消息内容
     * @throws IOException 抛出异常
     */
    public static void sendToRegion(Integer regionId, Integer userId, String message) throws IOException {
        // 1.获取某个区域的用户连接池
        ConcurrentHashMap<Integer, WebSocketSession> userMap = regionMap.get(regionId);
        if (userMap == null || userMap.size() == 0) {
            regionMap = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, WebSocketSession>>();
            logger.info("区域" + regionId + "的用户连接池不存在或为空");
            return;
        }
        // 2.发送推送消息
        for (Map.Entry<Integer, WebSocketSession> entry : userMap.entrySet()) {
            Integer key = entry.getKey();
            if (key.equals(userId)) {
                continue;
            }
            WebSocketSession value = entry.getValue();
            Map<String, Object> map = new HashMap<>(3);
            map.put("type", 1);
            map.put("msg", message);
            map.put("data", null);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);
            value.sendMessage(new TextMessage(json));
        }
    }

    /**
     * 点赞信息生产
     *
     * @param regionIdAndUserIdSet 区域编号和用户编号的集合
     * @param regionIdAndUserId    当前点赞的区域编号和用户编号
     * @param like                 点赞内容
     */
    public static void produceLikeMessage(Set<RegionIdAndUserId> regionIdAndUserIdSet, RegionIdAndUserId regionIdAndUserId, CircleLike like) {
        for (RegionIdAndUserId temp : regionIdAndUserIdSet) {
            // 1.不推送给点赞用户自己
            if (temp.equals(regionIdAndUserId)) {
                continue;
            }
            // 2.创建推送消息对象
            PushMessage pushMessage = new PushMessage();
            pushMessage.setType("1");
            pushMessage.setMessage(like);
            // 3.获取用户的推送池
            CopyOnWriteArrayList<PushMessage> pushMessages = pushMessageMap.get(temp);
            if (pushMessages == null) {
                pushMessages = new CopyOnWriteArrayList<>();
                pushMessageMap.put(temp, pushMessages);
            }
            // 4.将推送消息放入用户的推送池，最近的推送消息放在最前面
            pushMessages.add(0, pushMessage);
        }
    }

    /**
     * 评论信息生产
     *
     * @param regionIdAndUserIdSet 区域编号和用户编号的集合
     * @param regionIdAndUserId    当前评论的区域编号和用户编号
     * @param discussion           评论内容
     */
    public static void produceDiscussionMessage(Set<RegionIdAndUserId> regionIdAndUserIdSet, RegionIdAndUserId regionIdAndUserId, CircleDiscussion discussion) {
        for (RegionIdAndUserId temp : regionIdAndUserIdSet) {
            // 1.不推送给自己
            if (temp.equals(regionIdAndUserId)) {
                continue;
            }
            // 2.创建推送消息对象
            PushMessage pushMessage = new PushMessage();
            pushMessage.setType("2");
            pushMessage.setMessage(discussion);
            // 3.获取用户的推送池
            CopyOnWriteArrayList<PushMessage> pushMessages = pushMessageMap.get(temp);
            if (pushMessages == null) {
                pushMessages = new CopyOnWriteArrayList<>();
                pushMessageMap.put(temp, pushMessages);
            }
            // 4.将推送消息放入用户的推送池，最近的推送消息放在最前面
            pushMessages.add(0, pushMessage);
        }
    }

    /**
     * 推送点赞和评论
     *
     * @param regionIdAndUserIdSet 区域编号和用户编号的集合
     * @param regionIdAndUserId    当前评论的区域编号和用户编号
     * @throws IOException 抛出异常
     */
    public static void push(Set<RegionIdAndUserId> regionIdAndUserIdSet, RegionIdAndUserId regionIdAndUserId) throws IOException {
        for (RegionIdAndUserId temp : regionIdAndUserIdSet) {
            // 1.不推送给点赞用户自己
            if (temp.equals(regionIdAndUserId)) {
                continue;
            }
            // 2.获取用户的消息池
            CopyOnWriteArrayList<PushMessage> pushMessages = pushMessageMap.get(temp);
            // 3.获取用户的连接对象
            ConcurrentHashMap<Integer, WebSocketSession> integerWebSocketSessionConcurrentHashMap = regionMap.get(temp.getRegionId());
            if (integerWebSocketSessionConcurrentHashMap == null) {
                continue;
            }
            WebSocketSession webSocketSession = integerWebSocketSessionConcurrentHashMap.get(temp.getUserId());
            if (webSocketSession == null) {
                continue;
            }
            // 4.封装WebSocket推送文本
            Map<String, Object> map = new HashMap<>(3);
            map.put("type", 2);
            map.put("msg", pushMessages.size() + "条新消息");
            map.put("data", pushMessages);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);
            // 5.发送推送文本
            webSocketSession.sendMessage(new TextMessage(json));
            // 6.清空用户的消息池
            pushMessages.clear();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 1.获取用户信息，用户信息不存在时，关闭连接
        UserDTO user = (UserDTO) session.getAttributes().get("user");
        if (user == null) {
            session.sendMessage(new TextMessage("尚未登录"));
            session.close();
            logger.info("连接失败>>>尚未登录");
            return;
        }
        // 2.用户的区域编号不存在时，关闭连接
        Integer regionId = user.getRegionId();
        if (regionId == null) {
            session.sendMessage(new TextMessage("尚未激活"));
            session.close();
            logger.info("连接失败>>>尚未激活>>>用户编号：" + user.getId());
            return;
        }
        // 3.获取区域连接池
        ConcurrentHashMap<Integer, WebSocketSession> userMap = regionMap.get(user.getRegionId());
        if (userMap == null) {
            // 新建区域连接池
            userMap = new ConcurrentHashMap<>(16);
            regionMap.put(user.getRegionId(), userMap);
        }
        // 4.用户在线时，下线原连接
        if (userMap.containsKey(user.getId())) {
            // 用户在线，下线原连接
            WebSocketSession webSocketSession = userMap.get(user.getId());
            if (webSocketSession != null && webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage("用户离线"));
                webSocketSession.close();
            }
        }
        // 5.存入区域连接池
        userMap.put(user.getId(), session);
        onlineCount++;
        if (session.isOpen()){
            session.sendMessage(new TextMessage("OK>>>区域编号：" + user.getRegionId() + ">>>用户编号：" + user.getId()));
        }
        logger.info("连接成功>>>区域编号：" + user.getRegionId() + ">>>用户编号：" + user.getId() + ">>>当前在线：" + onlineCount);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1.获取用户信息
        UserDTO user = (UserDTO) session.getAttributes().get("user");
        if (user == null) {
            session.sendMessage(new TextMessage("尚未登录"));
            session.close();
            logger.info("连接失败>>>尚未登录");
            return;
        }
        // 2.获取客户端发送信息
        String payload = message.getPayload();
        String getPushMessages = "2";
        // 如果客户端发送2，表示获取当前用户的推送消息列表
        if (getPushMessages.equals(payload)) {
            // 获取用户的推送消息列表
            RegionIdAndUserId regionIdAndUserId = new RegionIdAndUserId();
            regionIdAndUserId.setRegionId(user.getRegionId());
            regionIdAndUserId.setUserId(user.getId());
            CopyOnWriteArrayList<PushMessage> pushMessages = pushMessageMap.get(regionIdAndUserId);
            if (pushMessages == null) {
                return;
            }
            // 封装消息
            Map<String, Object> map = new HashMap<>(3);
            map.put("type", 3);
            map.put("msg", user.getRegionId() + ":" + user.getId());
            map.put("data", pushMessages);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);
            session.sendMessage(new TextMessage(json));
            // 清空数据
            pushMessages.clear();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 1.获取用户信息
        UserDTO user = (UserDTO) session.getAttributes().get("user");
        if (user == null) {
            session.sendMessage(new TextMessage("尚未登录"));
            session.close();
            logger.info("连接失败>>>尚未登录");
            return;
        }
        // 2.关闭session
        if (session.isOpen()) {
            session.close();
            ConcurrentHashMap<Integer, WebSocketSession> userMap = regionMap.get(user.getRegionId());
            userMap.remove(user.getId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 1.没有用户信息时，直接关闭连接
        UserDTO user = (UserDTO) session.getAttributes().get("user");
        if (user == null) {
            return;
        }
        // 2.关闭session
        ConcurrentHashMap<Integer, WebSocketSession> userMap = regionMap.get(user.getRegionId());
        userMap.remove(user.getId());
        onlineCount--;
        logger.info("连接关闭>>>区域编号：" + user.getRegionId() + ">>>用户编号：" + user.getId() + ">>>当前在线：" + onlineCount);
    }


}
