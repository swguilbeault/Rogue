//  Samuel Guilbeault (1088129)
//  CIS*2430 01 F20
//  Prof. Judi McCuaig
//  5 Oct 2020

package rogue;
import java.awt.Point;
import java.io.Serializable;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable {
    private Point xyLocation;
    private int id;
    private String name;
    private String type;
    private Character tile;
    private String description;
    private int intendedRoomId;
    private Room currentRoom;

    /// CONSTRUCTORS ///

    /** Class constructor. */
    public Item() {
        this.id = -1;
        this.name = "unknown";
        this.type = "unknown";
        this.xyLocation = new Point(0, 0);
        this.description = "unknown";
        this.tile = '*';
        this.intendedRoomId = -1;
        this.currentRoom = null;
    }

    /**
     * Creates class from item blueprint.
     * @param original an item whose primary value will be copied.
     */
    public Item(Item original) {
        this();
        if (original != null) {
            this.id = original.getId();
            this.name = original.getName();
            this.type = original.getType();
            this.description = original.getDescription();
            this.tile = original.getDisplayCharacter();
            this.xyLocation = original.getXyLocation();
            this.intendedRoomId = original.getIntendedRoomId();
            this.currentRoom = original.getCurrentRoom();
        }
    }

    /**
     * Class constructor.
     * @param newId the id of the item.
     * @param newName the name of the item.
     * @param newType the type of the item.
     * @param newXyLocation the location of the item.
     */
    public Item(int newId, String newName, String newType, Point newXyLocation) {
        this.id = newId;
        this.name = newName;
        this.type = newType;
        this.xyLocation = newXyLocation;
        this.description = "missing description";
        this.tile = '*';
        this.currentRoom = null;
    }


    /// GETTERS AND SETTERS ///

    /**
     * Gets the ID of the item.
     * @return the ID of the item.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the ID of the item.
     * @param newId the ID for the item.
     */
    public void setId(int newId) {
        this.id = newId;
    }

    /**
     * Gets the name of the item.
     * @return the name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the item.
     * @param newName the name for the time.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Gets the type of the item.
     * @return the type of the item.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type of the item.
     * @param newType the type for the item.
     */
    public void setType(String newType) {
        this.type = newType;
    }

    /**
     * Gets the display character for this player.
     * @return the display character for this player.
     */
    public Character getDisplayCharacter() {
        return this.tile;
    }

    /**
     * Sets the display character for this player.
     * @param newDisplayCharacter the display character for this player.
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        this.tile = newDisplayCharacter;
    }

    /**
     * Gets the description of the item.
     * @return the description of the item.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the item.
     * @param newDescription the description of the item.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Gets the location of the item.
     * @return the location of the item.
     */
    public Point getXyLocation() {
        return this.xyLocation;
    }

    /**
     * Sets the location of the item.
     * @param newXyLocation the location of the item.
     */
    public void setXyLocation(Point newXyLocation) {
        this.xyLocation = newXyLocation;
    }

    /**
     * Gets the room id the room the item is meant to be in.
     * @return the room id of the room the item is meant to be in.
     */
    public int getIntendedRoomId() {
        return this.intendedRoomId;
    }

    /**
     * Sets the room id to the id of the room the item is meant to be in.
     * @param newIntendedRoomId the room the item is in.
     */
    public void setIntendedRoomId(int newIntendedRoomId) {
        this.intendedRoomId = newIntendedRoomId;
    }

    /**
     * Gets the room the item is in.
     * @return the room the item is in.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Sets the room the item is in.
     * @param newCurrentRoom the room the item is in.
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        this.currentRoom = newCurrentRoom;
    }

    /**
     * Returns a string representation of the item.
     * @return the string.
     */
    public String toString() {
        String out = "[" + this.getClass().getSimpleName().toUpperCase()
            + " (ID " + Integer.toString(this.getId()) + "); in room ";
        if (this.getCurrentRoom() != null) {
            out += Integer.toString(this.getCurrentRoom().getId()) + " (intended ";
        } else {
            out += "None (intended ";
        }
        out += Integer.toString(this.getIntendedRoomId()) + ") at " + this.getXyLocation().toString() + "; name \""
        + this.getName() + "\"; desc. \"";
        if (this.getDescription().length() > 2 * 2 * 2) {
            out += this.getDescription().substring(0, 2 * 2 * 2) + "...\"]";
        } else {
            out += this.getDescription() + "\"]";
        }
        return out;
    }

    /**
     * Get the text to display in the inventory window.
     * @return The text to display.
     */
    public String getInventoryTitle() {
        return this.getName();
    }
}
