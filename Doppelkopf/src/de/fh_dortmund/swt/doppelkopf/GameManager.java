package de.fh_dortmund.swt.doppelkopf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;
import de.fh_dortmund.swt.doppelkopf.messages.GameManagerMqttCallback;
import de.fh_dortmund.swt.doppelkopf.messages.ToClient_AddCard;
import de.fh_dortmund.swt.doppelkopf.messages.ToClient_LeaderBoardMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToClient_LoginReactionMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToClient_NextPlayerMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToClient_PlayedCardMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToClient_StateMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_LoginMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_LogoutMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_PlayedCardMsg;

public class GameManager {

	private static final Logger logger = Logger.getLogger(GameManager.class);

	private Game game;
	private Client[] clients = new Client[4];
	private static boolean waitingForPlayedCard = false;
	private static int loggedInClients = 0;
	private MqttClient mqttClient;


	public static void main(String[] args) {
		logger.info("Running");
		GameManager instance = new GameManager();
		instance.connect();
		instance.game = new Game();
		while(true) {
			if(loggedInClients == 4) {
				logger.info("All 4 Clients logged in. Starting Game...");
				instance.publishMessage(new ToClient_StateMsg(null, instance.game.nextState()));
				instance.handOutCards();
				while(!instance.game.nextState().equals(State.EVALUATION)) {
					instance.publishMessage(new ToClient_StateMsg(null, instance.game.getCurrentState()));
					logger.info(instance.game.getCurrentState().getStateName());
					instance.round();
				}

				instance.publishMessage(new ToClient_StateMsg(null, instance.game.nextState()));
				instance.publishMessage(new ToClient_LeaderBoardMsg(null, Arrays.stream(instance.clients).map(c -> c.getPlayer()).toArray(Player[]::new)));
				logger.info("Victory points re-party: " + instance.calcVictoryPoints());
				logger.info("Players in re-party: ");
				for(int i = 0; i<4 ; i++) {
					if(instance.clients[i].isRe()) logger.info(instance.clients[i].getPlayer().getName() + "   ");
				}
				logger.error(" \nGame Over.");
				instance.publishMessage(new ToClient_StateMsg(null, instance.game.nextState()));
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



	public int calcVictoryPoints() {
		int victoryPointsRe = 0;
		int pointsRe = 0;
		for(int i = 0; i< 10; i++) {
			Trick trick = game.getRounds()[i];
			boolean winnerIsRe = trick.evaluate().isRe();
			if(winnerIsRe) pointsRe += trick.getPoints();
			if(trick.getPoints()>=40) {
				if(winnerIsRe) victoryPointsRe++;
				else victoryPointsRe--;
			}
			victoryPointsRe+=trick.caughtFoxes();
		}
		if(pointsRe>120) { 
			victoryPointsRe++;
			if(pointsRe>150) {
				victoryPointsRe++;
				if(pointsRe>180) {
					victoryPointsRe++;
					if(pointsRe>210) {
						victoryPointsRe++;
						if(pointsRe==240) {
							victoryPointsRe++;
						}}}}
		} else {
			victoryPointsRe--;
			if(pointsRe<90) { 
				victoryPointsRe--;
				if(pointsRe<60) {
					victoryPointsRe--;
					if(pointsRe<30) {
						victoryPointsRe--;
						if(pointsRe==0) {
							victoryPointsRe--;
						}}}}
		}


		//TODO ANSAGEN PRÜFEN


		return victoryPointsRe;
	}

	public void handOutCards() {
		ArrayList<Card> deck = createDeck();
		Random rand = new Random();
		for(int client = 0; client < 4; client ++) {
			for(int i = 10; i > 0; i--) {
				int idx = rand.nextInt(deck.size());
				Card card = deck.get(idx);
				deck.remove(idx);
				card.setOwner(clients[client]);

				publishMessage(new ToClient_AddCard(clients[client].getId(), card));
			}
		}

	}

	public void addPlayedCard(Card card) {
		game.clientPlaysCard(card);
		game.nextClient();
		publishMessage(new ToClient_PlayedCardMsg(null, game.getCurrentRound()));
		waitingForPlayedCard = false;
	}

	public ArrayList<Card> createDeck(){
		ArrayList<Card> deck = new ArrayList<>();
		for (CardColour colour : CardColour.values()) {
			for (CardValue value : CardValue.values()) {
				deck.add(new Card(colour, value));
				deck.add(new Card(colour, value));
			}
		}
		return deck;
	}

	public void round() {
		game.setActiveClient(game.getStartingClient());
		for(int i = 0; i < 4; i++) {
			waitingForPlayedCard = true;
			publishMessage(new ToClient_NextPlayerMsg(null, clients[game.getActiveClient()].getPlayer().getName()));
			while(waitingForPlayedCard) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logger.info(game.currentRoundToString().toString());

		}
		Client winner = game.getCurrentRound().evaluate();
		logger.info(winner.getPlayer().getName() + " won this round (" + game.getCurrentRound().getPoints() + " pts)");
		for (int i = 0; i < clients.length; i++) {
			if(clients[i].equals(winner)) game.setStartingClient(i);
		}
	}

	public Game getGame() 
	{
		return game;
	}



	public void login(Client client, String username, String password) {
		//TODO if provided credentials match those on DB
		if(loggedInClients>=4) return;
		Player player = new Player(username, password);
		client.setPlayer(player);
		for (int i = 0; i < clients.length; i++) {
			if(clients[i]==null) {
				clients[i] = client;
				break;
			}
		}
		loggedInClients++;
		//TODO Für gespeicherten Client auch noch setzen?
		publishMessage(new ToClient_LoginReactionMsg(client.getId(), player, true));
	}



	public void logout(Client client) {
		int i = 0;
		for (; i < clients.length; i++) {
			if(clients[i].equals(client)) {
				clients[i] = null;
				break;
			}
		}

		//aufrücken
		//TODO prüfen
		for (int j = i; j < clients.length-1; j++) {
			clients[i] = clients[i+1];
		}

	}

	public  void connect() {
		try {
			mqttClient = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			mqttClient.connect();
			mqttClient.setTimeToWait(1000000);
			mqttClient.setCallback(new GameManagerMqttCallback(this));
			mqttClient.subscribe(ToServer_LoginMsg.type);
			mqttClient.subscribe(ToServer_LogoutMsg.type);
			mqttClient.subscribe(ToServer_PlayedCardMsg.type);

		} catch (MqttException e) {
			logger.error("Could not connect to MQTT Broker (" + e.getMessage() + ")");
		}
	}

	public  void publishMessage(Message msg) {
		MqttMessage message = new MqttMessage();

		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos);){
			out.writeObject(msg);
			out.flush();
			byte[] bytes = bos.toByteArray();

			message.setPayload(bytes);
			mqttClient.publish(msg.getType(), message);
		} catch (IOException e) {
			logger.error("Could not serialize Message '" + msg.getMessage() + "': " + e.getMessage());
		} catch (MqttException e) {
			logger.error("Problems while publishing Message '" + msg.getMessage() + "':" + e.getMessage());
		}
	}

}
