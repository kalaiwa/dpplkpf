package de.fh_dortmund.swt.doppelkopf.dataexcange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Map;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Trick;
import javafx.scene.control.Button;

public class GameDataModel {
	private ArrayList<Button> buttons;
	private ArrayList<Card> cards;
	private PropertyChangeSupport appSupport;
	private PropertyChangeSupport controllerSupport;
	private int lastPlayedCard;
	private Button lastPressedButton;
	private ArrayList<Button> disabledButtons;
	private Trick currentTrick;
	
	public GameDataModel() {
		appSupport = new PropertyChangeSupport(this);
		disabledButtons = new ArrayList<>();
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
		controllerSupport.firePropertyChange("CardsSetProperty", false, true);
	}
	
	public void setControllerPropertyChangeListener(PropertyChangeListener controllerListener) {
		controllerSupport = new PropertyChangeSupport(this);
		controllerSupport.addPropertyChangeListener(controllerListener);
	}
	
	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		appSupport = new PropertyChangeSupport(this);
		appSupport.addPropertyChangeListener(listener);
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void setMessage(String msg) {
		controllerSupport.firePropertyChange("NewMessageProperty", null, msg);
	}
	
	public void isClientsTurn(boolean isClientsTurn) {
		controllerSupport.firePropertyChange("IsClientsTurnProperty", false, isClientsTurn);
	}
	
	public void playCard(Button button) {
		int i = 0;
		for(Button currentButton : buttons) {
			if(currentButton == button) {
				this.lastPlayedCard = i;
				lastPressedButton = currentButton;
				break;
			}
			else i++;
		}
		appSupport.firePropertyChange("CardPlayedProperty", null, i);
	}
	
	public void addButtons(ArrayList<Button> buttonList) {
		buttons = buttonList;
	}
	
	public ArrayList<Button> getDisabledButtons() {
		return this.disabledButtons;
	}
	
	public Button getLastPressedButton() {
		return this.lastPressedButton;
	}
	
	public void isValidCard(boolean isValid) {
		buttons.remove(lastPlayedCard);
		disabledButtons.add(lastPressedButton);
		controllerSupport.firePropertyChange("IsValidCardProperty", false, isValid);
	}
	
	public void currentTrickChanged(Trick currentTrick) {
		controllerSupport.firePropertyChange("CurrentTrickProperty", null, currentTrick);
	}

	public void nextRoundBegins() {
		controllerSupport.firePropertyChange("NextRoundProperty", false, true);
	}
}
