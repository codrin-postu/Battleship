package battleship;

public enum Message {
    SHIP_HIT("You hit a ship!"),
    SHIP_SUNK("You sank a ship! Specify a new target:"),
    ALL_SHIP_SUNK("You sank the last ship. You won. Congratulations!"),
    MISS("You missed. Try again:"),
    ERR_WRNG_COORDS("Error! You entered the wrong coordinates! Try again:"),
    ERR_INV_INF("Error! Invalid information. Try again:"),
    ERR_ALR_HIT("Error! You already shot in those coordinates! Try again:");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
