package de.fh_dortmund.swt.doppelkopf.enumerations;

public enum Announcement {

	
	NONE(null, null),
	RE(true, 120),
	RE_90(true, 90),
	RE_60(true, 60),
	RE_30(true, 30),
	RE_SCHWARZ(true, 0),
	CONTRA(false, 120),
	CONTRA_90(false, 90),
	CONTRA_60(false, 60),
	CONTRA_30(false, 30),
	CONTRA_SCHWARZ(false, 0);
	
	int points;
	boolean re;
	
	Announcement(Boolean re, Integer noX){
		this.re = re;
		this.points = noX;
	}
	
	
	

}
