package org.jtest.app.controller.websocket;

import org.jtest.app.model.websocket.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Service
public class WebSocketController {  
	  
    public SimpMessagingTemplate template;  
  
    @Autowired  
    public WebSocketController(SimpMessagingTemplate template) {  
        this.template = template;  
    }  
    /**
     * 发送message到前台
     * @param msg
     * @return
     * @throws Exception
     */
    @MessageMapping("/message")  
    @SendToUser("/message")  
    public void userMessage(String userId,String msg) throws Exception {  
		template.convertAndSendToUser(userId, "/message",msg);
    }  
  
}  