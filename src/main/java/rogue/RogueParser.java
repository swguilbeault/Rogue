package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser implements Serializable {

    private static final String DEFAULTSYMBOLSJSON = "./resources/default-symbols.json";

    private ArrayList<Map<String, String>> rooms;
    private ArrayList<Map<String, String>> itemBlueprints;
    private ArrayList<Map<String, String>> items;
    private HashMap<String, Character> symbols;

    /** Default constructor. */
    public RogueParser() {
        this.rooms = new ArrayList<>();
        this.itemBlueprints = new ArrayList<>();
        this.items = new ArrayList<>();
        this.symbols = new HashMap<>();
    }

    /**
     * Class constructor. Loads information based on information in the specified config file.
     * @param filename The path of the config file.
     */
    public RogueParser(String filename) {
        this();
        this.parse(filename);
    }

    /**
     * Class constructor. Constructs normally if file is a configJSON. If the file is not a valid configJSON, it will
     * try and use the file as a rooms JSON accompanied with a defauly symbols JSON located at DEFAULTSYMBOLSJSON.
     * @param f the file to be loaded in.
     * @throws IllegalArgumentException if the file could not be read.
     */
    public RogueParser(File f) throws IllegalArgumentException {
        this();
        if (RogueParser.isValidConfigJSON(f)) {
            if (!parse(f.getAbsolutePath())) {
                throw new IllegalArgumentException("Failed to parse data from file locations in " + f.getName());
            }
        } else {
            JSONParser parser = new JSONParser();
            try {
                JSONObject roomsJSON = (JSONObject) parser.parse(new FileReader(f));
                JSONObject symbolsJSON = (JSONObject) parser.parse(new FileReader(DEFAULTSYMBOLSJSON));
                if (this.parse(roomsJSON, symbolsJSON)) {
                    return;
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } catch (ParseException e) {
            }
            throw new IllegalArgumentException("Failed to parse dungeon information from " + f.getName()
                                                + " and " + DEFAULTSYMBOLSJSON);
        }
    }

    /**
     * Gets a hashmap of symbols.
     * @return a capy of the hashmap of symbols
     */
    public HashMap<String, Character> getSymbols() {
        return new HashMap<String, Character>(this.symbols);
    }

    /**
     * Returns the room skeletons.
     * @return a capy of the ArrayList of room skeletons.
     */
    public ArrayList<Map<String, String>> getRooms() {
        return new ArrayList<>(this.rooms);
    }

    /**
     * Returns the item blueprint skeletons.
     * @return a capy of the ArrayList of item blueprint skeletons.
     */
    public ArrayList<Map<String, String>> getItemBlueprints() {
        return new ArrayList<>(this.itemBlueprints);
    }

    /**
     * Returns the item skeletons.
     * @return a capy of the ArrayList of item skeletons.
     */
    public ArrayList<Map<String, String>> getItems() {
        return new ArrayList<>(this.items);
    }

    /**
     * Checks if a file is a configJSON.
     * @param f The file which may be a configuration JSON.
     * @return true if the file is a valid config JSON, else false.
     */
    public static boolean isValidConfigJSON(File f) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject configJSON = (JSONObject) parser.parse(new FileReader(f));
            if (configJSON.get("Rooms") != null && !"".equals(configJSON.get("Rooms"))
                && configJSON.get("Symbols") != null && !"".equals(configJSON.get("Symbols"))) {
                return true;
            }
        } catch (IOException e) {
        } catch (ParseException e) {
        }
        return false;
    }

    /**
     * Loads information based from the files specified the specified config file.
     * @param filename The path of the config file.
     * @return false if there was some sort of parsing error.
     */
    public boolean parse(String filename) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject configJSON = (JSONObject) parser.parse(new FileReader(filename));
            JSONObject roomsJSON = (JSONObject) parser.parse(new FileReader((String) configJSON.get("Rooms")));
            JSONObject symbolsJSON = (JSONObject) parser.parse(new FileReader((String) configJSON.get("Symbols")));
            return parse(roomsJSON, symbolsJSON);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }
        return false;
    }

    private boolean parse(JSONObject roomsJSON, JSONObject symbolsJSON) {
        try {
            this.extractSymbols(symbolsJSON);
            this.extractItemBlueprints(roomsJSON);
            this.extractRooms(roomsJSON);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Extracts the symbols for the game.
     * @param symbolsJSON JSONObject containing information about the symbols.
     */
    private void extractSymbols(JSONObject symbolsJSON) {
        JSONArray symbolJSONs = (JSONArray) symbolsJSON.get("symbols");
        for (int i = 0; i < symbolJSONs.size(); i++) {
            JSONObject symbolJSON = (JSONObject) symbolJSONs.get(i);
            this.symbols.put(symbolJSON.get("name").toString(), String.valueOf(symbolJSON.get("symbol")).charAt(0));
        }
    }

    /**
     * Extracts all the room information for the game.
     * @param roomsJSON JSONObject Containing all the information about each room.
     */
    private void extractRooms(JSONObject roomsJSON) {
        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            this.extractRoom((JSONObject) roomsJSONArray.get(i));
        }
    }

    /**
     * Extract the room information of a single room..
     * @param roomJSON JSONObject Containing the information about a room.
     */
    private void extractRoom(JSONObject roomJSON) {
        HashMap<String, String> room = new HashMap<>();
        parseRoomBasics(room, roomJSON);
        parseRoomDoors(room, roomJSON);
        extractRoomItems(roomJSON);
        this.rooms.add(room);
    }

    /**
     * Helper function. Parses basic room data into room skeleton
     * @param room The room skeleton.
     * @param roomJSON JSONObject containing information about the room.
     */
    private void parseRoomBasics(HashMap<String, String> room, JSONObject roomJSON) {
        room.put("id", roomJSON.get("id").toString());
        room.put("start", roomJSON.get("start").toString());
        room.put("height", roomJSON.get("height").toString());
        room.put("width", roomJSON.get("width").toString());
    }

    /**
     * Helper function. Parses door data into room skeleton
     * @param room The room skeleton.
     * @param roomJSON JSONObject containing information about the room.
    */
    private void parseRoomDoors(HashMap<String, String> room, JSONObject roomJSON) {
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int i = 0; i < doorArray.size(); i++) {
            JSONObject doorJSON = (JSONObject) doorArray.get(i);
            String dir = String.valueOf(doorJSON.get("dir"));
            room.put(dir, doorJSON.get("wall_pos").toString());
            room.put(dir + "_con_room", doorJSON.get("con_room").toString());
        // room.put(dir, doorJSON.get("wall_pos").toString() + ":" + doorJSON.get("con_room").toString()); // pos:dest
        }
    }

    /** Helper function. Extracts item data from the room
     * @param roomJSON JSONObject containing information about the room.
     */
    private void extractRoomItems(JSONObject roomJSON) {
        JSONArray itemJSONs = (JSONArray) roomJSON.get("loot");
        for (int j = 0; j < itemJSONs.size(); j++) {
            this.extractItem((JSONObject) itemJSONs.get(j), roomJSON);
        }
    }

    /**
     * Create a map for information about an item in a room.
     * @param itemJSON (JSONObject) Loot key from the rooms file
     * @param roomJSON JSONObject containing information about the room.
     */
    private void extractItem(JSONObject itemJSON, JSONObject roomJSON) {
        HashMap<String, String> item = new HashMap<>();
        item.put("room", roomJSON.get("id").toString());
        item.put("id", itemJSON.get("id").toString());
        item.put("x", itemJSON.get("x").toString());
        item.put("y", itemJSON.get("y").toString());
        this.items.add(item);
    }

    /**
     * Extracts the information for all possible item types in the game.
     * @param roomsJSON JSONObject containing the information about all the possible items in the game.
     */
    private void extractItemBlueprints(JSONObject roomsJSON) {
        JSONArray blueprintJSONs = (JSONArray) roomsJSON.get("items");
        for (int i = 0; i < blueprintJSONs.size(); i++) {
            this.extractItemBlueprint((JSONObject) blueprintJSONs.get(i));
        }
    }

    /**
     * Extract the information for a possible item type in the game.
     * @param itemBlueprintJSON JSONObject containing the information for an item blueprint.
     */
    private void extractItemBlueprint(JSONObject itemBlueprintJSON) {
        HashMap<String, String> itemBlueprint = new HashMap<>();
        itemBlueprint.put("id", itemBlueprintJSON.get("id").toString());
        itemBlueprint.put("name", itemBlueprintJSON.get("name").toString());
        itemBlueprint.put("description", (String) itemBlueprintJSON.get("description"));
        itemBlueprint.put("type", itemBlueprintJSON.get("type").toString().toUpperCase());
        this.itemBlueprints.add(itemBlueprint);
    }
}
