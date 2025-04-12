package ntnu.idatt2003.group27.controllers;


import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.BoardGameGUI;
import ntnu.idatt2003.group27.view.MainMenuGUI;
import ntnu.idatt2003.group27.view.MainMenuMenu;
import ntnu.idatt2003.group27.view.MainMenuView;



public class MainController {

  private ObservableList<Player> playersObservableList = FXCollections.observableArrayList();
  private String[] initArgs;

  public static MainController instance;
  public Stage mainStage;

  public MainController(String[] args) {
    instance = this;
    this.initArgs = args;
  }

  public void switchToBoardGame(){
    BoardGameGUI view = new BoardGameGUI();
    view.start(mainStage);
  }

  /**
   * Initializes the main menu (for application startup)
   */
  public void initializeMainMenu(){
      MainMenuGUI.launchGui(initArgs);
  }

  public void switchToMainMenu(){
    MainMenuGUI view = new MainMenuGUI();
    view.start(mainStage);
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
}
