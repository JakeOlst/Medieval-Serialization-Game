import java.util.Scanner;
import java.util.Objects;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class MedievalGame {

  /* Instance Variables */
  private Player player;

  /* Main Method */
  public static void main(String[] args) {
    
    Scanner console = new Scanner(System.in);
    MedievalGame game = new MedievalGame();

    game.player = game.start(console);

    game.addDelay(500);
    System.out.println("\nLet's take a quick look at you to make sure you're ready to head out the door.");
    System.out.println(game.player);

    game.addDelay(1000);
    System.out.println("\nWell, you're off to a good start, let's get your game saved so we don't lose it.");
    game.save();

    game.addDelay(2000);
    System.out.println("We just saved your game...");
    System.out.println("Now we are going to try to load your character to make sure the save worked...");

    game.addDelay(1000);
    System.out.println("Deleting character...");
    String charName = game.player.getName();
    game.player = null;

    game.addDelay(1500);
    game.player = game.load(charName, console);
    System.out.println("Loading character...");

    game.addDelay(2000);
    System.out.println("Now let's print out your character again to make sure everything loaded:");

    game.addDelay(500);
    System.out.println(game.player);
  } // End of main

  /* Instance Methods */
  private Player start(Scanner console) {
    // Add start functionality here
    Player player;

    Art.homeScreen();

    System.out.println("Welcome to the game, adventurer!");
    System.out.println("Are you new around here?");
    System.out.print("Enter 'y' to Load a game, or 'n' to create a new game: ");

    String answer = console.next().toLowerCase();

    while (true) {
      if (answer.equals("y")) {
        System.out.print("Welcome back! Please remind me... What was your name?: ");
        player = load(console.next(), console);
        break;
      } else if (answer.equals("n")) {
        System.out.print("Welcome - it's great to have you here! Now... what is your name?: ");
        String potentialName = console.next();
        while (true) {
          System.out.print("Just to confirm my pronounciation, your name is "+potentialName+"?: ");
          String nameConfirmation = console.next().toLowerCase();
          if (nameConfirmation.equals("y")) {
            break;
          }
          System.out.println("So sorry, can you spell it for me again?");
          potentialName = console.next();
          player = new Player(potentialName);
        }
        player = new Player(console.next());
        break;
      } else {
        System.out.println("Sorry, I do not understand... Are you new around here?");
        System.out.print("Enter 'y' to Load a game, or 'n' to create a new game: ");
        answer = console.next().toLowerCase();
      }
    }
    return player;
  } // End of start

  private void save() {
    // Add save functionality here
    String fileName = player.getName() + ".svr";
    
    try {
      FileOutputStream userSaveFile = new FileOutputStream(fileName);
      ObjectOutputStream playerSaver = new ObjectOutputStream(userSaveFile);
      
      playerSaver.writeObject(this.player);
    } catch (IOException e) {
      System.out.println("Error - Unable to save the game.");
      e.printStackTrace();
    }

  } // End of save

  private Player load(String playerName, Scanner console) {
    // Add load functionality here
    Player loadedPlayer;
    String fileName = playerName + ".svr";
    try {
      FileInputStream userLoadFile = new FileInputStream(fileName);
      ObjectInputStream playerLoader = new ObjectInputStream(userLoadFile);

      loadedPlayer = (Player) playerLoader.readObject();

    } catch(IOException | ClassNotFoundException e) {
      addDelay(1500);
      System.out.println("There was a problem loading your saved character. We will create a new character with the same name.");
      addDelay(2000);
      loadedPlayer = new Player(playerName);
    }
    return loadedPlayer;
  } 

  // Adds a delay to the console so it seems like the computer is "thinking"
  // or "responding" like a human, not instantly like a computer.
  private void addDelay(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}