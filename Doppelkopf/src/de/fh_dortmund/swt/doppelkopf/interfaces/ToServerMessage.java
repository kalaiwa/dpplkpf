package de.fh_dortmund.swt.doppelkopf.interfaces;

import de.fh_dortmund.swt.doppelkopf.Client;

public interface ToServerMessage extends Message {
	
	public Client getSender();

}
