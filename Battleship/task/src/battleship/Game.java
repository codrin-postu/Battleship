package battleship;

import java.util.Scanner;

public class Game {
    private Board board1, board2;
    private GameStatus gameStatus;

    private final ShipType[] placeableShips = {ShipType.AIRCRAFT, ShipType.BATTLESHIP, ShipType.SUBMARINE, ShipType.CRUISER, ShipType.DESTROYER};
    ;


    public Game() {
        this.board1 = new Board("Player 1");
        this.board2 = new Board("Player 2");
        gameStatus = GameStatus.PREGAME;
    }

    public void prepareGame() {

        System.out.println(board1.getName() + ", place your ships on the game field");
        for (ShipType s : placeableShips
        ) {
            board1.outputBoard(gameStatus);
            board1.addShip(s);
        }
        board1.outputBoard(gameStatus);
        clearConsole();

        System.out.println(board2.getName() + ", place your ships on the game field");
        for (ShipType s : placeableShips) {
            board2.outputBoard(gameStatus);
            board2.addShip(s);
        }

        board2.outputBoard(gameStatus);
        clearConsole();

        //Move to next stage
        startGame();
    }

    public static void clearConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void startGame() {
        gameStatus = GameStatus.GAME;

        while (gameStatus != GameStatus.END) {
            outputGameBoards(board1, board2);

            System.out.println(board1.getName() + ", it's your turn:");
            Message msg = board2.takeShot();
            if (msg == Message.ALL_SHIP_SUNK) {
                gameStatus = GameStatus.END;
            }
            System.out.println(msg.getMessage());
            clearConsole();

            if (gameStatus == GameStatus.END){
                break;
            }

            outputGameBoards(board2, board1);

            System.out.println(board2.getName() + ", it's your turn:");
            msg = board1.takeShot();
            if (msg == Message.ALL_SHIP_SUNK) {
                gameStatus = GameStatus.END;
            }
            System.out.println(msg.getMessage());
            clearConsole();
        }
    }

    private void outputGameBoards(Board board1, Board board2) {
        board2.outputBoard(GameStatus.GAME);
        System.out.println("---------------------");
        board1.outputBoard(GameStatus.PREGAME);
    }

}
