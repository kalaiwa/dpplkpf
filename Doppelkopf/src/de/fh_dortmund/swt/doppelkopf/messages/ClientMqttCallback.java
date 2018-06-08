package de.fh_dortmund.swt.doppelkopf.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

public class ClientMqttCallback implements MqttCallback{

	private static final Logger logger = Logger.getLogger(Client.class);

	private Client client;

	public ClientMqttCallback(Client client) {
		this.client = client;
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.error("Connection to MQTT broker lost!");
		cause.printStackTrace();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Object o = null;
		try(ByteArrayInputStream bis = new ByteArrayInputStream(message.getPayload()); ObjectInputStream in = new ObjectInputStream(bis);) {
			o = in.readObject(); 

		} catch (IOException ex) {
			logger.error("Could not resolve message in topic " + topic);
		}
		if(!(o instanceof ToClientMessage)) {
			logger.error("The delivered object type does not fit into interface ToClientMessage");
			return;
		}
		ToClientMessage msg = (ToClientMessage) o;
		logger.debug(topic);
		switch (topic) {
		case ToClient_AddCard.type:
			if(msg.getAddressee().equals(client.getId())) {
				if(!(msg instanceof ToClient_AddCard)) return;
				client.addCard(((ToClient_AddCard)msg).getCard());
				logger.info(msg.getMessage());
			}
			break;
		case ToClient_StateMsg.type:
			if(!(msg instanceof ToClient_StateMsg)) return;
			logger.info(msg.getMessage());
			client.setCurrentTrick(null);
			if (((ToClient_StateMsg) msg).getState().equals(State.GAME_OVER)) client.showLogoutPrompt();
			break;
		case ToClient_LoginReactionMsg.type:
			if(msg.getAddressee().equals(client.getId())) {
				if(!(msg instanceof ToClient_LoginReactionMsg)) return;
				ToClient_LoginReactionMsg loginReaction = (ToClient_LoginReactionMsg) msg;
				if(loginReaction.isSuccess()) {
					client.setPlayer(loginReaction.getPlayer());
					logger.info(msg.getMessage());
				}
				else client.showLogoutPrompt();
			}
			break;
		case ToClient_PlayedCardMsg.type:
			if(!(msg instanceof ToClient_PlayedCardMsg)) return;
			client.setCurrentTrick(((ToClient_PlayedCardMsg)msg).getTrick());
			logger.info(msg.getMessage());
			break;
		case ToClient_NextPlayerMsg.type:
			if(!(msg instanceof ToClient_NextPlayerMsg)) return;
			ToClient_NextPlayerMsg nextPlayerMsg = (ToClient_NextPlayerMsg) o;
			String name = nextPlayerMsg.getPlayerName();
			if(name.equals(client.getPlayer().getName()))
				client.chooseCard();
		case ToClient_LeaderBoardMsg.type:
		case ToClient_LastTrickMsg.type:
			logger.info(msg.getMessage());
			break;
		default:
			break;
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO ?
	}

}
