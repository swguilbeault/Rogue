package rogue;

public class InvalidMoveException extends Exception {

    private char move;

    /**
     * Default constructor.
     */
    public InvalidMoveException() {
        this.move = '\0';
    }

    /**
     * Constructor.
     * @param c the invalid move.
     */
    public InvalidMoveException(char c) {
        this.move = c;
    }

    /**
     * Returns the invalid move.
     * @return the invalid move.
     */
    public char getInvalidMove() {
        return this.move;
    }
}
