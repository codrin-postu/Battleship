package battleship;

import java.util.Scanner;

public class Game {
    private Board board1, board2;
    private GameStatus gameStatus;

    private final ShipType[] placeableShips = {ShipType.AIRCRAFT, ShipType.BATTLESHIP, ShipType.SUBMARINE, ShipType.CRUISER, ShipType.DESTROYER};
    ;


    public Game() {
        this.board1 = new Board();
        this.board2 = new Board();
        gameStatus = GameStatus.PREGAME;
    }

    public void beginGame() {

        for (ShipType s : placeableShips
        ) {
            board1.outputBoard(gameStatus);
            board1.addShip(s);
        }
        board1.outputBoard(gameStatus);

        //Move to next stage
        startGame();
    }

    private void startGame() {
        gameStatus = GameStatus.GAME;
        System.out.println("The game starts!");
        board1.outputBoard(gameStatus);

        while (gameStatus != GameStatus.END) {
            if (board1.takeShot()) {
                
            }
        }
    }

}
