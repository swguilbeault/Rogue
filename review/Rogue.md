 | method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
 | :----------: | :--------------: | :------------------: | :--------------------------: | :------------------------------: | :-------------: |
 | public Rogue() | Default constructor | player, dungeon, tileset, controlMode |  | Player(), Dungeon() | 4
 | public Rogue(RogueParser theDungeonInfo) | Constructs class based on parser data | dungeonInfo, dungeon | createRooms() | Dungeon() | 4
 | public void setPlayer(Player thePlayer) | Sets the Player of the game | player | getPlayer() | Player.getCurrentRoom(), Room.setPlayer() | 5
 | public void setSymbols() | Sets the symbols for the game | tileset | getDungeon() | Dungeon.getSymbols() | 1
 | public ArrayList<Room> getRooms() | Gets all the Rooms in the game |  | getDungeon() | Dungeon.getRooms() | 1
 | public ArrayList<Item> getItems() | Gets all the Items of the game |  | getDungeon() | Dungeon.getItems() | 1
 | public Player getPlayer() | Gets the Player of the game | player |  |  | 1
 | public ArrayList<Item> getItemBlueprints() | Gets all the Item Blueprints in the game. |  | getDungeon() | Dungeon.getItemBlueprints() | 1
 | public Dungeon getDungeon() | Gets the Dungeon loaded into the game. | dungeon |  |  | 1
 | public void createRooms() | Calls the Dungeon class to extract the information to the parser and create the dungeon for the game. | dungeon | getDungeon() | Dungeon.loadFromParser(), Dungeon.getStartingRoom(), Room.setPlayer(), Player.setCurrentRoom(), Dungeon.verifyRoom() | 9
 | public void addRoom(Map<String, String> toAdd) | Passes the Room map to add into the Dungeon class to add |  | getDungeon() | Dungeon.addRoom() | 1
 | public void addItem(Map<String, String> toAdd) | Passes the Item map to add into the Dungeon class to add |  | getDungeon() | Dungeon.addItemBlueprint(), Dungeon.addItem() | 2
 | public int getControlMode() | Gets the primary focus of keyinput handler | controlMode |  |  | 1
 | public void printItems() | Prints all the items in the game to the terminal. |  | getDungeon() | Dungeon.getItems() | 5
 | public void printItemBlueprints() | Prints all the item blueprints in the game to the terminal. |  | getItemBlueprints() |  | 5
 | public String displayAll() | Prints all the rooms in the game to the terminal. |  | getDungeon(), getRooms() | Dungeon.getRooms(), Room.displayRoom() | 8
 | public String getNextDisplay() | Get the display string for the current room. |  | getPlayer() | Player.getCurrentRoom(), Room.stringMap() | 1
 | public String makeMove(char input) throws InvalidMoveException | Passes the move to the appropriate move controller based on the current control mode. | controlMode | makeMovementMove(), makeInventoryMove() |  | 7
 | private String makeMovementMove(char input) throws InvalidMoveException | Handles inputs and calls appropriate action when the control mode is focused on player movement. | controlMode, invMode | movePlayer(), getPlayer() | Player.suggestInventoryUpdate() | 16
 | private String makeInventoryMove(char input) | Handles the inputs and calls appropriate action when the control mode is focused on inventory. | controlMode | getPlayer(), useItem() | Player.shiftInventoryFocus(), Player.getCurrentItem() | 12
 | private String useItem(Item item) | Passes an appropriately casted Item to the Player to use. | controlMode, invMode | getPlayer() | Player.eat(), Player.wear(), Player.toss(), Item.getName(), Item.getDescription() | 14
 | public String movePlayer(int x, int y) throws InvalidMoveException | Moves the player by the specified distance if the move is possible. |  | getPlayer() | Player.getXyLocation(), Player.getCurrentRoom(), Room.getWidth(), Room.getHeight(), Player.pickUpItem(), Room.playerIsNextTo(), Room.getDoor(), Door.moveThrough() | 17
 | public static String oppositeDir(String dir) | Returns the opposite cardinal direction. |  |  |  | 10
