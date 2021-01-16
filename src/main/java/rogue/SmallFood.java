package rogue;

public class SmallFood extends Food implements Tossable {

    /** Class constructor. */
    public SmallFood() {
        super();
    }

    /**
     * Creates class from item blueprint.
     * @param blueprint an item whose primary value will be copied.
     */
    public SmallFood(Item blueprint) {
        super(blueprint);
    }

    /**
     * Eat the item.
     * @return A string representation of what happened.
     */
    public String eat() {
    String[] tokens = this.getDescription().split(":");
        return tokens[0];
    }

    /**
     * Toss the item.
     * @return A string representation of what happened.
     */
    public String toss() {
        String[] tokens = this.getDescription().split(":");
        return tokens[tokens.length > 1 ? 1 : 0];
    }
}
