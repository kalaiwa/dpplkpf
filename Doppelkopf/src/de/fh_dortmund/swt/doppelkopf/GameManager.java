package de.fh_dortmund.swt.doppelkopf;

import java.util.ArrayList;
import java.util.Random;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;
import messages.NextPlayerMsg;
import messages.PlayedCardMessage;

public class GameManager {

	private Game game;
	private Player[] players = new Player[4];
	private static boolean waiting = false;


	public static void main(String[] args) {
		GameManager gm = new GameManager();
		gm.game = new Game();
		gm.players[0] = new Player("Player 1", "pw1" , gm);
		gm.players[1] = new Player("Player 2", "pw3" , gm);
		gm.players[2] = new Player("Player 3", "pw2" , gm);
		gm.players[3] = new Player("Player 4", "pw4" , gm);
		gm.game.nextState();
		gm.handOutCards();
		while(!gm.game.nextState().equals(State.EVALUATION)) {
			System.out.println(" ############### Round " + (gm.game.getCurrentState().getRoundNo()+1) + " ############### ");
			gm.round();
		}
		System.out.println("Siegpunkte Re-Partei: " + gm.calcVictoryPoints());
//		HashMap<Player, Integer> points = new HashMap<>(4);
//		for(int i = 0; i < 10; i++) {
//			Trick round = gm.game.getRounds()[i];
//			Player winner = round.evaluate();
//			int pts = round.getPoints() + points.get(winner);
//			points.put(winner, pts);
//		}
//		Player winner = null;
//		int pts = -1;
//		for (Entry<Player, Integer> entry : points.entrySet()) {
//			if(entry.getValue()>pts)
//		}
		
		
		gm.game.nextState();
		System.out.println("Game Over.");


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
		for(int player = 0; player < 4; player ++) {
			for(int i = 10; i > 0; i--) {
				int idx = rand.nextInt(deck.size());
				Card card = deck.get(idx);
				deck.remove(idx);
				card.setOwner(players[player]);
				players[player].addCard(card);
			}
		}

	}

	public void receiveMessage(Message msg) {
		switch (msg.getMessage()) {
		case "PlayedCard":
			game.playerPlaysCard(((PlayedCardMessage) msg).getCard());
			game.nextPlayer();
			waiting = false;
			break;

		default:
			break;
		}
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
		game.setActivePlayer(game.getStartingPlayer());
		for(int i = 0; i < 4; i++) {
			waiting = true;
			players[game.getActivePlayer()].receiveMessage(new NextPlayerMsg(players[game.getActivePlayer()].getName()));
			while(waiting) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(game.currentRoundToString().toString());
			
		}
		System.out.println(game.getCurrentRound().evaluate().getName() + " won this round");
	}

	public Game getGame() 
	{
		return game;
	}

}
