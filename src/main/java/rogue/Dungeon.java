package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;

import java.awt.Point;

import java.io.Serializable;

public class Dungeon implements Serializable {

    // the size of the square an item could be placed in. For example, 11 means that an 11x11 square centered around
    // the item are where it will try to be placed if the original location fails. Must be odd.
    public static final int MAX_DISTRIBUTION_DISTANCE = 11;

    private RogueParser parser;
    private ArrayList<Room> rooms;
    private ArrayList<Item> items;
    private ArrayList<Item> itemBlueprints;

    /** Default constructor. */
    public Dungeon() {
        this.rooms = new ArrayList<Room>();
        this.items = new ArrayList<Item>();
        this.itemBlueprints = new ArrayList<Item>();
        this.parser = new RogueParser();
    }

    /**
     * Constructor.
     * @param rp A RogueParser instance for use with this dungon.
     */
    public Dungeon(RogueParser rp) {
        this();
        this.parser = rp;
    }

    /**
     * Gets all the rooms in the dungeon.
     * @return an ArrayList containing all the rooms in the dungeon.
     */
    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    /**
     * Get starting room.
     * @return The starting room.
     */
    public Room getStartingRoom() {
        for (Room room : this.getRooms()) {
            if (room.isStart()) {
                return room;
            }
        }
        return null;
    }

    /**
     * Gets all the rooms in the dungeon in a random order.
     * @return a shuffled ArrayList containing all the rooms in the dungeon.
     */
    public ArrayList<Room> getRoomsShuffled() {
        ArrayList<Room> out = new ArrayList<Room>(this.getRooms());
        Collections.shuffle(out);
        return out;
    }

    /**
     * Gets all the items in the dungeon.
     * @return an ArrayList containing all the items in the dungeon.
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * Get the item blueprints in the game.
     * @return An arraylist containing all the item blueprints.
     */
    public ArrayList<Item> getItemBlueprints() {
        return this.itemBlueprints;
    }

    /**
     * Adds a room to the dungeon. For grading purposes.
     * @param map A Map containing Room information.
     */
    public void addRoom(Map<String, String> map) {
        this.addRoom(this.mapToRoom(map));
        this.connectRooms();
        this.verifyDungeon();
    }

    /**
     * Adds a room to the dungeon. Does not link doors.
     * @param room The room to be added.
     */
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    /**
     * Adds an Item to the dungeon. For grading purposes.
     * @param map A Map containing Item information.
     */
    public void addItem(Map<String, String> map) {
        this.addItem(this.mapToItem(map));
        this.connectItemsToRooms();
    }

    /**
     * Adds an item to the dungeon. Does not place the item in the room.
     * @param item The item to be added to the dungeon.
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Adds an ItemBlueprint to the dungeon. For grading purposes.
     * @param map A Map containing Item information.
     */
    public void addItemBlueprint(Map<String, String> map) {
        this.addItemBlueprint(this.mapToItem(map));
    }

    /**
     * Add an item blueprint to the game.
     * @param itemBlueprint The item blueprint to be added.
     */
    public void addItemBlueprint(Item itemBlueprint) {
        this.itemBlueprints.add(itemBlueprint);
    }

    /**
     * Get the room with a certain Id.
     * @param id The id of the room being searched for.
     * @return The room with a matching Id, else null.
     */
    public Room getRoomWithId(int id) {
        for (Room room : this.getRooms()) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    /**
     * Get the item blueprint with a certain Id.
     * @param id The id of the item blueprint being searched for.
     * @return The item blueprint with a matching Id, else null.
     */
    public Item getItemBlueprintWithId(int id) {
        for (Item item : this.getItemBlueprints()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * Creates an Item from a Map.
     * @param map A Map containing Item information.
     * @return An Item with as much information loaded as possible from the HashMap.
     */
    public Item mapToItem(Map<String, String> map) {
        int newId = Integer.parseInt(map.get("id") != null ? map.get("id") : "-1");
        Item newItem = newItemOfType(map.get("type"), this.getItemBlueprintWithId(newId)); // create item
        newItem.setId(newId); // in case blueprint is missing
        if (map.containsKey("name")) {
            newItem.setName(map.get("name"));
        }
        newItem.setType(map.containsKey("type") ? map.get("type") : newItem.getType());
        if (map.containsKey("description")) {
            newItem.setDescription(map.get("description"));
        }
        newItem.setXyLocation(new Point(map.containsKey("x") ? Integer.parseInt(map.get("x")) : 0,
                                        map.containsKey("y") ? Integer.parseInt(map.get("y")) : 0));
        if (map.containsKey("room")) {
            newItem.setIntendedRoomId(Integer.parseInt(map.get("room")));
        }
        return newItem;
    }

    /**
     * Create a new Item of the proper subclass.
     * @param type A String representaion of the subclass.
     * @param blueprint The blueprint from which to create the new Item.
     * @return The new item.
     */
    public Item newItemOfType(String type, Item blueprint) {
        type = type == null && blueprint != null ? blueprint.getClass().getSimpleName().toUpperCase() : type;
        if ("FOOD".equals(type)) {
            return new Food(blueprint);
        } else if ("SMALLFOOD".equals(type)) {
            return new SmallFood(blueprint);
        } else if ("CLOTHING".equals(type)) {
            return new Clothing(blueprint);
        } else if ("MAGIC".equals(type)) {
            return new Magic(blueprint);
        } else if ("POTION".equals(type)) {
            return new Potion(blueprint);
        } else if ("RING".equals(type)) {
            return new Ring(blueprint);
        }
        return new Item(blueprint);
    }

    /**
     * Creates a Room from a Map.
     * @param map A Map containing Room information.
     * @return An Room with as much information loaded as possible from the HashMap.
     */
    public Room mapToRoom(Map<String, String> map) {
        Room newRoom = new Room();
        newRoom.setWidth(Integer.parseInt(map.get("width")));
        newRoom.setHeight(Integer.parseInt(map.get("height")));
        newRoom.setId(Integer.parseInt(map.get("id")));
        newRoom.setStart(Boolean.parseBoolean(map.get("start")));
        for (String dir : Rogue.DIRS) {
            if (map.containsKey(dir)) { // if a door on this wall exists
                newRoom.addDoor(dir, Integer.parseInt(map.get(dir)), Integer.parseInt(map.get(dir + "_con_room")));
            }
        }
        newRoom.setTileSet(this.parser.getSymbols());
        newRoom.setDungeon(this);
        newRoom.setRoomItems(this.getItemBlueprints());
        return newRoom;
    }

    /** Connects rooms to each other. */
    public void connectRooms() {
        this.connectPairedDoors();
        this.connectBadlyPairedDoors();
        this.connectUnpairedDoors();
    }

    /** Connects doors that are properly paired. */
    private void connectPairedDoors() {
        for (Room room : this.getRooms()) { // for each room
            for (String dir : Rogue.DIRS) { // for each dir
                if (room.getDoor(dir) != null  // if room has a door on dir wall
                && !room.getDoor(dir).isComplete()) { // if it is an incomplete door
                    Room toConnect = this.getRoomWithId(room.getDoor(dir).getConnection()); // get room to connect to
                    if (toConnect != null // if room to connect to exists
                    && toConnect.getDoor(Rogue.oppositeDir(dir)) != null // if room to connect to has door on opp wall
                    && !(toConnect.getDoor(Rogue.oppositeDir(dir)).isComplete()) // door is not already paired
                    && toConnect.getDoor(Rogue.oppositeDir(dir)).getConnection() == room.getId()) { // if pair is meant
                        room.getDoor(dir).absorb(toConnect.getDoor(Rogue.oppositeDir(dir))); // absorb door b into a
                    }
                }
            }
        }
    }

    /** Connects doors that are improperly paired, but a pairing still exists (i.e. wrong door on the wrong wall). */
    private void connectBadlyPairedDoors() {
        for (Room room : this.getRooms()) { // for each room
            for (String dir : Rogue.DIRS) { // for each dir
                if (room.getDoor(dir) != null  // if room has a door on dir wall
                && !room.getDoor(dir).isComplete()) { // if it is an incomplete door
                    Room toConnect = this.getRoomWithId(room.getDoor(dir).getConnection()); // get room to connect to
                    if (toConnect != null) { // if room to connect to exists
                        for (int i = 0; i < Rogue.DIRS.length && !room.getDoor(dir).isComplete(); i++) {
                            if (toConnect.getDoor(Rogue.DIRS[i]) != null // if room to connect to has door on opp wall
                            && !(toConnect.getDoor(Rogue.DIRS[i]).isComplete())
                            && toConnect.getDoor(Rogue.DIRS[i]).getConnection() == room.getId()) { // if pair is meant
                                room.getDoor(dir).absorb(toConnect.getDoor(Rogue.DIRS[i])); // absorb door b into a
                            }
                        }
                    }
                }
            }
        }
    }

    /** Connects any remaining unpaired doors to randomly added doors in the appropriate rooms. */
    private void connectUnpairedDoors() {
        for (Room room : this.getRooms()) { // for each room
            for (String dir : Rogue.DIRS) { // for each dir
                if (room.getDoor(dir) != null  // if room has a door on dir wall
                && !room.getDoor(dir).isComplete()) { // if it is an incomplete door
                    Room toConnect = this.getRoomWithId(room.getDoor(dir).getConnection()); // get room to connect to
                    if (toConnect != null) {
                        Door newDoor = toConnect.addRandomDoor();
                        if (newDoor != null) {
                            newDoor.absorb(room.getDoor(dir));
                        }
                    }
                }
            }
        }
    }

    /** Connects items to rooms. */
    public void connectItemsToRooms() {
        for (Item item : this.getItems()) {
            try {
                connectItemToRoom(item);
            } catch (ImpossiblePositionException e) {
                // this happens when an item cannot be placed in the room.
            }
        }
    }

    /**
     * Connects a single item to its room.
     * @param item The item to be connected.
     * @throws ImpossiblePositionException when the item could not be placed due to lack of space.
     */
    public void connectItemToRoom(Item item) throws ImpossiblePositionException {
        if (item.getCurrentRoom() == null) {
            Room dest = this.getRoomWithId(item.getIntendedRoomId());
            if (dest != null) {
                Point origin = item.getXyLocation();
                dest.adjust(origin);
                for (int i = 0; i < MAX_DISTRIBUTION_DISTANCE * MAX_DISTRIBUTION_DISTANCE; i++) {
                    try {
                        item.setXyLocation(spiral(origin, i, SPIRALMODE));
                        dest.addItem(item);
                        return;
                    } catch (NoSuchItemException e) {
                        return;
                    } catch (ImpossiblePositionException e) { // try again
                    }
                }
                throw new ImpossiblePositionException();
            }
        }
    }

    private static final int SPIRALMODE = 5;
    private static final int EIGHT = 8; // The number of edges + nodes in a square. Because checkstyle [MagicNumber]
    private static final int THREE = 3; // The index of the last side of a square. Because checkstyle [MagicNumber]
    private static final int FOUR = 4; // number of sides in a square. Because checkstyle [MagicNumber]
    /**
     * Takes a point and offsets it in a new point in a clockwise spiral pattern based off n.
     * @param center the starting point of the spiral
     * @param n The distance of from the center of the sprial to the point along a spiral path.
     * @param dir The starting point of the spiral (0 = top left, 1 = top middle, ... , 7 = middle left).
     * @return the nth point on the spiral centered at point
     */
    public static Point spiral(Point center, int n, int dir) {
        Point out = new Point(center);
        if (n == 0) {
            return out;
        }
        int ring = (int) (1 + Math.sqrt(n)) / 2;
        int branchLength = 2 * ring;
        int distance = n - (int) Math.pow(branchLength - 1, 2);
        distance = (distance + (int) ((FOUR * branchLength) * dir / EIGHT)) % (FOUR * branchLength);
        int branch = distance / branchLength;
        int location = distance % branchLength;
        spirallyDisplace(out, ring, branch, location);
        return out;
    }

    /**
     * Displace the point according to a spiral based off the provided constants.
     * @param p The point to be displaced.
     * @param ring number of rotations completed by the spiral.
     * @param branch The branch number on the ring.
     * @param location The location on the branch.
     */
    private static void spirallyDisplace(Point p, int ring, int branch, int location) {
        if (branch == 0) {
            p.translate(location - ring, ring);
        } else if (branch == 1) {
            p.translate(ring, ring - location);
        } else if (branch == 2) {
            p.translate(ring - location, -1 * ring);
        } else if (branch == THREE) { // this looks awful.
            p.translate(-1 * ring, location - ring);
        }
    }

    /**
     * Verifies the integrity of the dungeon.
     * @return true if dungeon layout is valid, else false
     */
    public boolean verifyDungeon() {
        for (Room room : this.getRooms()) {
            try {
                if (!room.verifyRoom()) {
                    return false;
                }
            } catch (NotEnoughDoorsException e) {
                if (connectRandomly(room) == null) {
                    // System.out.println("ERROR: The dungeon file cannot be used.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Connects a doorless room randomly to another room in the dungeon that already contains at least 1 door.
     * @param room A room without doors to be connected randomly.
     * @return The door representing the new random connection.
     */
    public Door connectRandomly(Room room) {
        Room dest = null;
        for (Room r : this.getRoomsShuffled()) {
            if (r != room && r.getDoors().size() < FOUR && r.getDoors().size() > 0) {
                dest = r;
                break;
            }
        }
        if (dest != null) {
            Door newDoor = dest.addRandomDoor();
            newDoor.absorb(room.addRandomDoor(Rogue.oppositeDir(newDoor.getDir(dest))));
            return newDoor;
        }
        return null;
    }

    /**
     * Loads the information from parser into the game.
     */
    public void loadFromParser() {
        for (Map<String, String> itemBlueprintMap : this.parser.getItemBlueprints()) {
            this.addItemBlueprint(this.mapToItem(itemBlueprintMap));
        }
        for (Map<String, String> roomMap : this.parser.getRooms()) {
            this.addRoom(this.mapToRoom(roomMap));
        }
        for (Map<String, String> itemMap : this.parser.getItems()) {
            this.addItem(this.mapToItem(itemMap));
        }

        this.connectRooms();
        this.connectItemsToRooms();
    }
}
