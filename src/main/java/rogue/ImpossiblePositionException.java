package rogue;

import java.awt.Point;

public class ImpossiblePositionException extends Exception {

    private Point point;

    /**
     * Default constructor.
     */
    public ImpossiblePositionException() {
        this.point = null;
    }

    /**
     * Constructor.
     * @param invalidPoint the point whose position is impossible.
     */
    public ImpossiblePositionException(Point invalidPoint) {
        this.point = invalidPoint;
    }

    /**
     * Returns the invalid point.
     * @return the invalid move.
     */
    public Point getInvalidPoint() {
        return this.point;
    }
}
