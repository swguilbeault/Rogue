//  Samuel Guilbeault (1088129)
//  CIS*2430 01 F20
//  Prof. Judi McCuaig
//  5 Oct 2020

package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import java.awt.Point;

import java.io.Serializable;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room implements Serializable {

    private int width;
    private int height;
    private int id;
    private ArrayList<Item> items;
    private ArrayList<Item> blueprints;
    private Player player;
    private HashMap<String, Door> doors;
    private HashMap<String, Character> tileset;
    private boolean start;
    private Dungeon dungeon;


    /// CONSTRUCTORS ///

    /**  Class constructor. */
    public Room() {
        this.width = 0;
        this.height = 0;
        this.id = 0;
        this.items = new ArrayList<Item>();
        this.player = null;
        this.doors = new HashMap<String, Door>();
        this.tileset = null;
        this.start = false;
        this.dungeon = null;
    }


    /// GETTERS AND SETTERS ///
    // Required getter and setters below

    /**
     * Gets the width of the room.
     * @return the width of the room.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets the width of the room.
     * @param newWidth the new width of the room.
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    /**
     *  Gets the height of the room.
     *  @return the height of the room.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the height of the room.
     * @param newHeight the new height of the room.
     */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    /**
     * Gets the id of the room.
     * @return the id of the room.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set the ID of the room.
     * @param newId the ID for the room.
     */
    public void setId(int newId) {
        this.id = newId;
    }

    /**
     * Gets whether or not this room is a starting room.
     * @return true if the room is a starting room, else false.
     */
    public boolean isStart() {
        return this.start;
    }

    /**
     * Sets whether or not this room is a starting room.
     * @param isStart true if this is a starting room, else false.
     */
    public void setStart(boolean isStart) {
        this.start = isStart;
    }

    /**
     * Gets the blueprints in the room.
     * @return an ArrayList with all the items in the room.
     */
    public ArrayList<Item> getRoomItems() {
        return this.blueprints;
    }

    /**
     * Sets the item blueprints in the room.
     * @param newRoomItems an ArrayList of items to be in the room.
     */
    public void setRoomItems(ArrayList<Item> newRoomItems) {
        this.blueprints = newRoomItems;
    }

    /**
     * Gets the items in the room.
     * @return an ArrayList with all the items in the room.
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * Sets the items in the room.
     * @param newItems an ArrayList of items to be in the room.
     */
    public void setItems(ArrayList<Item> newItems) {
        this.items = newItems;
    }

    /**
     * Gets the player currently in the room.
     * @return the player, null if no player is in the room.
     */
    public Player getPlayer() {
        return this.player;
    }
    /**
     * Sets the player in the room.
     * @param newPlayer the player in the room.
     */
    public void setPlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    /**
     * Returns a Collection view of the doors in the room.
     * @return the collection of rooms.
     */
    public Collection<Door> getDoors() {
        return this.doors.values();
    }

    /**
     * Gets the location of a door on a certain wall in the room.
     * @param direction one of NSEW.
     * @return the location of the door in the wall, -1 if there is no door.
     */
    public Door getDoor(String direction) {
        return this.doors.get(direction);
    }

    /**
     * Sets a door in the room.
     * @param door the door to be added.
     * @param direction the side of the room for door.
     */
    public void setDoor(Door door, String direction) {
        if (this.doors.containsKey(direction)) {
            this.doors.replace(direction, door);
        } else {
            this.doors.put(direction, door);
        }
    }

    /**
     * Sets a door in the room.
     * @param dir The wall the door is located in.
     * @param pos The location in the wall of the door.
     */
    public void setDoor(String dir, int pos) {
        this.doors.put(dir, new Door());
        this.doors.get(dir).connectRoom(this);
        this.doors.get(dir).setDir(this, dir);
        this.doors.get(dir).setPosition(this, pos);
    }

    /**
     * Adds a door.
     * @param dir the direction of the door
     * @param pos the position of the door
     * @param connectionId the id of the room the door connects to
     */
    public void addDoor(String dir, int pos, int connectionId) {
        this.setDoor(dir, pos);
        this.doors.get(dir).setConnection(connectionId);
    }

    /**
     * Adds and returns a door placed randomly somewhere in the room.
     * @return The randomly added door.
     */
    public Door addRandomDoor() {
        ArrayList<String> validDirs = new ArrayList<String>();
        for (String dir : Rogue.DIRS) {
            if (this.getDoor(dir) == null) {
                validDirs.add(dir);
            }
        }
        if (validDirs.size() > 0) {
            Collections.shuffle(validDirs);
            return this.addRandomDoor(validDirs.get(0));
        }
        return null;
    }

    /**
     * Adds and returns a door placed randomly on the specified wall.
     * @param dir The wall to place the door on.
     * @return The randomly added door.
     */
    public Door addRandomDoor(String dir) {
        if (dir.equals("N") || dir.equals("S")) {
            this.setDoor(dir, 1 + (int) (Math.random() * (this.getWidth() - 2)));
        } else if (dir.equals("E") || dir.equals("W")) {
            this.setDoor(dir, 1 + (int) (Math.random() * (this.getHeight() - 2)));
        }
        return this.getDoor(dir);
    }

    /**
     * Setter for the tileset field.
     * @param newTileset
     */
    public void setTileSet(HashMap<String, Character> newTileset) {
        this.tileset = newTileset;
    }

    /**
     * Returns the requested tile.
     * @param tile the name of the requested tile.
     * @return the requested teil.
     */
    public Character getTile(String tile) {
        return this.tileset.get(tile);
    }

    /**
     * Gets the dungeon of the room.
     * @return the dungeon of the room.
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * Sets the dungeon of the room.
     * @param newdungeon the new dungeon of the room.
     */
    public void setDungeon(Dungeon newdungeon) {
        this.dungeon = newdungeon;
    }

    /// CLASS METHODS ///

    /**
     * Add item to room.
     * @param item The item to add to the room.
     * @throws NoSuchItemException If the item does not exist in the game.
     * @throws ImpossiblePositionException if the item's position is outside the room or occupied
     */
    public void addItem(Item item) throws NoSuchItemException, ImpossiblePositionException {
        Item blueprint = null;
        for (Item bp : this.getRoomItems()) {
            if (bp.getId() == item.getId()) {
                blueprint = bp;
            }
        }
        if (blueprint == null) { // if item does not exist
            throw new NoSuchItemException(item);
        }
        if (this.isTileEmpty(item.getXyLocation())) {
            this.items.add(item);
            item.setCurrentRoom(this);
        } else {
            throw new ImpossiblePositionException(item.getXyLocation());
        }
    }

    /**
     * Finds an item at the specified point.
     * @param point the position to search for an item.
     * @return the item found at the point if it exists, else null.
     */
    public Item findItem(Point point) {
        for (Item item : this.getItems()) {
            if (item.getXyLocation().equals(point)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Removes and item at a given location.
     * @param point the position to search for an item.
     * @return the removed item, or null if there is no item.
     */
    public Item removeItemAt(Point point) {
        Item toRemove = this.findItem(point);
        this.getItems().remove(toRemove);
        return toRemove;
    }

    /**
     * Checks if the player is in the current room.
     * @return true if the player is in this room, else false.
     */
    public boolean isPlayerInRoom() {
        return this.player != null ? this.player.getCurrentRoom() == this : false;
    }

    /**
     * Checks if a point is an empty tile.
     * @param point The point being checked.
     * @return true if the point is in the room and empty, else false.
     */
    public Boolean isTileEmpty(Point point) {
        return this.getTilePopulation(point) == 0;
    }

    /**
     * Counts the number of entities on a given tile.
     * @param point the location of the tile.
     * @return the number of entities on the tile, or -1 if out of bounds.
     */
    public int getTilePopulation(Point point) {
        if (point.getX() <= 0 || point.getX() > this.getWidth() - 2) { // x bounds
            return -1;
        }
        if (point.getY() <= 0 || point.getY() > this.getHeight() - 2) { // y bounds
            return -1;
        }
        int counter = 0;
        if (this.isPlayerInRoom() && this.getPlayer().getXyLocation().equals(point)) { // player on tile?
            counter += 1;
        }
        for (Item item : this.getItems()) { // count items on tile
            if (item.getXyLocation().equals(point)) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Verifies various elements of the room are correct.
     * @return true if the room is complete, else false.
     * @throws NotEnoughDoorsException if the room has no doors.
     */
    public boolean verifyRoom() throws NotEnoughDoorsException {
        return this.verifyRoom(this);
    }

    /**
     * Verifies various elements of the room are correct.
     * @return true if the room is complete, else false.
     * @param room the room to be verified.
     * @throws NotEnoughDoorsException if the room has no doors.
     */
    public boolean verifyRoom(Room room) throws NotEnoughDoorsException {
        if (room.isPlayerInRoom() && getTilePopulation(room.getPlayer().getXyLocation()) != 1) { // player pos
            return false;
        }
        for (Item item : room.getItems()) { // verifies item positions
            if (room.getTilePopulation(item.getXyLocation()) != 1) {
                return false;
            }
        }
        if (room.getDoors().size() == 0) { // check there is at least one door
            throw new NotEnoughDoorsException(room);
        }
        for (Door door : room.getDoors()) { // check for broken doors
            if (door.getConnectedRooms().size() < 2 || door.getRoom(0) == null || door.getRoom(1) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the player is directly next to a door.
     * @param dir The direction of the door.
     * @return true if the player is next to the door, else false.
     */
    public boolean playerIsNextTo(String dir) {
        if (this.isPlayerInRoom() && this.getDoor(dir) != null) {
            int pos = this.getDoor(dir).getPosition(this);

            if ("N".equals(dir) || "S".equals(dir)) {
                return this.getPlayer().getXyLocation().getX() == pos;
            } else if ("E".equals(dir) || "W".equals(dir)) {
                return this.getPlayer().getXyLocation().getY() == pos;
            }
            System.out.println("Player is not next to " + this.getDoor(dir).toString());
        }
        return false;
    }

    /**
     * Adjusts point to the nearest point within the room.
     * @param point The point to be adjusted.
     */
    public void adjust(Point point) {
        if (point.getX() > this.getWidth()) {
            point.setLocation(this.getWidth(), point.getY());
        } else if (point.getX() < 1) {
            point.setLocation(1, point.getY());
        }
        if (point.getY() > this.getHeight()) {
            point.setLocation(point.getX(), this.getHeight());
        } else if (point.getY() < 1) {
            point.setLocation(point.getX(), 1);
        }
    }

    /**
     * Produces a string that can be printed to produce an ascii rendering of the room and all of
     * its contents. Header spacing assumes room id less than 10.
     * @return string representation of how the room looks.
     */
    public String displayRoom() {
        return stringHeader() + stringMap();
    }

    /**
     * Returns a header for the room string.
     * @return the header string.
     */
    public String stringHeader() {
        String header = "<";
        String title = " [Room " + this.id + "] ";
        for (int i = 0; i < (this.width - title.length()) / 2; i++) {
            header += "-";
        }
        header += title;
        for (int i = 0; i < (this.width - title.length()) / 2; i++) {
            header += "-";
        }
        header += this.width % 2 == 1 ? "->\n" : ">\n";
        if (this.isStart()) {
            header += "- Starting Room\n";
        }

        return header;
    }

    /**
     * Returns a string representation of the room.
     * @return the string representation of the room.
     */
    public String stringMap() {
        char[] chars = this.blankMap().toCharArray(); // replace characters with special characters
        for (Item i: this.getItems()) { // items
            Character c = this.getTile(i.getType());
            chars[pointToGridIndex(i.getXyLocation())] = c != null ? c : 'O';
        }
        if (this.isPlayerInRoom()) { // player location
            chars[pointToGridIndex(this.getPlayer().getXyLocation())] = this.getTile("PLAYER");
        }
        chars = addDoorsToMap(chars);
        return String.valueOf(chars);
    }

    /**
     * Adds the doors to the map.
     * @param chars A char[] representing the map.
     * @return The char[] populated with doors.
     */
    private char[] addDoorsToMap(char[] chars) {
        if (this.getDoor("N") != null && this.getDoor("N").isComplete()) { // north door
            Point doorPoint = new Point(this.getDoor("N").getPosition(this), 0);
            chars[pointToGridIndex(doorPoint)] = this.getTile("DOOR");
        }
        if (this.getDoor("S") != null && this.getDoor("S").isComplete()) { // south door
            Point doorPoint = new Point(this.getDoor("S").getPosition(this), this.getHeight() - 1);
            chars[pointToGridIndex(doorPoint)] = this.getTile("DOOR");
        }
        if (this.getDoor("W") != null && this.getDoor("W").isComplete()) { // south door
            Point doorPoint = new Point(0, this.getDoor("W").getPosition(this));
            chars[pointToGridIndex(doorPoint)] = this.getTile("DOOR");
        }
        if (this.getDoor("E") != null && this.getDoor("E").isComplete()) { // south door
            Point doorPoint = new Point(this.getWidth() - 1, this.getDoor("E").getPosition(this));
            chars[pointToGridIndex(doorPoint)] = this.getTile("DOOR");
        }
        return chars;
    }

    /**
     * Returns a string representation of the empty room.
     * @return the string representation of the empty room.
     */
    public String blankMap() {
        String map = "";
        for (int i = 0; i < this.getWidth(); i++) { // top border
            map += this.getTile("NS_WALL");
        }
        map += "\n";
        for (int i = 0; i < this.getHeight() - 2; i++) { // middle rows
            map += this.getTile("EW_WALL");
            for (int j = 0; j < this.getWidth() - 2; j++) {
                map += this.getTile("FLOOR");
            }
            map += this.getTile("EW_WALL") + "\n";
        }
        for (int i = 0; i < this.getWidth(); i++) { // bottom border
            map += this.getTile("NS_WALL");
        }
        map += "\n";
        return map;
    }

    /**
     * Calculates the index of a given point in the String representation of the room map.
     * @param point the coordinate in the room whose index should be returned.
     * @return the index of the point.
     */
    private int pointToGridIndex(Point point) {
        int rowLength = this.getWidth() + 1;
        return (rowLength) * ((int) point.getY()) + (int) point.getX();
    }
}
