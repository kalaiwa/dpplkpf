package messages;

import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class NextPlayerMsg implements Message {

	String msg;
	String name;
	
	public NextPlayerMsg(String playerName) {
		this.msg = "NextPlayer";
		this.name = playerName;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
	
}
