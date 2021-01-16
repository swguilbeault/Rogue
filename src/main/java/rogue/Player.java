//  Samuel Guilbeault (1088129)
//  CIS*2430 01 F20
//  Prof. Judi McCuaig
//  5 Oct 2020

package rogue;
import java.awt.Point;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * The player character.
 */
public class Player implements Serializable {
    private String name;
    private Point xyLocation;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private int currentItem;
    private boolean inventoryUpdate;

    /// CONSTRUCTORS ///

    /**
     * Class constructor.
     */
    public Player() {
        this.name = "Player";
        this.xyLocation = new Point(1, 1);
        this.currentRoom = null;
        this.inventory = new ArrayList<Item>();
        this.currentItem = 0;
        this.inventoryUpdate = true;
    }

    /**
     * Class constructor.
     * @param newName the name for the player.
     */
    public Player(String newName) {
        this();
        this.name = newName;
    }


    /// GETTERS AND SETTERS ///

    /**
     * Gets the name of the player.
     * @return the name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the player.
     * @param newName the new name for the player.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Gets the location of the player.
     * @return the location of the player.
     */
    public Point getXyLocation() {
        return this.xyLocation;
    }

    /**
     * Sets the location of the player.
     * @param newXyLocation the new location for the player.
     */
    public void setXyLocation(Point newXyLocation) {
        this.xyLocation = newXyLocation;
    }

    /**
     * Gets the current room of the player.
     * @return the current room of the player.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Sets the current room of the player.
     * @param newRoom the new current room of the player.
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    /**
     * Gets the current item being inspected in inventory.
     * @return The current item being inspected in inventory.
     */
    public Item getCurrentItem() {
        if (this.currentItem < this.getInventory().size() && this.currentItem >= 0) {
            return this.getInventory().get(this.currentItem);
        }
        return null;
    }

    /**
     * Adds an item to the players inventory.
     * @param item the item to be picked up.
     * @return true if the item was successfully added to inventory, else false
     */
    public boolean addItem(Item item) {
        if (item != null) {
            this.suggestInventoryUpdate();
            this.getInventory().add(item);
            return true;
        }
        return false;
    }

    /**
     * Get the player's inventory.
     * @return An ArrayList containing all the items in the players inventory.
     */
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    /**
     * Move through the player's inventory.
     * @param shift The amount to move through the inventory.
     */
    public void shiftInventoryFocus(int shift) {
        this.currentItem += shift;
        if (this.currentItem < 0) {
            this.currentItem = this.getInventory().size() - 1;
        }
        if (this.currentItem >= this.getInventory().size()) {
            this.currentItem = 0;
        }
        this.suggestInventoryUpdate();
    }

    /**
     * Checks whether the player's inventory has changed since the last acknowledgement.
     * @return true if the inventory has changed.
     */
    public boolean checkInventoryUpdate() {
        return this.inventoryUpdate;
    }

    /**
     * Sets inventoryUpdate to false until next inventoryUpdate.
     */
    public void acknowledgeInventoryUpdate() {
        this.inventoryUpdate = false;
    }

    /**
     * Sets inventoryUpdate to true.
     */
    public void suggestInventoryUpdate() {
        this.inventoryUpdate = true;
    }

    /**
     * Eats an item if it is in the Player's inventory.
     * @param item The item to be eaten.
     * @return A string message representing the performed function.
     */
    public String eat(Edible item) {
        if (this.getInventory().indexOf(item) == -1) {
            return "cannot eat item that the player does not have";
        }
        this.getInventory().remove(item);
        this.suggestInventoryUpdate();
        return item.eat();
    }

     /**
      * Put on a clothing item if it is in the Player's inventory.
      * @param item The item to be worn/removed.
      * @return A string message representing the performed function.
      */
    public String wear(Wearable item) {
        if (this.getInventory().indexOf(item) == -1) {
            return "cannot wear item that the player does not have";
        }
        this.suggestInventoryUpdate();
        return item.wear();
    }

     /**
      * Throw away an item in the Player's inventory. ASsumes item implements Tossable.
      * @param item The item to be thrown away.
      * @return A string message representing the performed function.
      */
    public String toss(Item item) {
        if (this.getInventory().indexOf(item) == -1) {
              return "cannot toss item that the player does not have";
        }

        item.setIntendedRoomId(this.getCurrentRoom().getId());
        item.setXyLocation(this.getXyLocation());
        try {
            this.getCurrentRoom().getDungeon().connectItemToRoom(item);
        } catch (ImpossiblePositionException e) {
            item.setCurrentRoom(null);
            return "no spots exist close enough to toss the item";
        }

        this.getInventory().remove(item);
        this.suggestInventoryUpdate();
        return ((Tossable) item).toss();
    }


    /**
     * Attempts to pick up the item at the player's feet.
     * @return A string message describing the item picked up, else null.
     */
    public String pickUpItem() {
        Item toPickUp = this.getCurrentRoom().removeItemAt(this.getXyLocation());
        if (this.addItem(toPickUp)) {
            toPickUp.setCurrentRoom(null);
            return "Picked up a " + toPickUp.getName() + ": " + toPickUp.getDescription();
        }
        return null;
    }
}
