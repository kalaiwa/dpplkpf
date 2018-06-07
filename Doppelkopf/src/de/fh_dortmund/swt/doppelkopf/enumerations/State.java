package de.fh_dortmund.swt.doppelkopf.enumerations;

public enum State {

	LOBBY(-2, "Lobby"),
	HANDOUT(-1, "Card Handout"),
	ROUND_1(0, "Round 1"),
	ROUND_2(1, "Round 1"),
	ROUND_3(2, "Round 1"),
	ROUND_4(3, "Round 1"),
	ROUND_5(4, "Round 1"),
	ROUND_6(5, "Round 1"),
	ROUND_7(6, "Round 1"),
	ROUND_8(7, "Round 1"),
	ROUND_9(8, "Round 1"),
	ROUND_10(9, "Round 1"),
	EVALUATION(10, "Round 1"),
	GAME_OVER(11, "Round 1");
	
	private int round;
	private String stateName;
	
	private State(int round, String stateName) {
		this.round = round;
		this.stateName = stateName;
	}
	
    public State next() {
    	if(this.equals(GAME_OVER)) return LOBBY;
    	return values()[ordinal() + 1];
    }
    
    public int getRoundNo() {
    	return round;
    }
    
    public String getStateName() {
    	return stateName;
    }
	
}
