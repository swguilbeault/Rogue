//  Samuel Guilbeault (1088129)
//  CIS*2430 01 F20
//  Prof. Judi McCuaig
//  5 Oct 2020

package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.io.Serializable;

/** The game itself. */
public class Rogue implements Serializable {

    public static final String[] DIRS = {"N", "E", "S", "W"};

    public static final char UP = 'd';
    public static final char RIGHT = 'v';
    public static final char LEFT = 'x';
    public static final char DOWN = 'c';
    public static final char OPENINV = 'i';
    public static final char OPENINVWEAR = 'w';
    public static final char OPENINVEAT = 'e';
    public static final char OPENINVTOSS = 't';

    public static final int MOVEMENT = 0;
    public static final int INVENTORY = 1;

    private static final int NEWLINESASCII = 10;
    private static final int BACKSPACEASCII = 8;

    private Player player;
    private Dungeon dungeon;
    private HashMap<String, Character> tileset;
    private RogueParser dungeonInfo;
    private int controlMode;
    private int invMode;
                                                                                                    /// CONSTRUCTORS ///
    /** Default constructor. */
    public Rogue() {
        this.player = new Player();
        this.dungeon = new Dungeon();
        this.tileset = null;
        this.controlMode = Rogue.MOVEMENT;
    }

    /**
     * Class constructor.
     * @param theDungeonInfo RogueParser with dungeon information already parsed.
     */
    public Rogue(RogueParser theDungeonInfo) {
        this();
        this.dungeonInfo = theDungeonInfo;
        this.dungeon = new Dungeon(theDungeonInfo);
        this.createRooms();
    }
                                                                                             /// GETTERS AND SETTERS ///
    /**
     * Sets the player of the game.
     * @param thePlayer The player for the game.
     */
    public void setPlayer(Player thePlayer) {
        if (this.getPlayer().getCurrentRoom() != null) {
            thePlayer.setCurrentRoom(this.getPlayer().getCurrentRoom());
            thePlayer.getCurrentRoom().setPlayer(thePlayer);
        }
        this.player = thePlayer;
    }

    /**
     * Set symbols based on data from parser.
     */
    public void setSymbols() {
        this.tileset = (this.dungeonInfo.getSymbols());
    }

    /**
     * Gets all the rooms in the game.
     * @return an ArrayList containing all the rooms in the game.
     */
    public ArrayList<Room> getRooms() {
        return this.getDungeon().getRooms();
    }

    /**
     * Gets all the items in the game.
     * @return an ArrayList containing all the items in the game.
     */
    public ArrayList<Item> getItems() {
        return this.getDungeon().getItems();
    }

    /**
     * Get the player in the game.
     * @return The player in the game.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the item blueprints in the game.
     * @return An arraylist containing all the item blueprints.
     */
    public ArrayList<Item> getItemBlueprints() {
        return this.getDungeon().getItemBlueprints();
    }

    /**
     * Get the dungeon in the game.
     * @return The dungeon.
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }
                                                                                                   /// CLASS METHODS ///
    /**
     * Creates and add rooms to instance using data in dungeonInfo.
     */
    public void createRooms() {
        this.getDungeon().loadFromParser();
        this.getDungeon().getStartingRoom().setPlayer(this.getPlayer());
        this.getPlayer().setCurrentRoom(this.getDungeon().getStartingRoom());
        if (!this.getDungeon().verifyDungeon()) {
            this.dungeon = null;
        }
        // this.printItemBlueprints();
        // this.printItems();

    }

    /**
     * Creates a room from a HashMap and adds a room to the instance.  Exists for grading purposes only.
     * @param toAdd the HashMap containing the room data.
     */
    public void addRoom(Map<String, String> toAdd) {
        this.getDungeon().addRoom(toAdd);
    }

    /**
     * Adds an item to the game. Exists for grading purposes only.
     * @param toAdd the HashMap containing the Item blueprint data.
     */
    public void addItem(Map<String, String> toAdd) {
        this.getDungeon().addItemBlueprint(toAdd);
        this.getDungeon().addItem(toAdd);
    }

    /**
     * Gets the current control mode of the game.
     * @return The current control mode of the game.
     */
    public int getControlMode() {
        return this.controlMode;
    }

    /**
     * Prints all the items.
     */
    public void printItems() {
        System.out.print("item list (size " + this.getDungeon().getItems().size() + "): ");
        for (Item i : this.getDungeon().getItems()) {
            System.out.print("\n\t" + i.toString() + ",  ");
        }
        System.out.println();
    }

    /**
     * Prints all the item blueprints.
     */
    public void printItemBlueprints() {
        System.out.print("item blueprints list (size " + this.getItemBlueprints().size() + "): ");
        for (Item i : this.getItemBlueprints()) {
            System.out.print("\n\t" + i.toString() + ",  ");
        }
        System.out.println();
    }


    /**
     * Creates a string representation of all the rooms in the instance.
     * @return the string represenation of all the rooms.
     */
    public String displayAll() {
        String out = "";

        for (int i = 0; i < this.getDungeon().getRooms().size(); i++) {
            out += this.getDungeon().getRooms().get(i).displayRoom();
            out += i != this.getRooms().size() - 1 ? "\n\n\n" : ""; // adds newlines if additional rooms
        }

        return out;
    }

    /**
     * Returns the string of the current room.
     * @return the string.
     */
    public String getNextDisplay() {
        return this.getPlayer().getCurrentRoom().stringMap();
    }

    /**
     * Make a move in the game.
     * @param input the key that has been pressed.
     * @return a message related to the input.
     * @throws InvalidMoveException when the player attempts to move somewhere they cannot.
     */
    public String makeMove(char input) throws InvalidMoveException {
        // System.out.println("input: " + input);
        if (this.controlMode == Rogue.MOVEMENT) {
            return this.makeMovementMove(input);
        } else if (this.controlMode == Rogue.INVENTORY) {
            return this.makeInventoryMove(input);
        }
        return "confused";
    }

    private String makeMovementMove(char input) throws InvalidMoveException {
        // System.out.println("input key: " + input + " " + (int) input);
        if (input == Rogue.DOWN) {
            return this.movePlayer(0, 1);
        } else if (input == Rogue.UP) {
            return this.movePlayer(0, -1);
        } else if (input == Rogue.LEFT) {
            return this.movePlayer(-1, 0);
        } else if (input == Rogue.RIGHT) {
            return this.movePlayer(1, 0);
        } else if (input == OPENINV || input == OPENINVEAT || input == OPENINVWEAR || input == OPENINVTOSS) {
            this.controlMode = Rogue.INVENTORY;
            this.invMode = input;
            this.getPlayer().suggestInventoryUpdate();
            return "Time to look through your inventory";
        }
        return "unrecognized keybind";
    }

    private String makeInventoryMove(char input) {
        if (input == Rogue.DOWN) {
            this.getPlayer().shiftInventoryFocus(1);
        } else if (input == Rogue.UP) {
            this.getPlayer().shiftInventoryFocus(-1);
        } else if (input == Rogue.OPENINV || input == (char) BACKSPACEASCII) {
            this.controlMode = Rogue.MOVEMENT;
            this.getPlayer().suggestInventoryUpdate();
            return "closed inventory";
        } else if (input == (char) NEWLINESASCII) {
            return this.useItem(this.getPlayer().getCurrentItem());
        }
        return "SELECT AN ITEM IN THE INVENTORY TO USE";
    }

    private String useItem(Item item) {
        this.getPlayer().suggestInventoryUpdate();
        this.controlMode = Rogue.MOVEMENT;
        if (item == null) {
            return "your inventory is empty";
        }
        if (invMode == OPENINVEAT) {
            return (item instanceof Edible) ? this.getPlayer().eat((Edible) item)
                                            : "eating " + item.getName() + " probably won't sit too well";
        } else if (invMode == OPENINVWEAR) {
            return (item instanceof Wearable) ? this.getPlayer().wear((Wearable) item)
                                              : "wearing " + item.getName() + " might be a little too scandalous";
        } else if (invMode == OPENINVTOSS) {
            return (item instanceof Tossable) ? this.getPlayer().toss(item)
                                              : item.getName() + " seems too valuable to leave behind";
        }
        this.controlMode = Rogue.INVENTORY;
        return item.getDescription();
    }

    /**
     * Moves the player by (x, y).
     * @param x the horizontal movement
     * @param y the vertical movement
     * @return A string message which reflects the movement.
     * @throws InvalidMoveException when the player attempts to move somewhere they cannot.
     */
    public String movePlayer(int x, int y) throws InvalidMoveException {
        if (this.getPlayer().getXyLocation().getX() + x < this.getPlayer().getCurrentRoom().getWidth() - 1
         && this.getPlayer().getXyLocation().getX() + x > 0
         && this.getPlayer().getXyLocation().getY() + y < this.getPlayer().getCurrentRoom().getHeight() - 1
         && this.getPlayer().getXyLocation().getY() + y > 0) {
            this.getPlayer().getXyLocation().translate(x, y);
            String msg = this.getPlayer().pickUpItem();
            return msg != null ? msg : "Moved player by (" + x + ", " + y + ")";
        }

        String dir = (x == 1 ? "E" : x == -1 ? "W" : y == 1 ? "S" : y == -1 ? "N" : null);
        if (this.getPlayer().getCurrentRoom().playerIsNextTo(dir)) {
            this.getPlayer().getCurrentRoom().getDoor(dir).moveThrough(this.getPlayer());
            String msg = this.getPlayer().pickUpItem();
            return msg != null ? msg : "Moved through door (" + dir + ")";
        }

         throw new InvalidMoveException();
    }

    /**
     * Get the opposite cardinal direction.
     * @param dir The direction to be inverted.
     * @return A string representing the opposite direction.
     */
    public static String oppositeDir(String dir) {
        if ("N".equals(dir)) {
            return "S";
        } else if ("E".equals(dir)) {
            return "W";
        } else if ("S".equals(dir)) {
            return "N";
        } else if ("W".equals(dir)) {
            return "E";
        }
        return null;
    }
}
