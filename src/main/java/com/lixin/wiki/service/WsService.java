package com.lixin.wiki.service;

import com.lixin.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mao
 */
@Service
public class WsService {

    @Resource
    private WebSocketServer webSocketServer;
    @Async
    public void sendInfo(String message,String logId) {
        MDC.put("LOG_ID",logId);
        webSocketServer.sendInfo(message);
    }

}
