| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :--------------: | :------------------: | :--------------------------: | :------------------------------: | :-------------: |
| public Door() | Default constructor | connectedRooms, positions, dirs, toConnect |  |  | 4
| public void connectRoom(Room r) | Connects the room to the door. | connectedRooms |  |  | 1
| public ArrayList<Room> getConnectedRooms() | Retuns an ArrayList containing the connected rooms. | connectedRooms |  |  | 1
| public Room getOtherRoom(Room currentRoom) | Get the room the door connects to. | connectedRooms |  |  | 13
| public Room getRoom(int index) | Get connected room by adding order. | connectedRooms |  |  | 1
| public void setPosition(Room room, int pos) | Sets the position of the door in the room. | positions | getRoom() |  | 1
| public int getPosition(Room room) | Get the position of the door in the room. | positions | getRoom() |  | 6
| public void setConnection(int id) | Set the intended other room ID. | toConnect |  |  | 1
| public int getConnection() | Get the intended other room ID. | toConnect |  |  | 1
| public void setDir(Room room, String dir) | Set the direction of the door. | dirs | getRoom() |  | 1
| public String getDir(Room room) | Get the wall the door is on in the room. | dirs | getRoom() |  | 6
| public boolean isComplete() | Checks if the door connects 2 rooms. |  | getConnectedRooms(), getRoom() |  | 1
| public void absorb(Door otherDoor) | Takes another half door and merges it with this half door. |  | connectRoom(), setPosition(), setDir() | Door.getPosition(), Door.getDir(), Room.setDoor() | 5
| public void moveThrough(Player player) | Updates the player's position when it moves through this door. |  | isComplete(), getOtherRoom(), getDir(), getPosition() | Player.getCurrentRoom(), Player.setCurrentRoom(), setXyLocation(), Room.getWidth(), Room.getHeight(), Room.setPlayer() | 17
| public String toString() | Returns a string representation of the door. |  | getConnectedRooms(), getRoom(), getDir() | Room.getId() | 9
