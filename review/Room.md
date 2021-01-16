| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :--------------: | :------------------: | :--------------------------: | :------------------------------: | :-------------: |
| public Room() | Default constructor. | width, height, id, items, player, doors, tileset, start, dungeon |  |  | 9
| public int getWidth() | Gets the width of the room. | width |  |  | 1
| public void setWidth(int newWidth) | Sets the width of the room. | width |  |  | 1
| public int getHeight() | Gets the height of the room. | height |  |  | 1
| public void setHeight(int newHeight) | Sets the height of the room. | height |  |  | 1
| public int getId() | Gets the ID of the room. | id |  |  | 1
| public void setId(int newId) | Sets the ID of the room. | id |  |  | 1
| public boolean isStart() | Checks if the room is the starting room. | start |  |  | 1
| public void setStart(boolean isStart) | Sets whether or not the room is the starting room. | start |  |  | 1
| public ArrayList<Item> getRoomItems() | Returns an ArrayList with all the room's blueprint items. | blueprints |  |  | 1
| public void setRoomItems(ArrayList<Item> newRoomItems) | Sets the list of room blueprint items. | blueprints |  |  | 1
| public ArrayList<Item> getItems() | Returns an ArrayList containing all the Item instances in the room. | items |  |  | 1
| public void setItems(ArrayList<Item> newItems) | Sets the ArrayList containing all the Item instances in the room. | items |  |  | 1
| public Player getPlayer() | Gets the player in the room. | player |  |  | 1
| public void setPlayer(Player newPlayer) | Sets the player in the room. | player |  |  | 1
| public Collection<Door> getDoors() | Gets a Collection containing all the doors. | doors |  |  | 1
| public Door getDoor(String direction) | Get the door in the direction wall. | doors |  |  | 1
| public void setDoor(Door door, String direction) | Sets the door in the specified wall. | doors |  |  | 5
| public void setDoor(String dir, int pos) | Sets a new room door | doors |  | Door() | 4
| public void addDoor(String dir, int pos, int connectionId) | Adds a door into the specified wall. | doors |  |  | 2
| public Door addRandomDoor() | Adds a door randomly in the room and returns it. |  | getDoor(), addRandomDoor() |  | 11
| public Door addRandomDoor(String dir) | Adds a door randomly in the specified wall and returns it. |  | getDoor(), setDoor() |  | 6
| public void setTileSet(HashMap<String, Character> newTileset) | Sets the tileset of the room. | tileset |  |  | 1
| public Character getTile(String tile) | Returns the character for a tile. | tileset |  |  | 1
| public Dungeon getDungeon() | Gets the Dungeon the room belongs to. | dungeon |  |  | 1
| public void setDungeon(Dungeon newdungeon) | Sets the dungeon the room belongs to. | dungeon |  |  | 1
| public void addItem(Item item) throws NoSuchItemException, ImpossiblePositionException | Adds an item to the room. | items | isTileEmpty(), getRoomItems() | item.getId(), item.getXyLocation(), item.setCurrentRoom() | 15
| public Item findItem(Point point) | Returns the item at the specified position. |  | getItems() | Item.getXyLocation() | 7
| public Item removeItemAt(Point point) | Removes the item at the specified position. |  | findItem(), getItems() |  | 3
| public boolean isPlayerInRoom() | Checks if the player is in the room. | player |  | Player.getCurrentRoom() | 1
| public Boolean isTileEmpty(Point point) | Check if the tile is empty. |  | getTilePopulation() |  | 1
| public int getTilePopulation(Point point) | Returns the number of entities on the specified point. |  | getWidth(), getHeight(), isPlayerInRoom(), getPlayer(), getItems() | Player.getXyLocation(), Item.getXyLocation() | 16
| public boolean verifyRoom() throws NotEnoughDoorsException | Verifies the integrity of the room. |  | verifyRoom() |  | 1
| public boolean verifyRoom(Room room) throws NotEnoughDoorsException | Verifies the integrity of a room. |  | isPlayerInRoom(), getTilePopulation(), getPlayer(), getItems, getDoors() | Player.getXyLocation(), item.getXyLocation(), Door.getConnectedRooms(), Door.getRoom() | 17
| public boolean playerIsNextTo(String dir) | Checks if the player is next to the door at dir. |  | isPlayerInRoom(), getDoor(), getPlayer() | Player.getXyLocation(), Door.getPosition() | 11
| public void adjust(Point point) | Adjusts a point to ensure it is in the room bounds. |  | getWidth(), getHeight() |  | 10
| public String displayRoom() | Returns a string representation of the room. |  | stringHeader(), stringMap() |  | 1
| public String stringHeader() | Returns a string header for the room map. | width | isStart() |  | 15
| public String stringMap() | Returns a visual representation of the room and its contents. |  | blankMap(), getTile(), isPlayerInRoom(), addDoorsToMap(), pointToGridIndex() | Player.getXyLocation(), Item.getXyLocation(), Item.getType() | 10
| private char[] addDoorsToMap(char[] chars) | Adds doors to the stringMap. |  | getDoor(), pointToGridIndex(), getTile(), getHeight(), getWidth() | Door.getPosition(), Door.isComplete() | 17
| public String blankMap() | Creates a blank map of the room. |  | getWidth(), getTile(), getHeight() |  | 17
| private int pointToGridIndex(Point point) | Converts a point to an index in the string map. |  | getWidth() |  | 2
