package battleship;

import java.util.Scanner;

public class Game {
    private Board[] players;
    private GameStatus gameStatus;

    //TODO: Make placeableShips dynamic. Player can choose how many ships to have (2-6)
    private final ShipType[] placeableShips = {ShipType.AIRCRAFT, ShipType.BATTLESHIP, ShipType.SUBMARINE, ShipType.CRUISER,ShipType.DESTROYER};



    public Game() {
        players = new Board[2];
        this.players[0] = new Board("Player 1");
        this.players[1] = new Board("Player 2");
        gameStatus = GameStatus.PREGAME;
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

            playerShoot(players[0], players[1]);

            if (gameStatus == GameStatus.END) {
                break;
            }

            playerShoot(players[1], players[0]);
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
        board2.outputBoard(GameStatus.GAME);
        System.out.println("---------------------");
        board1.outputBoard(GameStatus.PREGAME);
    }

}
