/*
 * TCSS 305 - Tetris
 * Deline Zent (Madeline)
 */

package view;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import model.Board;
 

/** Creates a Tetris Game.
 * 
 * @author Deline Zent (Madeline)
 * @version 41, 12/13/19
 */
public class TetrisFrame implements Observer {

    /** The default timer delay. */
    private static final int TIMER_SPEED = 1000;
    
    /** A default font for "Game Over" message. */
    private static final Font GAME_OVER_FONT = new Font("Cambria", 5, 50);
    
    /** The Tetris board.  */
    private Board myBoard;
    
    /** The JFrame window for the tetris. */
    private final JFrame myFrame;
    
    /** A Timer for the tetris. */
    private Timer myTimer;

    /** The status of the game. */
    private Boolean myGameStatus;
    
    /** The control of tetris pieces. */
    private TetrisKeyControls myControls;
    
    /** The score panel of the tetris. */
    private ScorePanel myScorePanel;
    
    /** The end game button. */
    private JMenuItem myEndGameButton;
    
    /** The pause button. */
    private JMenuItem myPauseButton;
    
    /**  The new game button. */
    private JMenuItem myNewGameButton;
    
    /** The quit button. */
    private JMenuItem myQuitButton;

    /** The main tetris panel to display pieces. */
    private TetrisPanel myTetrisPanel;
    
    /** The game over label. */
    private JLabel myYouLostLabel;
    
    /**
     * Constructs Tetris GUI and initializes all fields.
     */
    public TetrisFrame() {

        myFrame = new JFrame("T E T R I S");
      
        myGameStatus = false;
        setupGUI();
    }
     
    /**
     * Sets up graphical components.
     */
    private void setupGUI() {

        final TetrisPanelBackground panel = new TetrisPanelBackground();
        myTetrisPanel = new TetrisPanel();
        myTetrisPanel.setLayout(new GridBagLayout()); 
        panel.add(myTetrisPanel);

        final JMenuBar menu = setupMenuBar();
        myFrame.setJMenuBar(menu);
        
        myBoard = new Board();
        myControls = new TetrisKeyControls(myBoard);
        myControls.pauseGame(false);
        
        final JPanel sidePanel = new JPanel();
        final BoxLayout boxLayout = new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS);
        sidePanel.setLayout(boxLayout);
        
        final NextPiecePanel nextPiecePanel = new NextPiecePanel();
        final HelpPanel controlPanel = new HelpPanel();
        myScorePanel = new ScorePanel(myBoard, myTimer);
        
        final Box sideBox = new Box(BoxLayout.PAGE_AXIS);
        
        myTimer = new Timer(TIMER_SPEED, new TimerListener());
        
        sideBox.add(myScorePanel);
        sideBox.add(nextPiecePanel);
        sideBox.add(controlPanel);
        
        myYouLostLabel = new JLabel("You lost!");
        myYouLostLabel.setFont(GAME_OVER_FONT);
        myYouLostLabel.setForeground(Color.RED);
        
        sidePanel.add(sideBox);
        myFrame.add(sidePanel, BorderLayout.WEST);
        myFrame.addKeyListener(myControls);
        myFrame.add(panel); 
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        
        myBoard.addObserver(nextPiecePanel);
        myBoard.addObserver(myTetrisPanel);
        myBoard.addObserver(this);
    }

    /** 
     * If the board changes, the Tetris GUI changes the game status.
     * 
     * @param theObservable is the Board sending updates
     * @param theData is the boolean truth of if the game is still playing
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theObservable instanceof Board && theData instanceof Boolean) {
            myTimer.stop();
            myPauseButton.setEnabled(false);
            myEndGameButton.setEnabled(false);
            myNewGameButton.setEnabled(true);
            myTetrisPanel.add(myYouLostLabel);
            myFrame.validate();
        }
    }
    
    
    /**
     * This method creates a menu bar for the Tetris frame and returns it.
     * 
     * @return Returns a menu.
     */
    private JMenuBar setupMenuBar() {
        
        final JMenuBar menuBar = new JMenuBar();
        
        final JMenu optionsMenu = setupOptionsMenu();

        menuBar.add(optionsMenu);
        
        return menuBar;
    }
    
    /**
     * All options within the options menu. 
     * 
     * @return Returns the options menu.
     */
    private JMenu setupOptionsMenu() {
        
        final MenuActionListener menuActions = new MenuActionListener();
        final JMenu menu = new JMenu("Game Options");
        menu.setMnemonic(KeyEvent.VK_F);
        
        myNewGameButton = new JMenuItem("Start Game", KeyEvent.VK_N);
        myNewGameButton.addActionListener(menuActions);
        
        myPauseButton = new JMenuItem("Pause/Resume", KeyEvent.VK_P);
        myPauseButton.setEnabled(false);
        myPauseButton.setAccelerator(KeyStroke.getKeyStroke('p'));
        myPauseButton.addActionListener(menuActions);
        
        myEndGameButton = new JMenuItem("End Game", KeyEvent.VK_E);
        myEndGameButton.setEnabled(false);
        myEndGameButton.addActionListener(menuActions);
        
        myQuitButton = new JMenuItem("Quit", KeyEvent.VK_Q);
        myQuitButton.addActionListener(menuActions);
        
        menu.add(myNewGameButton);
        menu.add(myPauseButton);
        menu.addSeparator();
        menu.add(myEndGameButton);
        menu.addSeparator();
        menu.add(myQuitButton);
        
        return menu;
    }

    /**
     * The inner class timer steps the game for every second. 
     */
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
    }
    
    
    /**
     * Inner class creates actions for the game options.
     */
    private class MenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            
            // Create a new game
            if (theEvent.getSource() == myNewGameButton) {
               
                myGameStatus = true;
                myControls.pauseGame(true);
                myNewGameButton.setEnabled(false);
                myEndGameButton.setEnabled(true);
                myPauseButton.setEnabled(true);
                myControls.play("res/sounds/backgroundMusic.wav");
                myBoard.newGame();
                myScorePanel.clearScore();
                myTimer.start();
                myTetrisPanel.remove(myYouLostLabel);
                
            }
            
            // End the game
            if ((theEvent.getSource() == myEndGameButton) && myGameStatus) {
                
                myTetrisPanel.add(myYouLostLabel);
                myEndGameButton.setEnabled(false);
                myNewGameButton.setEnabled(true);
                myPauseButton.setEnabled(false);
                myTimer.stop();
                myControls.pauseGame(false);
                myTetrisPanel.validate();
                myTetrisPanel.repaint();
                
            }
            
            // Pause or resume the game
            if (theEvent.getSource() == myPauseButton) {
                
                // resume the game
                if (!myTimer.isRunning()) {
                    myTimer.start();
                    myControls.pauseGame(true);
                    
                // pause the game
                } else {
                    myTimer.stop();
                    myControls.pauseGame(false);
                }
            }
            
            // Quit Game
            if (theEvent.getSource() == myQuitButton) {
                myFrame.dispatchEvent(new WindowEvent(myFrame,
                                                      WindowEvent.WINDOW_CLOSING));
            }
            
        }
        
    }
     
    /**
     * Inner class that creates a picture background on the tetris frame.
     * 
     */
    private static final class TetrisPanelBackground extends JPanel {
        
        /** A generated serial ID. */
        private static final long serialVersionUID = 8412667021253374407L;
        
        /** A default color for the panel. */
        private static final Color MY_COLOR = new Color(6, 3, 17);

        /**
         * Constructs a panel with an image on the background.
         */
        TetrisPanelBackground() {
            
            super();
           
            setBackground(MY_COLOR);
            
        }

    }
    
}