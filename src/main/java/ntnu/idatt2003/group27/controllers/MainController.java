package ntnu.idatt2003.group27.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.view.SceneManager;


public class MainController {

  private ObservableList<Player> playersObservableList = FXCollections.observableArrayList();
  private String[] initArgs;

  private static MainController instance;
  public SceneManager sceneManager;
  public Stage mainStage;

  //GUI
  private BoardGameController boardGameController;

  public MainController(String[] args) {
    if (instance == null) {
      instance = this;
      this.initArgs = args;
    }
  }

  public void switchToBoardGame(LadderGameType ladderGameType) {
    boardGameController = new BoardGameController();
    boardGameController.InitializeGame(ladderGameType);
    sceneManager.switchSceneImmediate(boardGameController.getView().getRoot());
  }

  public void switchToMainMenu(){
    sceneManager.switchToMainMenu();
  }

  public ObservableList<Player> getPlayers(){
    return playersObservableList;
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
