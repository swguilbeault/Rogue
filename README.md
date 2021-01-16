# CIS*2430 Assignment 3: Rogue Prototype 3
Prototype for a roguelike game that loads and displays interactable ASCII representations of dungeon rooms that can be traversed by the player using the arrow keys or DXCV. The player may specify their name while they explore dungeon rooms and interact with the items they find through the player's inventory. Games may be saved, loaded, or created at any time.
### Controls
When the program is opened, the user must first load a dungeon. New dungeons may be loaded from JSON files using the "New dungeon from JSON" button in the menu located at the top of the window. If the selected JSON is a *file locations JSON* (contains a file location for a rooms JSON *AND* a symbols map JSOS: see fileLocations.json for an example of this type of file), then it will load a new dungeon for the specified locations and use the specified symbol map to print the room. If the selected JSON is a *rooms JSON*, then it will load the dungeon in this file and use the default symbols map. If the user wishes to change the tiles of a rooms json, they may:
* modify the *default-symbols.json* in the *resources* folder
* create a *file locations json* for the room and specify a *symbols json*
    * again, see *fileLocations.json* as a reference for how this file should be formatted
* (NOT recommended) change the default symbols file path on line 20 of RogueParser.java

The current game can also be saved and loaded at a later date using the "Save current game" and "Load savegame" buttons in the menu. Custom .json files should be made following the format of the *fileLocations.json*, *rooms.json*, and *symbols.json* files located in the resource folder.
The name of the player can be changed using the "Change player name" button in the menu. The player name is visible in the bottom-right corner of the GUI.
Once a dungeon is loaded, the player can move around the dungeon with arrow keys or DXCV. The game provides constant feedback to the player. This feedback is located in the bottom-left corner of the GUI.
The inventory can be opened in multiple ways. When opened, up/down or d/c may be used to select an item in the inventory, and the enter key attempts to use the item based upon how the inventory was opened. Backspace or 'i' can be pressed close the inventory and regain control of the character.
* 'i' opens the inventory to inspect the items
    * this is the only inventory mode which does not automatically close the inventory when using (inspecting) an item (successful use or not). 'i', backspace, or escape must be pressed to regain control of the character.
* 'w' opens the inventory to wear an items
* 'e' opens the inventory to eat an items
* 't' opens the inventory to toss an items
### Compilation
To compile the program, ensure Gradle is installed and run `gradle build` from the primary directory.
### Usage
To run the program, ensure the program has been compiled and run the command `java -jar ./build/libs/a3.jar` or `gradle run` from the primary directory.

---------------------------------
###### Projected completed November 30, 2020
