package rogue;

public class Clothing extends Item implements Wearable {

    private boolean isWorn;

    /** Class constructor. */
    public Clothing() {
        super();
        this.isWorn = false;
    }

    /**
     * Creates class from item blueprint.
     * @param blueprint an item whose primary value will be copied.
     */
     public Clothing(Item blueprint) {
         super(blueprint);
         this.isWorn = false;
     }

    /**
     * Put on/take off the item.
     * @return A message detailing what happened.
     */
    public String wear() {
        this.isWorn = !this.isWorn;
        return this.getDescription();
        // return ((this.isWorn ? "now wearing " : "no longer wearing ") + this.getName());
    }

    /**
     * Get the text to display in the inventory window.
     * @return The text to display.
     */
    public String getInventoryTitle() {
        return this.getName() + (this.isWorn ? " [EQUIPPED]" : "");
    }
}
