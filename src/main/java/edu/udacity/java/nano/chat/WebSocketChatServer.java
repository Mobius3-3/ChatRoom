package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {
	Logger logger = Logger.getLogger(WebSocketChatServer.class);

	/**
	 * All chat sessions.
	 */
	private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

	private static void sendMessageToAll(String msg) {
		//TODO: add send message method.
		onlineSessions.values().forEach( onlineSession -> {
			try {
				onlineSession.getBasicRemote().sendText(msg);
			}
			catch (IOException e){
				e.printStackTrace();
			}
		});
	}

	/**
	 * Open connection, 1) add session, 2) add user.
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {
		//TODO: add on open connection.
		onlineSessions.put(username + "@" + session.getId(), session);

//        Map<String, Object> validation = new HashMap<>();
//
//        validation.put("online", onlineSessions.size());

		Message message = new Message(username, onlineSessions.size(), "OnOpen", "Enter Now");

		sendMessageToAll(JSON.toJSONString(message));
	}

	/**
	 * Send message, 1) get username and session, 2) send message to all.
	 */
	@OnMessage
	public void onMessage(Session session, String jsonStr) {
		//TODO: add send message.
		Map onMessageMap = JSON.parseObject(jsonStr,Map.class);
		Map <String, String> temp = new HashMap<>();
		for (Object map : onMessageMap.entrySet()){
			temp.put( (String)((Map.Entry)map).getKey(),(String)((Map.Entry)map).getValue() );
		}


//        Message message = JSON.parseObject(jsonStr, Message.class);
		Message newMessage = new Message(temp.get("username"), onlineSessions.size(),"OnMessage", temp.get("msg"));
//        System.out.println("session = " + session + ", jsonStr = " + jsonStr+", newMessage = " + newMessage.getData());
		sendMessageToAll(JSON.toJSONString(newMessage));

	}

	/**
	 * Close connection, 1) remove session, 2) update user.
	 */
	@OnClose
	public void onClose(Session session, @PathParam("username") String username) {
		//TODO: add close connection.
		onlineSessions.remove(username + "@" + session.getId());

//        Map<String, Object> closeMessage = new HashMap<>();
//
//        closeMessage.put("offline", Map.class);

		Message message = new Message("",onlineSessions.size(), "closeMessage","closeMessage");

		sendMessageToAll(JSON.toJSONString(message));
	}

	/**
	 * Print exception.
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

}
