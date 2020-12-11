package battleship;

import javax.management.remote.JMXConnectionNotification;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    // ~ - a miss
    // o - your ship
    // x - hit ship
    // M - a miss

    String[][] prepBoard; //Shows all ships
    String[][] gameBoard; //Fog until a missile hit
    Ship[] ships;
    int shipCount;

    public Board() {

        //We initialize the both matrix with the fog.
        this.prepBoard = new String[10][10];
        this.gameBoard = new String[10][10];

        for (int i = 0; i < prepBoard.length; i++) {
            for (int j = 0; j < prepBoard.length; j++) {
                prepBoard[i][j] = gameBoard[i][j] = "~";
            }
        }
        this.ships = new Ship[5];
        shipCount = 0;
    }

    public void outputBoard(GameStatus status) {
        //Output the empty corner
        System.out.print(" ");

        //The columns
        for (int i = 1; i <= prepBoard.length; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");

        //Output the current table
        for (int i = 0; i < prepBoard.length; i++) {
            //The lines
            System.out.print((char) (i + 65));
            for (int j = 0; j < prepBoard[i].length; j++) {

                //We check the game status
                switch (status) {
                    case PREGAME:
                        System.out.print(" " + prepBoard[i][j]);
                        break;
                    case GAME:
                        System.out.print(" " + gameBoard[i][j]);
                        break;
                }

            }
            System.out.print("\n");
        }
    }

    public void addShip(ShipType ship) {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        int[] xCoords = new int[2];
        int[] yCoords = new int[2];

        boolean errorHappened = false;

        //We get the coords of the Ship to verify if it is valid
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

                xCoords[i] = input.charAt(0) - 65;
                if (input.length() == 2 && Character.isDigit(input.charAt(1))) {
                    yCoords[i] = Integer.parseInt(input.substring(1,2)) - 1;
                } else if (input.length() == 3 && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(2))) {
                    yCoords[i] = Integer.parseInt(input.substring(1,3)) - 1;
                } else {
                    errorHappened = true;
                    break;
                }
            }
            if (!errorHappened) {
                errorHappened = !checkShip(xCoords, yCoords, ship.getShipName(), ship.getShipLength());
            } else {
                System.out.println("Error! Invalid information. Try again:");
            }
            scanner.nextLine();

        } while (errorHappened);

        ships[shipCount++] = new Ship(ship.getShipName(), ship.getShipLength(), xCoords, yCoords);
        drawShip(xCoords, yCoords);
    }

    private void drawShip(int[] xCoords, int[] yCoords) {
        for (int i = xCoords[0]; i <= xCoords[1]; i++) {
            for (int j = yCoords[0]; j <= yCoords[1]; j++) {
                prepBoard[i][j] = "o";
            }
        }
    }

    private boolean checkShip(int[] xCoords, int[] yCoords, String shipName, int expectedLength) {
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

    public Message takeShot() {
        Scanner scanner = new Scanner(System.in);
        int targetxCoord, targetyCoord;

        System.out.println("Take a shot!");

        String input = scanner.next();
        input = input.toUpperCase();

        if (input.length() == 2 && Character.isDigit(input.charAt(1))) {
            targetxCoord = input.charAt(0) - 65;
            targetyCoord = Integer.parseInt(input.substring(1, 2)) - 1;
        } else if (input.length() == 3 && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(2))) {
            targetxCoord = input.charAt(0) - 65;
            targetyCoord = Integer.parseInt(input.substring(1, 3)) - 1;
        } else {
            return Message.ERR_INV_INF;
        }

        if (targetxCoord >= 10 || targetxCoord < 0 || targetyCoord >= 10 || targetyCoord < 0) {
            return Message.ERR_WRNG_COORDS;
        } /*else if (gameBoard[targetxCoord][targetyCoord] != "~") {
            return Message.ERR_ALR_HIT;
        } */else if (prepBoard[targetxCoord][targetyCoord] == "o") {
            gameBoard[targetxCoord][targetyCoord] = "X";
            outputBoard(GameStatus.GAME);

            for (Ship s : ships
            ) {
                if (s.isSunk()) continue;
                if (s.checkHit(targetxCoord, targetyCoord)) {
                    if (s.isSunk()) {
                        shipCount--;
                        if (shipCount == 0) {
                            return Message.ALL_SHIP_SUNK;
                        }
                        return Message.SHIP_SUNK;
                    }
                }
            }
            return Message.SHIP_HIT;
        } else {
            gameBoard[targetxCoord][targetyCoord] = "M";
            outputBoard(GameStatus.GAME);

            return Message.MISS;
        }
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
