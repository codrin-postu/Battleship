package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Game game = new Game();
        MainMenu(game);
    }

    public static void MainMenu(Game game) {

        System.out.println("-----Main Menu-----");
        System.out.println("1. 2 Players Mode");
        System.out.println("2. Computer Mode");
        System.out.println("0. Exit");

        int answer = getPlayerChoice();

        switch (answer) {
            case 0:
                return;
            case 1:
                ShipModeSelection(game);
                break;
            case 2:
                System.out.println("Not completed!");
                MainMenu(game);
                break;
            default:
                System.out.println("Error: Invalid input!");
                MainMenu(game);
        }
    }

    public static void ShipModeSelection(Game game) {

        System.out.println("-----Player vs Player-----");
        System.out.println("1. Default Mode");
        System.out.println("2. Custom Ship Mode");
        System.out.println("0. Return");

        int answer = getPlayerChoice();

        switch (answer) {
            case 0:
                MainMenu(game);
                break;
            case 1:
                ArrayList<ShipType> ships = new ArrayList<ShipType>(Arrays.asList(ShipType.AIRCRAFT, ShipType.BATTLESHIP, ShipType.SUBMARINE, ShipType.CRUISER, ShipType.DESTROYER));
                ShipSelection(game, ships, 1);
                break;
            case 2:
                ships = new ArrayList<>();
                ShipSelection(game, ships);
                break;
            default:
                System.out.println("Error: Invalid input!");
                ShipModeSelection(game);
        }
    }

    private static void ShipSelection(Game game, ArrayList<ShipType> ships) {

        System.out.println("-----Ship Selection-----");
        System.out.println("--You have " + ships.size() + " ships selected--");
        System.out.println("1. Aircraft - Length 5");
        System.out.println("2. Battleship - Length 4");
        System.out.println("3. Cruiser - Length 3");
        System.out.println("4. Submarine - Length 3");
        System.out.println("5. Destroyer - Length 2");
        System.out.println("6. Start Game - Must have between 2 - 6 Ships");
        System.out.println("0. Return");

        int answer = getPlayerChoice();

        switch (answer) {
            case 0:
                ShipModeSelection(game);
                break;
            case 1:
                ships.add(ShipType.AIRCRAFT);
                ShipSelection(game, ships);
                break;
            case 2:
                ships.add(ShipType.BATTLESHIP);
                ShipSelection(game, ships);
                break;
            case 3:
                ships.add(ShipType.CRUISER);
                ShipSelection(game, ships);
                break;
            case 4:
                ships.add(ShipType.SUBMARINE);
                ShipSelection(game, ships);
                break;
            case 5:
                ships.add(ShipType.DESTROYER);
                ShipSelection(game, ships);
                break;
            case 6:
                if (ships.size() >= 2 && ships.size() <= 6) {
                    game.setPlaceableShips(ships);
                    game.prepareGame();

                } else {
                    System.out.println("You must have between 2 and 6 ships");
                    ShipSelection(game, ships);
                }
                break;
            default:
                System.out.println("Error: Invalid input!");
                ShipModeSelection(game);
        }
    }

    //TODO: Add multiple templates (5 ships - default) 7 Ships (3 2L, 4 3L)
    private static void ShipSelection(Game game, ArrayList<ShipType> ships, int shipTemplate) {
        game.setPlaceableShips(ships);
        game.prepareGame();
    }

    private static int getPlayerChoice() {
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Error: Invalid input! (Only numbers)");
        }
        return scanner.nextInt();
    }

}
