package rogue;

import java.util.ArrayList;
import java.awt.Point;

import java.io.Serializable;

public class Door implements Serializable {
    private ArrayList<Room> connectedRooms;
    private int[] positions;
    private String[] dirs;
    private int toConnect;

    /** Class constructor. */
    public Door() {
        this.connectedRooms = new ArrayList<Room>(2);
        this.positions = new int[] {-1, -1};
        this.dirs = new String[] {null, null};
        this.toConnect = -1;
    }

    /**
     * Specify one of the two rooms that are attached through this door.
     * @param r the room to be connected.
     */
    public void connectRoom(Room r) {
        this.connectedRooms.add(r);
    }

    /**
     * Gets the rooms connected by the door.
     * @return an ArrayList containing the rooms connected by this door.
     */
    public ArrayList<Room> getConnectedRooms() {
        return this.connectedRooms;
    }

    /**
     * Gets the room the door connects the current room to.
     * @param currentRoom the current room.
     * @return the room connected to the current room through the door.
     */
    public Room getOtherRoom(Room currentRoom) {
        if (this.connectedRooms.get(0) == currentRoom) {
            return this.getRoom(1);
        }
            return this.getRoom(0);
        }

    /**
     * Returns the room at index.
     * @param index the index of the room returned.
     * @return the room at the index.
     */
    public Room getRoom(int index) {
        return this.connectedRooms.get(index);
    }

    /**
     * Set the position in the wall of the room.
     * @param room the room the position is related to.
     * @param pos the position along the wall in the room.
     */
    public void setPosition(Room room, int pos) {
        this.positions[room == this.getRoom(0) ? 0 : 1] = pos;
    }

    /**
     * Returns the position of the door in the room.
     * @param room the room of the door.
     * @return the position of the door in the room.
     */
    public int getPosition(Room room) {
        if (room == this.getRoom(0)) {
            return this.positions[0];
        } else if (room == this.getRoom(1)) {
            return this.positions[1];
        }
        return -1;
    }

    /**
     * Prepare connection for the second room.
     * @param id the ID of the room that is to be connected.
     */
    public void setConnection(int id) {
        this.toConnect = id;
    }

    /**
     * Returns the ID of the room to be connected.
     * @return the iD of the room to be connected.
     */
    public int getConnection() {
        return this.toConnect;
    }

    /**
     * Set the  the wall of the door in the room.
     * @param room the room the door is in.
     * @param dir the wall the door is along.
     */
    public void setDir(Room room, String dir) {
        this.dirs[room == this.getRoom(0) ? 0 : 1] = dir;
    }

    /**
     * Returns the wall  of the door in the room.
     * @param room the room of the door.
     * @return the wall of the door in the room.
     */
    public String getDir(Room room) {
        if (room == this.getRoom(0)) {
            return this.dirs[0];
        } else if (room == this.getRoom(1)) {
            return this.dirs[1];
        }
        return null;
    }

    /**
     * Check is the door is complete.
     * @return true if the door is complete, else false.
     */
    public boolean isComplete() {
        return this.getConnectedRooms().size() >= 2 && this.getRoom(0) != null && this.getRoom(1) != null;
    }

    /**
     * Takes the given door and assumes its data is the other half or this door.
     * @param otherDoor Door containing the other half of this door.
     */
    public void absorb(Door otherDoor) {
        Room otherRoom = otherDoor.getOtherRoom(null);
        this.connectRoom(otherRoom);
        this.setPosition(otherRoom, otherDoor.getPosition(otherRoom));
        this.setDir(otherRoom, otherDoor.getDir(otherRoom));
        otherRoom.setDoor(this, this.getDir(otherRoom));
    }

    /**
     * Moves the player through the door.
     * @param player the player being moved through the room.
     */
    public void moveThrough(Player player) {
        if (this.isComplete()) {
            player.getCurrentRoom().setPlayer(null);
            player.setCurrentRoom(this.getOtherRoom(player.getCurrentRoom()));
            player.getCurrentRoom().setPlayer(player);
            String dir = this.getDir(player.getCurrentRoom());
            int pos = this.getPosition(player.getCurrentRoom());

            if ("N".equals(dir)) {
                player.setXyLocation(new Point(pos, 1));
            } else if ("S".equals(dir)) {
                player.setXyLocation(new Point(pos, player.getCurrentRoom().getHeight() - 2));
            } else if ("E".equals(dir)) {
                player.setXyLocation(new Point(player.getCurrentRoom().getWidth() - 2, pos));
            } else if ("W".equals(dir)) {
                player.setXyLocation(new Point(1, pos));
            }
        }
    }

    /**
     * Returns a string representation of the item.
     * @return the string.
     */
    public String toString() {
        String out = "Door connects rooms: ";
        for (int i = 0; i < this.getConnectedRooms().size(); i++) {
              out += this.getRoom(i) != null ? this.getRoom(i).getId() : "null";
              out += " ";
              if (i < 2) {
                  out += "[" + this.getDir(this.getRoom(i)) + "] ";
              }
        }
        return out;
    }
}
