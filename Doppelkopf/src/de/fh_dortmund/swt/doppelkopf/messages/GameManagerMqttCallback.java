package de.fh_dortmund.swt.doppelkopf.messages;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.fh_dortmund.swt.doppelkopf.GameManager;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

/**
 * Handles all messages to Srver via the MQTT Broker
 */
public class GameManagerMqttCallback implements MqttCallback {

	private static Logger logger = Logger.getLogger(GameManager.class);
	private GameManager gameManager;
	
	public GameManagerMqttCallback(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.error("Connection to MQTT broker lost!");
		cause.printStackTrace();
	}

	@Override
	/**
	 * Handles incoming messages
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		//tries to deserialize MQTT message to an ToServerMessage
		Object o = null;
		try(ByteArrayInputStream bis = new ByteArrayInputStream(message.getPayload()); ObjectInputStream in = new ObjectInputStream(bis);) {
			o = in.readObject(); 

		} catch (IOException ex) {
			logger.error("Could not resolve message in topic " + topic);
		}
		Message msg;
		if(!(o instanceof ToServerMessage)) {
			logger.error("The delivered object type does not fit into interface ToServerMessage");
			return;
		}
		msg = (ToServerMessage) o;
		// processes message depending on its type
		
		switch (topic) {
		case ToServer_LoginMsg.type:
			// redirects login request to GameManager
			if(!(msg instanceof ToServer_LoginMsg)) return;
			ToServer_LoginMsg loginMsg = (ToServer_LoginMsg)msg;
			gameManager.login(loginMsg.getSender(), loginMsg.getUsername(), loginMsg.getPassword());
			logger.info(msg.getMessage());
			break;
		case ToServer_LogoutMsg.type:
			// redirects logout request to GameManager
			if(!(msg instanceof ToServer_LogoutMsg)) return;
			gameManager.logout(((ToServer_LogoutMsg)msg).getSender());
			logger.info(msg.getMessage());
			break;
		case ToServer_PlayedCardMsg.type:
			// redirects card info to GameManager
			if(!(msg instanceof ToServer_PlayedCardMsg)) return;
			gameManager.addPlayedCard(((ToServer_PlayedCardMsg)msg).getCard());
			logger.info(msg.getMessage());
			break;
		case ToServer_LeaderBoardMsg.type:
			// redirects card info to GameManager
			ToServer_LeaderBoardMsg leaderboardMsg = (ToServer_LeaderBoardMsg) msg;
			gameManager.sendLeaderboard(leaderboardMsg.getSender());
			logger.info(leaderboardMsg.getMessage());
			break;
		case ToServer_EnterLobbyMsg.type:
			ToServer_EnterLobbyMsg enterLobbyMsg = (ToServer_EnterLobbyMsg) msg;
			gameManager.enterLobby(enterLobbyMsg.getSender());
			logger.info(enterLobbyMsg.getMessage());
			break;
		case ToServer_LeaveLobbyMsg.type:
			ToServer_LeaveLobbyMsg leaveLobbyMsg = (ToServer_LeaveLobbyMsg) msg;
			gameManager.leaveLobby(leaveLobbyMsg.getSender());
			logger.info(leaveLobbyMsg.getMessage());
			break;
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// ?
	}

}
