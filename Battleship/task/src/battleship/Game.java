package battleship;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private ArrayList<Board> players;
    private GameStatus gameStatus;

    private ArrayList<ShipType> placeableShips;


    public Game() {
        players = new ArrayList<>(Arrays.asList(new Board("Player 1"), new Board("Player 2")));
        gameStatus = GameStatus.PREGAME;
    }

    public void addPlaceableShip(ShipType ship) {
        placeableShips.add(ship);
    }

    public void setPlaceableShips(ArrayList<ShipType> ships) {
        placeableShips = ships;
    }

    //TODO: Change it so there is a computer version
    public void prepareGame() {

        for (Board player : players
        ) {
            System.out.println(player.getName() + ", place your ships on the game field");
            for (ShipType s : placeableShips
            ) {
                player.outputBoard(gameStatus);
                addShipInput(s, player);
            }
            player.outputBoard(gameStatus);
            clearConsole();
        }

        //Move to next stage
        startGame();
    }

    public static void clearConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.flush();
    }

    //TODO: Change so it is possible to show output only for 1 player (computer case)
    private void playerShoot(Board player1, Board player2) {
        outputGameBoards(player1, player2);

        System.out.println(player1.getName() + ", it's your turn:");
        Message msg = player2.takeShot();
        System.out.println(msg.getMessage());

        if (msg == Message.ALL_SHIP_SUNK) {
            gameStatus = GameStatus.END;
        } else {
            clearConsole();
        }
    }

    private void startGame() {
        gameStatus = GameStatus.GAME;

        while (gameStatus != GameStatus.END) {

            playerShoot(players.get(0), players.get(1));

            if (gameStatus == GameStatus.END) {
                break;
            }

            playerShoot(players.get(1), players.get(0));
        }
    }

    //We get input from player
    private void addShipInput(ShipType ship, Board player) {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        int[] values = new int[4];
        boolean errorHappened = false;

        System.out.println("Enter the coordinates of the " + ship.getShipName() + " (" + ship.getShipLength() + " cells):");
        do {
            for (int i = 0; i < 2; i++) {
                errorHappened = false;

                if (scanner.hasNext()) {
                    input = scanner.next();
                } else {
                    errorHappened = true;
                }
                input = input.toUpperCase();

                values[i] = input.charAt(0) - 65;
                if (input.length() == 2 && Character.isDigit(input.charAt(1))) {
                    values[i + 2] = Integer.parseInt(input.substring(1, 2)) - 1;
                } else if (input.length() == 3 && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(2))) {
                    values[i + 2] = Integer.parseInt(input.substring(1, 3)) - 1;
                } else {
                    errorHappened = true;
                    break;
                }
            }
            if (!errorHappened) {
                errorHappened = !player.checkShip(values, ship.getShipName(), ship.getShipLength());
            } else {
                System.out.println("Error! Invalid information. Try again:");
            }

        } while (errorHappened);

        player.addShipToBoard(ship, values);
    }

    private void outputGameBoards(Board board1, Board board2) {
        System.out.println("-------Enemy Ships-----");
        board2.outputBoard(GameStatus.GAME);
        System.out.println("--------Your Ships-----");
        board1.outputBoard(GameStatus.PREGAME);
    }

}
