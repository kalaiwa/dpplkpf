package de.fh_dortmund.swt.doppelkopf.messages;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.fh_dortmund.swt.doppelkopf.GameManager;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class GameManagerMqttCallback implements MqttCallback {

	private static Logger logger = Logger.getLogger(GameManager.class);
	private GameManager gameManager;
	
	public GameManagerMqttCallback(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.error("Connection to MQTT broker lost!");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Object o = null;
		try(ByteArrayInputStream bis = new ByteArrayInputStream(message.getPayload()); ObjectInputStream in = new ObjectInputStream(bis);) {
			o = in.readObject(); 

		} catch (IOException ex) {
			logger.error("Could not resolve message in topic " + topic);
		}
		Message msg;
		if(!(o instanceof Message)) {
			logger.error("The delivered object type does not fit into interface Message");
			return;
		}
		msg = (Message) o;
		switch (topic) {
		case ToServer_LoginMsg.type:
			if(!(msg instanceof ToServer_LoginMsg)) return;
			ToServer_LoginMsg loginMsg = (ToServer_LoginMsg)msg;
			gameManager.login(loginMsg.getUsername(), loginMsg.getPassword());
			
			
		case Server
			if(!(msg instanceof ToPlayer_StateMsg)) return;
			logger.info(msg.getMessage());
			if (((ToPlayer_StateMsg) msg).getState().equals(State.GAME_OVER)) player.showLogoutPrompt();
		case ToPlayer_NextPlayerMsg.type:
			if(!(msg instanceof ToPlayer_NextPlayerMsg)) return;
			ToPlayer_NextPlayerMsg nextPlayerMsg = (ToPlayer_NextPlayerMsg) o;
			String name = nextPlayerMsg.getPlayerName();
			if(name.equals(player.getName())) player.chooseCard();
		case ToPlayer_LeaderBoardMsg.type:
		case ToPlayer_PlayedCardMsg.type:
		case ToPlayer_LastTrickMsg.type:
			logger.info(msg.getMessage());
			break;
		default:
			break;
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		//TODO ?
	}

}
