package de.fh_dortmund.swt.doppelkopf;

import java.util.ArrayList;
import java.util.Scanner;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.Suit;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;
import messages.PlayedCardMessage;

//Hibernate Entity
public class Player{

	private String name;
	private String password;
	private int victoryPoints;
	private transient boolean re = false;
	private transient GameManager manager;
	private transient ArrayList<Card> cards = new ArrayList<>();

	public Player(String name, String password, GameManager manager) {
		this.name = name;
		this.password = password;
		this.manager = manager;
		this.victoryPoints = 0;
	}


	//	public static void main(String args[]) {
	//		
	//		//Kommandozeilenaktion
	//		
	//	}

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

	public boolean isRe() {
		return re;
	}
	public void setRe(boolean re) {
		this.re = re;
	}

	public GameManager getManager() {
		return manager;
	}
	public void setManager(GameManager manager) {
		this.manager = manager;
	}


	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		Card clubsQueen = new Card(CardColour.CLUB, CardValue.QUEEN);
		if(cards.contains(clubsQueen)) re = true;
		this.cards = cards;
	}
	public void addCard(Card card) {
		Card clubsQueen = new Card(CardColour.CLUB, CardValue.QUEEN);
		if(card.equals(clubsQueen)) re = true;
		cards.add(card);
	}

	public Card chooseCard() {
		System.out.println("\n" + name);

		int pos = 1;
		for (Card card : cards) {
			System.out.print(pos + ": " + card.toString() + "    ");
			pos++;
		}
		Scanner keyboard = new Scanner(System.in);
		boolean validCard = false;
		int input = 0;
		Card card = null;
		while(!validCard) {
			System.out.print("\nEnter card position: ");
			input = keyboard.nextInt();
			if(cards.size()>=input && input>0) {
				card = cards.get(input-1);
				validCard = checkSuitToFollow(card);
				if(!validCard) System.out.print("X   Your card sucks!!! ");
			}
			else {
				System.out.print("X   Are you even capable of counting?!");
			}

		}
		cards.remove(input-1);
		manager.receiveMessage(new PlayedCardMessage(card));
		return card;
	}




	public void receiveMessage(Message msg) {
		switch (msg.getMessage()) {
		case "NextPlayer":
			chooseCard();
			break;

		default:
			break;
		}


	}

	public boolean checkSuitToFollow(Card cardToCheck) 
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



}
