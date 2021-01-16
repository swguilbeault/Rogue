| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :--------------: | :------------------: | :--------------------------: | :------------------------------: | :-------------: |
| public RogueParser() | Default constructor. | rooms, itemBlueprints, items, symbols |  |  | 4
| public RogueParser(String filename) | Creates parser and fills with information from specified configuration JSON |  | this(), parse() |  | 2
| public RogueParser(File f) throws IllegalArgumentException | Creates parser and fills with information from given Configuration/Rooms JSON. |  | isValidConfigJSON(), parse() |  | 20
| public HashMap<String, Character> getSymbols() | Returns a HashMap of symbols for the game. | symbols |  |  | 1
| public ArrayList<Map<String, String>> getRooms() | Returns an ArrayList of HashMaps which represent Rooms. | rooms |  |  | 1
| public ArrayList<Map<String, String>> getItemBlueprints() | Returns an ArrayList of HashMaps which represent Item Blueprints. | itemBlueprints |  |  | 1
| public ArrayList<Map<String, String>> getItems() | Returns and ArrayList of HashMaps which represent Items. | items |  |  | 1
| public static boolean isValidConfigJSON(File f) | Check is a File is a configuration JSON. |  |  |  | 11
| public boolean parse(String filename) | Parses dungeon from the configuration file data. |  | parse() |  | 14
| private boolean parse(JSONObject roomsJSON, JSONObject symbolsJSON) | Parses out the dungeon information when all JSON Objects are known. |  | extractSymbols(), extractItemBlueprints(), extractRooms() |  | 10
| private void extractSymbols(JSONObject symbolsJSON) | Parses the symbols from the symbolsJSON. | symbols |  |  | 5
| private void extractRooms(JSONObject roomsJSON) | Parses all the room info from the roomsJSON. |  | extractRoom() |  | 4
| private void extractRoom(JSONObject roomJSON) | Parses a single room's info from the roomJSON. | rooms | parseRoomBasics(), parseRoomDoors(), extractRoomItems() |  | 5
| private void parseRoomBasics(HashMap<String, String> room, JSONObject roomJSON) | Parses the basic attributes of a room from the roomJSON. |  |  |  | 4
| private void parseRoomDoors(HashMap<String, String> room, JSONObject roomJSON) | Parses the Doors from the roomJSON. |  |  |  | 8
| private void extractRoomItems(JSONObject roomJSON) | Parses all the Items from the roomJSON. |  | extractItem() |  | 4
| private void extractItem(JSONObject itemJSON, JSONObject roomJSON) | Parses a single Item from the itemJSON and roomJSON. | items |  |  | 6
| private void extractItemBlueprints(JSONObject roomsJSON) | Parses all the Item Blueprints from the roomsJSON. |  | extractItemBlueprint |  | 4
| private void extractItemBlueprint(JSONObject itemBlueprintJSON) | Parses a single Item Blueprint from the itemBlueprintJSON. | itemBlueprints |  |  | 6
