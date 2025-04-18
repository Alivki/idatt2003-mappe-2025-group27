package ntnu.idatt2003.group27.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.view.SceneManager;


public class MainController {

  private ObservableList<Player> playersObservableList = FXCollections.observableArrayList();

  private static MainController instance;
  public SceneManager sceneManager;

  //Controllers
  private BoardGameController boardGameController;

  public MainController() {
    if (instance == null) {
      instance = this;
    }
  }

  public void switchToBoardGame(LadderGameType ladderGameType) {
    boardGameController = new BoardGameController(this);
    boardGameController.InitializeGame(ladderGameType, getPlayerArray());
    sceneManager.switchSceneImmediate(boardGameController.getView().getRoot());
  }

  public void switchToMainMenu(){
    sceneManager.switchToMainMenu();
  }

  public ObservableList<Player> getPlayers(){
    return playersObservableList;
  }

  public Player[] getPlayerArray(){
    Player[] players = new Player[playersObservableList.size()];
    players = playersObservableList.toArray(players);
    return players;
  }

  public void addPlayer(Player player){
    playersObservableList.add(player);
    System.out.println("Player added: " + player.getName());
  }

  public void removePlayer(Player player){
    playersObservableList.remove(player);
  }

  public void setPlayers(Player[] players){
    playersObservableList.clear();
    playersObservableList.addAll(players);
  }

  public static MainController getInstance(){
    return instance;
  }
}
