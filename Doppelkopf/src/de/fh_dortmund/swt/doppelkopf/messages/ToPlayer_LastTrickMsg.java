package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Trick;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToPlayer_LastTrickMsg implements Message {

	public static final String type = "ToPlayer_LastTrick";
	private Trick trick;
	
	public ToPlayer_LastTrickMsg(Trick trick) {
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
	
}
