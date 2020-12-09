package battleship;

import javax.management.remote.JMXConnectionNotification;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    // ~ - a miss
    // o - your ship
    // x - hit ship
    // M - a miss

    String[][] board;
    Ship[] ships;
    int shipCount;

    public Board() {
        this.board = new String[10][10];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = "~";
            }
        }
        this.ships = new Ship[5];
        shipCount = 0;
    }

    public void outputBoard() {
        //Output the empty corner
        System.out.print(" ");

        //First line (positions)
        for (int i = 1; i <= board.length; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");

        //Output the current table
        for (int i = 0; i < board.length; i++) {
            System.out.print((char) (i + 65));

            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void addShip(int shipType) {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        int[] xCoords = new int[2];
        int[] yCoords = new int[2];

        String shipName = "Not Defined";
        int expectedLength = -1;

        switch (shipType) {
            case 5:
                shipName = "Aircraft Carrier";
                expectedLength = 5;
                break;
            case 4:
                shipName = "Battleship";
                expectedLength = 4;
                break;
            case 3:
                shipName = "Submarine";
                expectedLength = 3;
                break;
            case 2:
                shipName = "Cruiser";
                expectedLength = 3;
                break;
            case 1:
                shipName = "Destroyer";
                expectedLength = 2;
                break;
            default:
                System.out.println("Error! This ship type does not exist");
        }

        System.out.println("Enter the coordinates of the " + shipName + " (" + expectedLength + " cells):");
        do {

            for (int i = 0; i < 2; i++) {
                input = scanner.next();
                input = input.toUpperCase();

                xCoords[i] = input.charAt(0) - 65;
                yCoords[i] = Integer.parseInt(input.substring(1)) - 1;
            }

        } while (!checkShip(xCoords, yCoords, expectedLength, shipName));

        ships[shipCount++] = new Ship(shipName, expectedLength, xCoords, yCoords);
        drawShip(xCoords, yCoords);
    }

    private void drawShip(int[] xCoords, int[] yCoords) {
        for (int i = xCoords[0]; i <= xCoords[1]; i++) {
            for (int j = yCoords[0]; j <= yCoords[1]; j++) {
                board[i][j] = "o";
            }
        }
    }

    private boolean checkShip(int[] xCoords, int[] yCoords, int expectedLength, String shipName) {
        //We check if we have at least 2 points where 2 ships may interact. If true, then it means those 2 are too close to eachother.
        int incorrectPos = 0;

        //We order the coords in case the ships were placed the other way around
        Arrays.sort(xCoords);
        Arrays.sort(yCoords);

        for (int i = 0; i < 2; i++) {
            if (xCoords[i] >= 10 || xCoords[i] < 0 || yCoords[i] >= 10 || yCoords[i] < 0) {
                System.out.println("Error! Wrong location! Try again:");
                return false;
            }
        }

        if (Math.abs(xCoords[0] - xCoords[1]) + 1 != expectedLength && Math.abs(yCoords[0] - yCoords[1]) + 1 != expectedLength) {
            System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            return false;
        }
        if (xCoords[0] != xCoords[1] && yCoords[0] != yCoords[1]) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        for (int i = 0; i < shipCount; i++) {
            incorrectPos = 0;

            int[] placedShipxCoords = ships[i].getxPos();
            int[] placedShipyCoords = ships[i].getyPos();

            Arrays.sort(placedShipxCoords);
            placedShipxCoords[0] -= 1;
            placedShipxCoords[1] += 1;

            Arrays.sort(placedShipyCoords);
            placedShipyCoords[0] -= 1;
            placedShipyCoords[1] += 1;

            if (xCoords[0] == xCoords[1]) {
                if (xCoords[0] >= placedShipxCoords[0] && xCoords[1] <= placedShipxCoords[1]) {
                    incorrectPos++;
                }
                incorrectPos = getIncorrectPos(yCoords, incorrectPos, placedShipyCoords);

            } else if (yCoords[0] == yCoords[1]) {
                if (yCoords[0] >= placedShipyCoords[0] && yCoords[1] <= placedShipyCoords[1]) {
                    incorrectPos++;
                }
                incorrectPos = getIncorrectPos(xCoords, incorrectPos, placedShipxCoords);
            }

            if (incorrectPos >= 2) {
                System.out.println("Error! You placed it too close to another one, Try again:");
                return false;
            }
        }

        return true;
    }

    public boolean takeShot() {
        Scanner scanner = new Scanner(System.in);
        int targetxCoord, targetyCoord;

        System.out.println("Take a shot!");

        String input = scanner.next();
        input = input.toUpperCase();

        targetxCoord = input.charAt(0) - 65;
        targetyCoord = Integer.parseInt(input.substring(1)) - 1;

        if (targetxCoord >= 10 || targetxCoord < 0 || targetyCoord >= 10 || targetyCoord < 0) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        } else if (board[targetxCoord][targetyCoord] == "o") {
            System.out.println("You hit a ship!");
            board[targetxCoord][targetyCoord] = "X";
        } else {
            System.out.println("You missed!");
            board[targetxCoord][targetyCoord] = "M";
        }
        return true;
    }

    private int getIncorrectPos(int[] yCoords, int incorrectPos, int[] placedShipyCoords) {
        if (yCoords[0] >= placedShipyCoords[0] && yCoords[1] <= placedShipyCoords[1]) {
            incorrectPos++;
        } else if (yCoords[1] >= placedShipyCoords[0] && yCoords[1] <= placedShipyCoords[1]) {
            incorrectPos++;
        } else if (placedShipyCoords[0] >= yCoords[0] && placedShipyCoords[0] <= yCoords[1]) {
            incorrectPos++;
        } else if (placedShipyCoords[1] >= yCoords[0] && placedShipyCoords[1] <= yCoords[1]) {
            incorrectPos++;
        }
        return incorrectPos;
    }
}
