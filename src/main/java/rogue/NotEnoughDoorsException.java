package rogue;

public class NotEnoughDoorsException extends Exception {

    private Room room;

    /**
     * Default constructor.
     */
    public NotEnoughDoorsException() {
        this.room = null;
    }

    /**
     * Constructor.
     * @param invalidRoom the room without enough doors.
     */
    public NotEnoughDoorsException(Room invalidRoom) {
        this.room = invalidRoom;
    }

    /**
     * Returns the invalid room.
     * @return the invalid room.
     */
    public Room getInvalidRoom() {
        return this.room;
    }
}
