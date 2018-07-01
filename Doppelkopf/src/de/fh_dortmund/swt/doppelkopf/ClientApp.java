package de.fh_dortmund.swt.doppelkopf;

import java.beans.PropertyChangeEvent;

import de.fh_dortmund.swt.doppelkopf.controller.GameController;
import de.fh_dortmund.swt.doppelkopf.controller.GameOverController;
import de.fh_dortmund.swt.doppelkopf.controller.LeaderboardController;
import de.fh_dortmund.swt.doppelkopf.controller.LobbyController;
import de.fh_dortmund.swt.doppelkopf.controller.LoginController;
import de.fh_dortmund.swt.doppelkopf.controller.MenuController;
import de.fh_dortmund.swt.doppelkopf.controller.ScreenController;
import de.fh_dortmund.swt.doppelkopf.dataexcange.GameDataModel;
import de.fh_dortmund.swt.doppelkopf.dataexcange.GameOverDataModel;
import de.fh_dortmund.swt.doppelkopf.dataexcange.LeaderboardDataModel;
import de.fh_dortmund.swt.doppelkopf.dataexcange.LobbyDataModel;
import de.fh_dortmund.swt.doppelkopf.dataexcange.LoginDataModel;
import de.fh_dortmund.swt.doppelkopf.dataexcange.MenuDataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application{
	private Scene scene;
	private ScreenController screenController;
	private Client client;
	private MenuDataModel menuDataModel;
	private LobbyDataModel lobbyDataModel;
	private LoginDataModel loginDataModel;
	private GameDataModel gameDataModel;
	private GameOverDataModel gameOverDataModel;
	private LeaderboardDataModel leaderboardDataModel;
	
	public static void main(String[] args) {
		Application.launch(ClientApp.class);
	}
	
	public ClientApp() {
		client = new Client(li -> {
			if(li.getPropertyName().equals("LoginSuccessfullProperty")) {
				screenController.activate("lobby");
			}
			if(li.getPropertyName().equals("GameStartProperty")) {
				gameDataModel.setCards(client.getCards());
				screenController.activate("game");
			}
			if(li.getPropertyName().equals("NewMessageProperty")) {
				String msg = (String)li.getNewValue();
				gameDataModel.setMessage(msg);
			}
			if(li.getPropertyName().equals("IsValidCardProperty")) {
				gameDataModel.isValidCard((boolean)li.getNewValue());
			}
			if(li.getPropertyName().equals("IsClientsTurnProperty")) {
				gameDataModel.isClientsTurn((boolean)li.getNewValue());
			}
			if(li.getPropertyName().equals("CurrentTrickProperty")) {
				gameDataModel.currentTrickChanged((Trick)li.getNewValue());
			}
			if(li.getPropertyName().equals("NextRoundProperty")) {
				gameDataModel.nextRoundBegins();
			}
			if(li.getPropertyName().equals("GameScoreProperty")) {
				screenController.activate("gameOver");
				gameOverDataModel.setGameScore((String)li.getNewValue());
			}
			if(li.getPropertyName().equals("LeaderboardProperty")) {
				leaderboardDataModel.setGameScore((String)li.getNewValue());
			}
		});
	}
	
	@Override
	public void start(Stage stage){
		try {
			ClassLoader loader = getClass().getClassLoader();
			
			Group root = new Group();
			scene = new Scene(root);
			screenController = new ScreenController(scene);
			prepareDataModels();
			
			FXMLLoader loginLoader = new FXMLLoader();
			loginLoader.setController(new LoginController(loginDataModel));
			loginLoader.setLocation(loader.getResource("LoginScreen.fxml"));
			
			FXMLLoader menuLoader = new FXMLLoader();
			menuLoader.setController(new MenuController(menuDataModel));
			menuLoader.setLocation(loader.getResource("MenuScreen.fxml"));
			
			FXMLLoader lobbyLoader = new FXMLLoader();
			lobbyLoader.setController(new LobbyController(lobbyDataModel));
			lobbyLoader.setLocation(loader.getResource("LobbyScreen.fxml"));
			
			FXMLLoader gameLoader = new FXMLLoader();
			gameLoader.setController(new GameController(gameDataModel));
			gameLoader.setLocation(loader.getResource("GameScreen.fxml"));
			
			FXMLLoader gameOverLoader = new FXMLLoader();
			gameOverLoader.setController(new GameOverController(gameOverDataModel));
			gameOverLoader.setLocation(loader.getResource("GameOverScreen.fxml"));
			
			FXMLLoader leaderboardLoader = new FXMLLoader();
			leaderboardLoader.setController(new LeaderboardController(leaderboardDataModel));
			leaderboardLoader.setLocation(loader.getResource("LeaderboardScreen.fxml"));
			
			screenController.addScreen("login", loginLoader.load());
			screenController.addScreen("menu", menuLoader.load());
			screenController.addScreen("lobby", lobbyLoader.load());
			screenController.addScreen("game", gameLoader.load());
			screenController.addScreen("gameOver", gameOverLoader.load());
			screenController.addScreen("leaderboard", leaderboardLoader.load());

			screenController.activate("menu");
			
			stage.setTitle("Doppelkopf");
		    stage.setScene(scene);
		    stage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void prepareDataModels() {
		menuDataModel = new MenuDataModel();
		menuDataModel.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("StartPressedProperty")) {		
				screenController.activate("login");
			} 
			if(li.getPropertyName().equals("LeaderboardPressedProperty")) {
				client.login("test", "test");
				client.askLeaderboard();
				screenController.activate("leaderboard");
			}
		});
		
		lobbyDataModel = new LobbyDataModel();
		lobbyDataModel.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("CancelPressedProperty")) {
				screenController.activate("menu");
			} 
		});
		
		loginDataModel = new LoginDataModel();
		loginDataModel.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("LoginPressedProperty")) {
				client.login(loginDataModel.getUsername(), loginDataModel.getPassword());
			}
		});
		
		gameDataModel = new GameDataModel();
		gameDataModel.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("CardPlayedProperty")) {
				client.playCard((int)li.getNewValue());
			}
		});
		
		gameOverDataModel = new GameOverDataModel();
		gameOverDataModel.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("BackToMenuProperty")) {
				screenController.activate("menu");
			}
		});
		
		leaderboardDataModel = new LeaderboardDataModel();
		leaderboardDataModel.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("BackToMenuProperty")) {
				client.logout();
				screenController.activate("menu");
			}
		});
	}
}
