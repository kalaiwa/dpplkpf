package de.fh_dortmund.swt.doppelkopf.enumerations;

public enum State {

	LOBBY(-2, " Lobby "),
	HANDOUT(-1, " Card  Handout "),
	ROUND_1(0, " Round  1  "),
	ROUND_2(1, "  Round  2  "),
	ROUND_3(2, "  Round  3  "),
	ROUND_4(3, "  Round  4  "),
	ROUND_5(4, "  Round  5  "),
	ROUND_6(5, "  Round  6  "),
	ROUND_7(6, "  Round  7  "),
	ROUND_8(7, "  Round  8  "),
	ROUND_9(8, "  Round  9  "),
	ROUND_10(9, "  Round 10  "),
	EVALUATION(10, "  Evaluation  "),
	GAME_OVER(11, "  Game Over  ");
	
	private int roundNo;
	private String stateName;
	
	private State(int round, String stateName) {
		this.roundNo = round;
		this.stateName = stateName;
	}
	
    public State next() {
    	if(this.equals(GAME_OVER)) return LOBBY;
    	return values()[ordinal() + 1];
    }
    
    public int getRoundNo() {
    	return roundNo;
    }
    
    public String getStateName() {
    	return stateName;
    }
	
}
