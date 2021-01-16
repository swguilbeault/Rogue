package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TextColor.RGB;
// import com.googlecode.lanterna.TextColor;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.File;

public class WindowUI extends JFrame implements ActionListener {

    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 800;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private static final String MENUSAVE = "Save current game";
    private static final String MENULOAD = "Load savegame";
    private static final String MENUNEW = "New game from JSON";
    private static final String MENUNAME = "Change player name";
    private static final String[] MENUOPTIONS = new String[] {MENUSAVE, MENULOAD, MENUNEW, MENUNAME};
    private static final int ADDITIONALTERMINALHEIGHT = 200;
    private static final int BCKSP = 8;

    // border widths; here because checkstyle [MagicNumber]
    private static final int B4 = 4;
    private static final int B10 = 10;

    // All the values used in Colors; here because checkstyle [MagicNumber]
    private static final int C7 = 7;
    private static final int C9 = 9;
    private static final int C10 = 10;
    private static final int C12 = 12;
    private static final int C14 = 14;
    private static final int C15 = 15;
    private static final int C20 = 20;
    private static final int C120 = 120;
    private static final int C150 = 150;
    private static final int C195 = 195;
    private static final int C200 = 200;
    private static final int C220 = 220;
    private static final int C240 = 240;
    private static final int C255 = 255;

    private final char startCol = 0;
    private final char msgRow = 1;
    private final char roomRow = 0;
    private JFileChooser fc;

    private Container contentPane;
    private JPanel itemList;
    private JLabel messageLabel;
    private JLabel playerNameLabel;
    private Rogue theGame;

    private static final String INVENTORYTEXT =
        "<html><p style=\"color:rgb(120,120,120)\">This is your inventory."
        + "<br/> You may press '" + Rogue.OPENINV + "' (inspect),<br>'" + Rogue.OPENINVEAT
        + " (eat), '" + Rogue.OPENINVWEAR + "' (wear), or '" + Rogue.OPENINVTOSS
        + "' (toss) to <br/>open the inventory at any time. <br/> Use the up/down arrowkeys to"
        + " navigate<br/>the menu and press enter to<br/>select the item.<br/>Backspace or '"
        + Rogue.OPENINV + "' can be pressed to<br/>close the inventory and resume<br/>control "
        + "of your character.</p></html>";

    /** Constructor. */
    public WindowUI() {
        super("my awesome game");
        contentPane = getContentPane();
        setWindowDefaults(getContentPane());
        setUpPanels();
        setUpFileChooser();
        pack();
        startScreen();
        setupGame();
    }

    private void setWindowDefaults(Container pane) {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane.setLayout(new GridBagLayout());
    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminal.setBorder(BorderFactory.createEmptyBorder());
        terminal.setForegroundColor(new RGB(0, 0, 0));
        terminalPanel.add(terminal);
        terminalPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        terminalPanel.setBackground(Color.black);
        contentPane.add(terminalPanel, c(0, 0, 1, 1));
    }

    private void setUpPanels() {
        setUpPlayerNameLabel();
        setUpMessagePanel();
        setTerminal();
        setUpInventoryPanel();
        setUpJMenuBar();
    }

    private void setUpFileChooser() {
        this.fc = new JFileChooser();
        try {
            File f = new File(new File("./resources").getCanonicalPath());
            fc.setCurrentDirectory(f);
        } catch (IOException e) {
        }
    }

    private void setUpPlayerNameLabel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(C15, C15, C15));
        panel.setBorder(BorderFactory.createEmptyBorder(B4, B4, B4, B4));
        playerNameLabel = new JLabel("author @swguilbeault", SwingConstants.LEFT);
        playerNameLabel.setForeground(new Color(C255, C220, C220));
        panel.add(playerNameLabel);
        contentPane.add(panel, c(1, 1, 1, 1));
    }

    private void setUpMessagePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(B4, B4, B4, B4));
        panel.setBackground(new Color(C12, C12, C12));
        messageLabel = new JLabel("message label", SwingConstants.CENTER);
        messageLabel.setForeground(new Color(C195, C240, C255));
        panel.add(messageLabel);
        contentPane.add(panel, c(0, 1, 1, 1));
    }

    private void setUpInventoryPanel() {
        itemList = new JPanel();
        itemList.setBorder(BorderFactory.createEmptyBorder(B10, B10, B10, B10));
        itemList.setBackground(new Color(C9, C9, C9));
        itemList.setLayout(new GridLayout(0, 1));
        JLabel temp = new JLabel("                                                                                   ");
        temp.setMinimumSize(new Dimension(ADDITIONALTERMINALHEIGHT, 0));
        itemList.add(temp);
        contentPane.add(itemList, c(1, 0, 1, 1));
    }

    private void setUpJMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.setBorder(BorderFactory.createEmptyBorder());
        bar.setBackground(new Color(C20, C20, C20));
        JMenu menu = new JMenu("Menu");
        menu.setForeground(new Color(C120, C120, C120));

        for (String t : MENUOPTIONS) {
            JMenuItem menuItem = new JMenuItem(t);
            menuItem.addActionListener(this);
            menu.add(menuItem);
        }


        bar.add(menu);
        this.setJMenuBar(bar);
    }

                                                                                              /// Action Controllers ///
    /**
     * Controller method for WindowUI's actions.
     * @param e Details about the action that occured.
     */
    public void actionPerformed(ActionEvent e) {
        if (MENUSAVE.equals(e.getActionCommand())) {
            this.saveGame();
        } else if (MENULOAD.equals(e.getActionCommand())) {
            this.loadGameFromFile();
        } else if (MENUNAME.equals(e.getActionCommand())) {
            this.changePlayerName();
        } else if (MENUNEW.equals(e.getActionCommand())) {
            this.newGame();
        }
    }

    private void saveGame() {
        if (theGame != null) {
            if (fc.showSaveDialog(this.contentPane) == JFileChooser.APPROVE_OPTION) {
                theGame.getPlayer().suggestInventoryUpdate();
                try {
                    ObjectOutputStream serialFile = new ObjectOutputStream(new FileOutputStream(fc.getSelectedFile()));
                    serialFile.writeObject(theGame);
                    serialFile.close();
                    this.setMessage("Successfully saved the game at " + fc.getSelectedFile().getName());
                    return;
                } catch (IOException e) {
                }
                this.setMessage("Failed to save game at " + fc.getSelectedFile().getAbsolutePath());
                this.displayErrorPopup("Could not save Rogue instance at '" + fc.getSelectedFile().getAbsolutePath()
                                        + "'. Please try again.", "Save error");
            }
        } else {
            this.displayErrorPopup("Saving the game requires a game to be loaded", "Save error");
        }
    }

    private void loadGameFromFile() {
        if (fc.showOpenDialog(this.contentPane) == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fileIn = new FileInputStream(fc.getSelectedFile());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                theGame = (Rogue) in.readObject();
                in.close();
                fileIn.close();
                playerNameLabel.setText("[" + theGame.getPlayer().getName() + "]");
                this.draw("Successfully loaded game at " + fc.getSelectedFile().getName());
                return;
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
            this.setMessage("Failed to load game from " + fc.getSelectedFile().getName());
            this.displayErrorPopup("Could not load Rogue instance from '" + fc.getSelectedFile().getAbsolutePath()
                                   + "'. Please try again. Did you mean to create a new game from a JSON file?",
                                   "Load error");
        }
    }

    private void changePlayerName() {
        if (theGame != null) {
            String name = JOptionPane.showInputDialog("New name:");
            if (name != null) {
                name = name.trim();
                if (!"".equals(name)) {
                    this.setMessage("changed player name from \"" + theGame.getPlayer().getName()
                                     + "\" to \"" + name + "\"");
                    theGame.getPlayer().setName(name);
                    playerNameLabel.setText("[" + theGame.getPlayer().getName() + "]");
                } else {
                    this.displayErrorPopup("Names must contain at least one non-whitespace character",
                                           "invalid name");
                }
            }
            return;
        }
        this.displayErrorPopup("A player is required to perform this function", "missing player");
    }

    private void newGame() {
        if (fc.showOpenDialog(this.contentPane) == JFileChooser.APPROVE_OPTION) {
            try {
                theGame = new Rogue(new RogueParser(fc.getSelectedFile())); // create game from rooms or config JSON
                theGame.setPlayer(new Player("Player Name")); //set up the initial game display
                if (theGame.getDungeon() == null) {
                    throw new NotEnoughDoorsException(); // weird work around for parsing error and checkstyle
                }
                theGame.setSymbols();
                playerNameLabel.setText("[" + theGame.getPlayer().getName() + "]");
                this.draw("Successfully loaded a new game.");
                return;
            } catch (Exception e) {
                this.displayErrorPopup("New dungeon configuration information was bad or could not be retreived from '"
                                  + fc.getSelectedFile().getAbsolutePath() + "' Please try again.", "JSON Error");
            }
            this.setMessage("The file " + fc.getSelectedFile().getName() + " could not be used.");
        }
    }

    private void displayErrorPopup(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private GridBagConstraints c(int gridX, int gridY, int gridWidth, int gridHeight) {
        GridBagConstraints out = new GridBagConstraints();
        out.gridx = gridX;
        out.gridy = gridY;
        out.gridwidth = gridWidth;
        out.gridheight = gridHeight;
        out.fill = GridBagConstraints.BOTH;
        return out;
    }

    private void startScreen() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupGame() {
        char userInput = 'h';
        String message;
        message = "<   This is Rogue. Load or create a new game to begin.   >";
        this.setMessage(message);
        this.setVisible(true);
        while (userInput != 'q') {
            userInput = this.getInput(); //get input from the user
            if (theGame != null) {
                try { //ask the game if the user can move there
                    message = theGame.makeMove(userInput);
                    this.draw(message);
                } catch (InvalidMoveException badMove) {
                    this.draw("You cannot move there");
                }
            }
        }
    }

    /**
     * Prints a string to the screen starting at the indicated column and row.
     * @param toDisplay the string to be printed
     * @param column the column in which to start the display
     * @param row the row in which to start the display
    **/
    public void putString(String toDisplay, int column, int row) {
        Terminal t = screen.getTerminal();
        try {
            t.setCursorPosition(column, row);
        for (char ch: toDisplay.toCharArray()) {
            t.putCharacter(ch);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the message at the top of the screen for the user.
     * @param msg the message to be displayed
    **/
    public void setMessage(String msg) {
        messageLabel.setText(msg);
    }

    /**
     * Redraws the whole screen including the room and the message.
     * @param message the message to be displayed at the top of the room
    **/
    public void draw(String message) {
        if (theGame != null) {
            try {
                if (theGame.getControlMode() == Rogue.INVENTORY) {
                    itemList.setBackground(new Color(C14, C14, C14));
                } else if (theGame.getControlMode() == Rogue.MOVEMENT) {
                    itemList.setBackground(new Color(C7, C7, C7));
                }
                this.drawRoom(message);
                this.updateInventory();
            } catch (IOException e) {
                System.out.println("IO Exception");
            }
        }
    }

    private void drawRoom(String message) throws IOException {
        screen.getTerminal().clearScreen();
        setMessage(message);
        putString(theGame.getNextDisplay(), startCol, roomRow);
        screen.refresh();
    }

    private void updateInventory() {
        if (theGame.getPlayer().checkInventoryUpdate()) {
            addInventoryHeader();
            for (Item item : theGame.getPlayer().getInventory()) {
                itemList.add(newItemTitleJLabel(item));
            }
            itemList.add(new JLabel("                                                                               "));
            itemList.revalidate();
            itemList.repaint();
            pack();
        }
    }

    private void addInventoryHeader() {
        theGame.getPlayer().acknowledgeInventoryUpdate();
        itemList.removeAll();
        itemList.add(new JLabel("                                                                               "));
        JLabel temp = new JLabel(theGame.getPlayer().getInventory().size() == 0 ? INVENTORYTEXT : "INVENTORY");
        temp.setForeground(Color.white);
        itemList.add(temp);
    }

    private JLabel newItemTitleJLabel(Item item) {
        String text = item.getInventoryTitle();
        JLabel label = new JLabel(item.getName());
        label.setForeground(new Color(C150, C150, C150));
        if (item == theGame.getPlayer().getCurrentItem() && theGame.getControlMode() == Rogue.INVENTORY) {
            text = "> " + text; // + "<br>" + item.getDescription();
            label.setBackground(new Color(C10, C10, C10));
            label.setForeground(new Color(C255, C255, C195));
            // label.setOpaque(true);
        }
        label.setText("<html>" + text + "</html>");
        return label;
    }

    /**
     * Obtains input from the user and returns it as a char.  Converts arrow
     * keys to the equivalent movement keys in rogue.
     * @return the ascii value of the key pressed by the user
     */
    public char getInput() {
        KeyStroke keyStroke = null;
        while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        return this.adjustKeystroke(keyStroke);
    }

    /**
     * Gets the char from the keyStroke.
     * @param keyStroke The keyStroke.
     * @return The char presesd.
     */
    private char adjustKeystroke(KeyStroke keyStroke) {
        if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            return Rogue.DOWN;  //constant defined in rogue
        } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            return Rogue.UP;
        } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            return Rogue.LEFT;
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            return Rogue.RIGHT;
        } else {
            try {
                return keyStroke.getCharacter();
            } catch (NullPointerException e) {
                return (char) BCKSP;
            }
        }
    }

    /**
     * The controller method for making the game logic work.
     * @param args command line parameters
     */
    public static void main(String[] args) {
        new WindowUI();
    }
}
