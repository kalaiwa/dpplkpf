package de.fh_dortmund.swt.doppelkopf.controller;

import java.util.ArrayList;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Trick;
import de.fh_dortmund.swt.doppelkopf.dataexcange.GameDataModel;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {
	@FXML
	Button card1Button;
	@FXML
	Button card2Button;
	@FXML
	Button card3Button;
	@FXML
	Button card4Button;
	@FXML
	Button card5Button;
	@FXML
	Button card6Button;
	@FXML
	Button card7Button;
	@FXML
	Button card8Button;
	@FXML
	Button card9Button;
	@FXML
	Button card10Button;
	
	@FXML
	Label playedCard1Label;
	@FXML
	Label playedCard2Label;
	@FXML
	Label playedCard3Label;
	@FXML
	Label playedCard4Label;
	
	@FXML
	Label gameLogLabel;
	
	private GameDataModel model;
	
	public GameController(GameDataModel model) {
		this.model = model;
		
		model.setControllerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("CardsSetProperty")) {
				
				ArrayList<Button> buttons = new ArrayList<>();
				buttons.add(card1Button);
				buttons.add(card2Button);
				buttons.add(card3Button);
				buttons.add(card4Button);
				buttons.add(card5Button);
				buttons.add(card6Button);
				buttons.add(card7Button);
				buttons.add(card8Button);
				buttons.add(card9Button);
				buttons.add(card10Button);
				
				model.addButtons(buttons);
				
				ArrayList<Card> cards = model.getCards();
				
				card1Button.setGraphic(new ImageView(getCardImage(0, cards)));
				card2Button.setGraphic(new ImageView(getCardImage(1, cards)));
				card3Button.setGraphic(new ImageView(getCardImage(2, cards)));
				card4Button.setGraphic(new ImageView(getCardImage(3, cards)));
				card5Button.setGraphic(new ImageView(getCardImage(4, cards)));
				card6Button.setGraphic(new ImageView(getCardImage(5, cards)));
				card7Button.setGraphic(new ImageView(getCardImage(6, cards)));
				card8Button.setGraphic(new ImageView(getCardImage(7, cards)));
				card9Button.setGraphic(new ImageView(getCardImage(8, cards)));
				card10Button.setGraphic(new ImageView(getCardImage(9, cards)));
				
				disableButtons(true);
			}
			if(li.getPropertyName().equals("NewMessageProperty")) {
				Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 gameLogLabel.setText((String)li.getNewValue() + "\n" + gameLogLabel.getText());	
	                 }
	             });
			}
			if(li.getPropertyName().equals("IsClientsTurnProperty")) {
				disableButtons(!(boolean)li.getNewValue());
				for(Button b : model.getDisabledButtons()) {
					b.setDisable(true);
				}
			}
			if(li.getPropertyName().equals("IsValidCardProperty")) {
				boolean isValid = (boolean)li.getNewValue();
				if(isValid) {
					Button played = model.getLastPressedButton();
					played.setGraphic(null);
					disableButtons(true);
				}
			}
			if(li.getPropertyName().equals("CurrentTrickProperty")) {
				 Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 Trick currentTrick = (Trick)li.getNewValue();
	     				if(currentTrick != null ) {
	     					ArrayList<Card> cards = new ArrayList<>();
	     					for(Card card : currentTrick.getCards()) {
	     						cards.add(card);
	     					}
	     					if(cards != null) {
	     						playedCard1Label.setGraphic(new ImageView(getCardImage(0, cards)));
	    						playedCard2Label.setGraphic(new ImageView(getCardImage(1, cards)));
	    						playedCard3Label.setGraphic(new ImageView(getCardImage(2, cards)));
	    						playedCard4Label.setGraphic(new ImageView(getCardImage(3, cards)));
	    					}
	     				}	
	                 }
	             });
			}
			if(li.getPropertyName().equals("NextRoundProperty")) {
				Platform.runLater(new Runnable() {
					@Override public void run() {
						playedCard1Label.setGraphic(null);	
						playedCard2Label.setGraphic(null);
						playedCard3Label.setGraphic(null);
						playedCard4Label.setGraphic(null);
					}
				});
			}
		});
	}
	
	private Image getCardImage(int index, ArrayList<Card> cards) {
		if(cards.get(index) == null) return null;
		ClassLoader loader = getClass().getClassLoader();
		String path = "";
		if(cards.get(index).getColour().equals(CardColour.CLUB)) path += "club_";
		System.out.println("Cards: " + cards);
		System.out.println("Colour: " + cards.get(index).getColour());
		System.out.println("CardColout: " + CardColour.CLUB);
		if(cards.get(index).getColour().equals(CardColour.SPADE)) path += "spade_";
		if(cards.get(index).getColour().equals(CardColour.HEART)) path += "hearth_";
		if(cards.get(index).getColour().equals(CardColour.DIAMOND)) path += "diamond_";
		Image cardImage = new Image(loader.getResourceAsStream(path + cards.get(index).getValue().toString() + ".jpg"));
		return cardImage;
	}
	
	private void disableButtons(boolean disable) {
		card1Button.setDisable(disable);
		card2Button.setDisable(disable);
		card3Button.setDisable(disable);
		card4Button.setDisable(disable);
		card5Button.setDisable(disable);
		card6Button.setDisable(disable);
		card7Button.setDisable(disable);
		card8Button.setDisable(disable);
		card9Button.setDisable(disable);
		card10Button.setDisable(disable);
	}
	
	@FXML
	private void handleCardSelectedAction(ActionEvent event) {
		model.playCard((Button)event.getSource());
	}
	
	
}
