package de.fh_dortmund.swt.doppelkopf;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.Suit;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;
import de.fh_dortmund.swt.doppelkopf.messages.PlayerMqttCallback;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_LastTrickMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_LeaderBoardMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_LoginReactionMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_NextPlayerMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_PlayedCardMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_StateMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_LoginMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_PlayedCardMsg;

public class Client {

	private static final Logger logger = Logger.getLogger(Client.class);

	private static boolean loggedIn = false;
	private static boolean re = false;
	private static GameManager manager;
	private static ArrayList<Card> cards = new ArrayList<>();
	private static MqttClient client;
	private static Player me;
	private static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		connect();
		showLoginPrompt();
		while(me==null) {
			try { Thread.sleep(100); } catch (InterruptedException e) { }
		}
		loggedIn = true;
		while(loggedIn) {
			//TODO
		}
	}

	public static boolean isRe() {
		return re;
	}
	public static void setRe(boolean re) {
		Client.re = re;
	}

	public static GameManager getManager() {
		return manager;
	}
	public static void setManager(GameManager manager) {
		Client.manager = manager;
	}


	public static ArrayList<Card> getCards() {
		return cards;
	}
	public static void setCards(ArrayList<Card> cards) {
		Card clubsQueen = new Card(CardColour.CLUB, CardValue.QUEEN);
		if(cards.contains(clubsQueen)) re = true;
		Client.cards = cards;
	}
	public static void addCard(Card card) {
		Card clubsQueen = new Card(CardColour.CLUB, CardValue.QUEEN);
		if(card.equals(clubsQueen)) re = true;
		cards.add(card);
	}


	public static Card chooseCard() {
		int pos = 1;
		for (Card card : cards) {
			logger.info(pos + ": " + card.toString() + "    ");
			pos++;
		}
		boolean validCard = false;
		int input = 0;
		Card card = null;
		while(!validCard) {
			logger.info("\nEnter card position: ");
			input = keyboard.nextInt();
			if(cards.size()>=input && input>0) {
				card = cards.get(input-1);
				validCard = checkSuitToFollow(card);
				if(!validCard) System.out.print("X   Your card sucks!!! ");
			}
			else {
				logger.info("X   Are you even capable of counting?!");
			}

		}
		cards.remove(input-1);
		manager.receiveMessage(new ToServer_PlayedCardMsg(card));
		return card;
	}

	public static void publishMessage(Message msg) {
		//TODO
	}

	public static boolean login() {
		showLoginPrompt();
		return true;
	}

	public static void logout() {
		try {
			client.disconnect();
		} catch (MqttException e) { }
		keyboard.close();
	}

	public static void showLoginPrompt() {

		String usernameAttempt = null; 
		logger.info("Please enter your username: ");
		while(usernameAttempt==null)
			usernameAttempt = keyboard.nextLine(); //TODO NextLine or next?

		String pwAttempt = null;
		while(pwAttempt ==null) {
			logger.info("Please enter your password: ");
			pwAttempt = keyboard.nextLine(); //TODO NextLine or next?
		}
				publishMessage(new ToServer_LoginMsg(usernameAttempt, pwAttempt));
	}

	public static void showLogoutPrompt() {
		String logout = "";
		logger.info("Do you want to logout? [y/n]");
		while(logout.equals("y") && logout.equals("n")) {
			logout = keyboard.next();
		}
		if(logout.equals("y")) logout();
	}


	public static boolean checkSuitToFollow(Card cardToCheck) 
	{
		Suit suit = manager.getGame().getCurrentRound().getSuitToFollow();

		if(suit==null) return true;

		if(cardToCheck.getSuit().equals(suit)) return true;

		else {
			for (Card card : cards) {
				if(suit.equals(card.getSuit()))
					return false;
			}
			return true;
		}
	}

	public static void connect() {
		try {
			client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			client.connect();
			client.setCallback(new PlayerMqttCallback());
			client.subscribe(ToPlayer_LastTrickMsg.type);
			client.subscribe(ToPlayer_LeaderBoardMsg.type);
			client.subscribe(ToPlayer_LoginReactionMsg.type);
			client.subscribe(ToPlayer_NextPlayerMsg.type);
			client.subscribe(ToPlayer_PlayedCardMsg.type);
			client.subscribe(ToPlayer_StateMsg.type);
		} catch (MqttException e) {
			logger.error("Could not connect to MQTT Broker (" + e.getMessage() + ")");
		}
	}

	public static Player getPlayer() {
		return me;
	}
	public static void setPlayer(Player player) {
		me = player;
	}


}
