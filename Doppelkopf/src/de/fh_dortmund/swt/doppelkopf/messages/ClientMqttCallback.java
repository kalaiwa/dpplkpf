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
	/**
	 * Handles incoming messages
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		//tries to deserialize MQTT message to an ToClientMessage
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
		// processes message depending on its type
		switch (topic) {
		case ToClient_AddCardMsg.type:
			// if client is the addressee, it adds an card to his hand cards
			if(msg.getAddressee().equals(client.getId())) {
				if(!(msg instanceof ToClient_AddCardMsg)) return;
				client.addCard(((ToClient_AddCardMsg)msg).getCard());
				client.signalNewMessage(msg.getMessage());
				logger.debug(msg.getMessage());
			}
			break;
		case ToClient_StateMsg.type:
			// prints state information, 
			// if last state was a game round adds evaluation info,
			// if current state is game over, prompts Logout choice
			if(!(msg instanceof ToClient_StateMsg)) return;
			State state = ((ToClient_StateMsg) msg).getState();
			if(state.getRoundNo() == 0) client.signalGameStart();
			if (state.getRoundNo()>0 && state.getRoundNo()<11) { 
				client.signalNewMessage(client.getCurrentTrick().toString() +  "  -  " + client.getCurrentTrick().evaluate().getPlayer().getName() + " won this round (" + client.getCurrentTrick().getPoints() + " pts)");
				logger.info(client.getCurrentTrick().toString() +  "  -  " + client.getCurrentTrick().evaluate().getPlayer().getName() + " won this round (" + client.getCurrentTrick().getPoints() + " pts)");
				client.setCurrentTrick(null);
				client.signalNextRound();
			}
			client.signalNewMessage(msg.getMessage());
			logger.info(msg.getMessage());
			//if (state.equals(State.GAME_OVER)) client.logout();
			break;
		case ToClient_LoginReactionMsg.type:
			//if client is the addressee, it either sets its player info to the received ones on success or reprints the login prompt otherwise
			System.out.println("client: " + client + ": " + client.getId());
			System.out.println("msg: " + msg);
			if(msg.getAddressee().equals(client.getId())) {
				if(!(msg instanceof ToClient_LoginReactionMsg)) return;
				ToClient_LoginReactionMsg loginReaction = (ToClient_LoginReactionMsg) msg;
				if(loginReaction.isSuccess()) {
					client.setPlayer(loginReaction.getPlayer());
					client.signalNewMessage(msg.getMessage());
					logger.info(msg.getMessage());
					client.signalLoginSuccessfull(true);
				}
				else client.signalLoginSuccessfull(false);
			}
			break;
		case ToClient_PlayedCardMsg.type:
			//Updates info about current trick
			if(!(msg instanceof ToClient_PlayedCardMsg)) return;
			client.setCurrentTrick(((ToClient_PlayedCardMsg)msg).getTrick());
			client.signalNewMessage(msg.getMessage());
			logger.info(msg.getMessage());
			break;
		case ToClient_NextPlayerMsg.type:
			// if client is next player, prompt card choice lines
			if(!(msg instanceof ToClient_NextPlayerMsg)) return;
			ToClient_NextPlayerMsg nextPlayerMsg = (ToClient_NextPlayerMsg) o;
			String name = nextPlayerMsg.getPlayerName();
			if(name.equals(client.getPlayer().getName())) {
				client.signalNewMessage("It's your turn!");
				client.signalYourTurn(true);
				break;
			}
			else client.signalYourTurn(false);
			client.signalNewMessage(msg.getMessage());
			logger.debug(msg.getMessage());
			break;
		//Only print message text in all other cases
		case ToClient_LeaderBoardMsg.type:
			ToClient_LeaderBoardMsg leaderBoardMsg = (ToClient_LeaderBoardMsg) msg;
			client.signalGameScore(leaderBoardMsg.getMessage());
		case ToClient_LastTrickMsg.type:
			client.signalNewMessage(msg.getMessage());
			logger.info(msg.getMessage());
			break;
		case ToClient_OverallScoreMsg.type:
			if(msg.getAddressee().equals(client.getId())) {
				client.signalLeaderBoard(msg.getMessage());
				logger.info(msg.getMessage());	
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// ?
	}

}
