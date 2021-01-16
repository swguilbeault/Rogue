| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :--------------: | :------------------: | :--------------------------: | :------------------------------: | :-------------: |
| public Player() | Default constructor. | name, xyLocation, currenRoom, inventory, currentItem, inventoryUpdate |  |  | 6
| public Player(String newName) | Creates player with specified name. | name | this() |  | 2
| public String getName() | Gets the name of the player. | name |  |  | 1
| public void setName(String newName) | Sets the name of the player. | name |  |  | 1
| public Point getXyLocation() | Gets the location of the player. | xyLocation |  |  | 1
| public void setXyLocation(Point newXyLocation) | Sets the location of the player. | xyLocation |  |  | 1
| public Room getCurrentRoom() | Gets the current room. | currentRoom |  |  | 1
| public void setCurrentRoom(Room newRoom) | Sets the current room. | currentRoom |  |  | 1
| public Item getCurrentItem() | Gets the item currently in focus in the inventory. | currentItem | getInventory() |  | 4
| public boolean addItem(Item item) | Adds the item to player's inventory. |  | suggestInventoryUpdate(), getInventory() |  | 6
| public ArrayList<Item> getInventory() | Retrieves an ArrayList containing every item in the player's inventory. | inventory |  |  | 1
| public void shiftInventoryFocus(int shift) | Shifts the inventories focus to a different item. | currentItem | getInventory() |  | 8
| public boolean checkInventoryUpdate() | Check whether the inventory has changed since last acknowledgement. | inventoryUpdate |  |  | 1
| public void acknowledgeInventoryUpdate() | Sets the inventory change tracking variable to false. | inventoryUpdate |  |  | 1
| public void suggestInventoryUpdate() | Sets the inventory change tracking variable to true. | inventoryUpdate |  |  | 1
| public String eat(Edible item) | Causes the player to eat the item if they have it. |  | getInventory(), suggestInventoryUpdate() | Edible.eat() | 6
| public String wear(Wearable item) | Causes the player to put on/take off the item if they have it. |  | getInventory(), suggestInventoryUpdate() | Wearable.wear() | 5
| public String toss(Item item) | Causes the player to toss the Item if they have it. |  | getInventory(), getCurrentRoom(), getXyLocation(), suggestInventoryUpdate() | Room.getId(), Item.setIntendedRoomId(), Item.setXyLocation(), Item.setCurrentRoom(), Room.getDungeon(), Dungeon.connectItemToRoom(), Tossable.toss() | 16
| public String pickUpItem() | Causes the player to pick up the item at its feet. |  | getCurrentRoom(), getXyLocation(), addItem() | Room.removeItemAt(), Item.getName(), Item.getDescription(), Item.setCurrentRoom() | 6
