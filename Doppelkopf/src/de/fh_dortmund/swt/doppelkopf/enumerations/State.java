package de.fh_dortmund.swt.doppelkopf.enumerations;

public enum State {

	LOBBY(-2),
	HANDOUT(-1),
	ROUND_1(0),
	ROUND_2(1),
	ROUND_3(2),
	ROUND_4(3),
	ROUND_5(4),
	ROUND_6(5),
	ROUND_7(6),
	ROUND_8(7),
	ROUND_9(8),
	ROUND_10(9),
	EVALUATION(10),
	GAME_OVER(11);
	
	private int round;
	
	private State(int round) {
		this.round = round;
	}
	
    public State next() {
    	if(this.equals(GAME_OVER)) return LOBBY;
    	return values()[ordinal() + 1];
    }
    
    public int getRoundNo() {
    	return round;
    }
	
}
