package battleship;

import java.util.Scanner;

public class Main {

    //TODO: Add menu with options 1. 2 Players 2. VS Computer
    //TODO: Add after choice an option to select number of ships (2-6) They can be all same type
    /*
    Something like:
    You have selected X ships. Choose:
    
    1. AIRCRAFT - Length = 5
    2. SUBMARINE - Length = 3
        :
        :
    6. Start game - Requires (2-6) ships
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.prepareGame();
    }
}
