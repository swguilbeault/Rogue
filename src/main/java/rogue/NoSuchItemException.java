package rogue;

public class NoSuchItemException extends Exception {

    private Item item;

    /**
     * Default constructor.
     */
    public NoSuchItemException() {
        this.item = null;
    }

    /**
     * Constructor.
     * @param invalidItem the invalid item.
     */
    public NoSuchItemException(Item invalidItem) {
        this.item = invalidItem;
    }

    /**
     * Returns the invalid item.
     * @return the invalid item.
     */
    public Item getInvalidItem() {
        return this.item;
    }
}
