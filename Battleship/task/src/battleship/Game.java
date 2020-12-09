package battleship;

import java.util.Scanner;

public class Game {
    private Board board1, board2;
    private STATUS gameStatus;

    enum STATUS {
        PREGAME,
        GAME,
        END
    }

    public Game() {
        this.board1 = new Board();
        this.board2 = new Board();
        gameStatus = STATUS.PREGAME;

    }


    public void preGame() {
        for (int i = 5; i >0; i--) {
            board1.outputBoard();
            board1.addShip(i);
        }
        board1.outputBoard();
        gameStatus = STATUS.GAME;

        startGame();
    }

    private void startGame() {
        System.out.println("The game starts!");
        board1.outputBoard();

        while(gameStatus != STATUS.END) {
            if(board1.takeShot()) {
                board1.outputBoard();
            }
        }
    }

}
