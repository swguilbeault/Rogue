package rogue;

public class Potion extends Magic implements Edible, Tossable {

    /** Class constructor. */
    public Potion() {
        super();
    }

    /**
     * Creates class from item blueprint.
     * @param blueprint an item whose primary value will be copied.
     */
     public Potion(Item blueprint) {
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
     @return A string represenation of what happened.
     */
    public String toss() {
        String[] tokens = this.getDescription().split(":");
        return tokens[tokens.length > 1 ? 1 : 0];
    }
}
