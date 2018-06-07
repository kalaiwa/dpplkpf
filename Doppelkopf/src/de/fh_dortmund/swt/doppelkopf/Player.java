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
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_PlayedCardMsg;

//Hibernate Entity
@SuppressWarnings("resource")
public class Player{

	private static Logger logger = Logger.getLogger(Player.class);

	private String name;
	private String password;
	private int victoryPoints;

	    

	public Player(String name, String password) {
		this.name = name;
		this.password = password;
		this.victoryPoints = 0;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//TODO Hashing Magic
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
}
