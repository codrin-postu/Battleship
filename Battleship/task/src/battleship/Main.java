package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        Board gameBoard = new Board();
        for (int i = 5; i >0; i--) {
            gameBoard.outputBoard();
            gameBoard.addShip(i);
        }
        gameBoard.outputBoard();
    }
}
