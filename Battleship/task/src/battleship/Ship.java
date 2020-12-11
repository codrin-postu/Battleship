package battleship;

import java.util.Arrays;

public class Ship {

    private int length;
    private String name;
    private int[] xPos;
    private int[] yPos;
    private boolean sunk;
    private int placesHit;


    public Ship(String name, int length, int[] xPos, int[] yPos) {
        this.name = name;
        this.length = length;
        this.xPos = new int[2];
        this.yPos = new int[2];
        this.xPos = Arrays.copyOf(xPos, xPos.length);
        this.yPos = Arrays.copyOf(yPos, yPos.length);
        this.sunk = false;
        this.placesHit = 0;
    }

    public int getStartxPos() {
        return xPos[0];
    }

    public int getEndxPos() {
        return xPos[1];
    }

    public int getStartyPos() {
        return yPos[0];
    }

    public int getEndyPos() {
        return yPos[1];
    }

    public int[] getxPos() {
        return Arrays.copyOf(xPos, xPos.length);
    }

    public boolean isSunk() {
        return sunk;
    }

    public int[] getyPos() {
        return Arrays.copyOf(yPos, yPos.length);
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public boolean checkHit(int xTarget, int yTarget) {
        Arrays.sort(this.xPos);
        Arrays.sort(this.yPos);
        if (xTarget >= this.xPos[0] && xTarget <= this.xPos[1] &&
                yTarget >= this.yPos[0] && yTarget <= yPos[1]) {
            placesHit++;

            //We check if the whole ship was shot down;
            if (placesHit == length) {
                setSunk(true);
            }
            return true;
        }
        return false;
    }

}
