package rogue;

public class Food extends Item implements Edible {

    /** Class constructor. */
    public Food() {
        super();
    }

    /**
     * Creates class from item blueprint.
     * @param blueprint an item whose primary value will be copied.
     */
     public Food(Item blueprint) {
         super(blueprint);
     }

    /**
     * Eat the item.
     * @return A string representation of what happened.
     */
    public String eat() {
        return this.getDescription();
    }
}
