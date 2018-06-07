package de.fh_dortmund.swt.doppelkopf.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.Player;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class PlayerMqttCallback implements MqttCallback{
	
	private static Logger logger = Logger.getLogger(Player.class);


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
	case ToPlayer_StateMsg.type:
		if(!(msg instanceof ToPlayer_StateMsg)) return;
		logger.info(msg.getMessage());
		if (((ToPlayer_StateMsg) msg).getState().equals(State.GAME_OVER)) Client.showLogoutPrompt();
		break;
	case ToPlayer_LoginReactionMsg.type:
		if(!(msg instanceof ToPlayer_LoginReactionMsg)) return;
		ToPlayer_LoginReactionMsg loginReaction = (ToPlayer_LoginReactionMsg) msg;
		if(loginReaction.isSuccess())
			Client.setPlayer(loginReaction.getPlayer());
		else Client.showLogoutPrompt();
	case ToPlayer_NextPlayerMsg.type:
		if(!(msg instanceof ToPlayer_NextPlayerMsg)) return;
		ToPlayer_NextPlayerMsg nextPlayerMsg = (ToPlayer_NextPlayerMsg) o;
		String name = nextPlayerMsg.getPlayerName();
		if(name.equals(Client.getPlayer().getName())) Client.chooseCard();
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
	// TODO ?
}

}
