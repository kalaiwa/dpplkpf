package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Trick;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

public class ToClient_LastTrickMsg implements ToClientMessage {

	private static final long serialVersionUID = 1923535204408433590L;

	public static final String type = "ToClient_LastTrick";

	private String addressee;
	private Trick trick;
	
	public ToClient_LastTrickMsg(String addressee, Trick trick) {
		this.addressee = addressee;
		this.trick = trick;
	}
	
	@Override
	public String getMessage() {
		String output = "Last Trick: \n";
		for (int i = 0; i < 4; i++) {
			Card card = trick.getCards()[i];
			output += (i+1) + ". " + card.getOwner() + " -> " + card.toString() + "\n";
		}
		return output;
	}

	public Trick getTrick() {
		return trick;
	}

	@Override
	public String getAddressee() {
		return addressee;
	}

	@Override
	public String getType() {
		return type;
	}
	
}
