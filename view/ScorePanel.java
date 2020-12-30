package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import model.Board;

/** The panel which keeps track of the score.

 * @author Deline Zent (Madeline)
 * @version 38, 12/13/19
 *
 */
public class ScorePanel extends JPanel implements Observer {
    
    /** A serial ID number. */
    private static final long serialVersionUID = 8334748585986597893L;
    
    /** The second level int. */
    private static final int LEVEL_2 = 2;
    
    /** The third level int. */
    private static final int LEVEL_3 = 3;
    
    /** The fourth level int. */
    private static final int LEVEL_4 = 4;
    
    /** The fifth level int. */
    private static final int LEVEL_5 = 5;
    
    /** The dimensions of the panel. */
    private static final Dimension PANEL_SIZE = new Dimension(210, 120);   
    
    /** The font for the panel. */
    private static final String MY_FONT = "Courier";
    
    /** The font of the title border. */
    private static final Font LABEL_FONT = new Font(MY_FONT, Font.PLAIN, 22);
    
    /** The font of the panel's labels. */
    private static final Font SMALL_LABEL_FONT = new Font(MY_FONT, Font.PLAIN, 15);

    /** The color of the board. */
    private static final Color MY_COLOR = new Color(6, 3, 17);
    
    /** The point's rewarded for each line eliminated. */
    private static final int POINTS_PER_LINE = 1000;
    
    /** Amount of lines to get to level 2. */
    private static final int LEVEL_TWO_LINES = 3;
    
    /** Amount of lines to get to level 3. */
    private static final int LEVEL_THREE_LINES = 8;
    
    /** Amount of lines to get to level 4. */
    private static final int LEVEL_FOUR_LINES = 13;
    
    /** Amount of lines to get to level 5. */
    private static final int LEVEL_FIVE_LINES = 16;
    
    /** Label for amount of lines completed. */
    private static final String LINES_LABEL_MSG =  "      Lines:   ";   
    
    /** Label for player's score. */
    private static final String SCORE_LABEL_MSG =  "      Score:   ";
    
    /** Label for current level. */
    private static final String LEVEL_LABEL_MSG =  "      Level:   ";
    
    /** Label for amount of lines until next level. */
    private static final String NEXT_LEVEL_LABEL_MSG = "Next level in: ";
    
    /** Timer speed at level 2. */
    private static final int LEVEL_TWO_SPEED = 600;
    
    /** Timer speed at level 3. */
    private static final int LEVEL_THREE_SPEED = 400;
    
    /** Timer speed at level 4. */
    private static final int LEVEL_FOUR_SPEED = 325;
    
    /** Timer speed at level 5. */
    private static final int LEVEL_FIVE_SPEED = 200;
    
    /** The amount of lines completed label. */
    private JLabel myLinesLabel;
    
    /** The score label. */
    private JLabel myScoreLabel;
    
    /** The level label. */
    private JLabel myLevelLabel;
    
    /** The next level label. */
    private JLabel myNextLevelLabel;
    
    /** The timer. */
    private final Timer myTimer;
    
    /** Amount of lines cleared. */
    private int myLines;
    
    /** Player's current score. */
    private int myScore;
    
    /** Player's current level. */
    private int myLevel;
    
    /** Adds all components to the score panel. 
     * 
     * @param theBoard is the Tetris game's board
     * @param theTimer is the timer of the game
     */
    public ScorePanel(final Board theBoard, final Timer theTimer) {
        
        super();
        setPreferredSize(PANEL_SIZE);
        
        setLayout(new GridBagLayout());
        
        final Board board = theBoard;
        board.addObserver(this);
        
        myTimer = theTimer;
        
        initialize();
        setupBorder();
        printScore();
    }
    

    /** Initalizes all player values. */
    private void initialize() {
       
        setBackground(MY_COLOR);
        
        myLevel = 1;
        myScore = 0;
        myLines = 0;
        
    }
    
    /** Creates a titled border for the panel. */
    private void setupBorder() {
        final TitledBorder border = BorderFactory.createTitledBorder(null, 
                                          "Score", TitledBorder.CENTER, 
                                          TitledBorder.TOP, LABEL_FONT, 
                                          Color.WHITE);
        
        setBorder(border);
    }
    
    /**
     * This method prints the player's score.
     */
    private void printScore() {
        
        final Box box = Box.createVerticalBox();
        
        final List<JLabel> labelList = new ArrayList<>(); 
        
        myLinesLabel = new JLabel(LINES_LABEL_MSG + myLines);
        myScoreLabel = new JLabel(SCORE_LABEL_MSG + myScore);
        myLevelLabel = new JLabel(LEVEL_LABEL_MSG + myLevel);
        myNextLevelLabel = new JLabel(NEXT_LEVEL_LABEL_MSG + LEVEL_TWO_LINES + " lines");
        
        labelList.add(myLinesLabel);
        labelList.add(myScoreLabel);
        labelList.add(myLevelLabel);
        labelList.add(myNextLevelLabel);
        
        for (JLabel label : labelList) {  
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setFont(SMALL_LABEL_FONT);
            label.setForeground(Color.WHITE);
            box.add(label);
        }
        
        add(box);
    }
    
    
    /** Updates the score panel anytime an internal change has happened in the board. 
     * 
     * @param theObservable is the Board
     * @parm theData is an integer array of the player's score
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theObservable instanceof Board && theData instanceof Integer[]) {
            
            final int clearedLines = ((Integer[]) theData).length;
            
            calculateScore(clearedLines);
            calculateLevel();
            
            repaint();
        }
        
    }
    
    /** Calculates the player's current level and adjusts speeds. */
    private void calculateLevel() {
        
        calculateNextLevel(LEVEL_TWO_LINES);
        
        if (myLines >= LEVEL_TWO_LINES && myLines < LEVEL_THREE_LINES) {
            calculateNextLevel(LEVEL_THREE_LINES);
            
            myLevel = LEVEL_2;
            myLevelLabel.setText(LEVEL_LABEL_MSG + myLevel);
            myTimer.setDelay(LEVEL_TWO_SPEED);
        
        } else if (myLines >= LEVEL_THREE_LINES && myLines < LEVEL_FOUR_LINES) {
            calculateNextLevel(LEVEL_FOUR_LINES);
            
            myLevel = LEVEL_3;
            myLevelLabel.setText(LEVEL_LABEL_MSG + myLevel);
            myTimer.setDelay(LEVEL_THREE_SPEED);
        
        } else if (myLines >= LEVEL_FOUR_LINES && myLines < LEVEL_FIVE_LINES) {
            calculateNextLevel(LEVEL_FIVE_LINES);
            
            myLevel = LEVEL_4;
            myLevelLabel.setText(LEVEL_LABEL_MSG + myLevel);
            myTimer.setDelay(LEVEL_FOUR_SPEED);
        
        } else if (myLines >= LEVEL_FIVE_LINES) {
            myNextLevelLabel.setText("Final level.");
            
            myLevel = LEVEL_5;
            myLevelLabel.setText(LEVEL_LABEL_MSG + myLevel);
            myTimer.setDelay(LEVEL_FIVE_SPEED);
        }
        
    }

    /** Calculates the score from the player's completed lines.
     * 
     * @param theLines is the amount of lines completed.
     */
    private void calculateScore(final int theLines) {
        
        final int clearedLines = theLines;
        
        myLines += clearedLines;
        myLinesLabel.setText(LINES_LABEL_MSG + myLines);
        
        myScore += myLevel * POINTS_PER_LINE;
        
        myScoreLabel.setText(SCORE_LABEL_MSG + myScore);

    }
    
    /** Clears the Tetris game score. */
    public void clearScore() {
       
        myLines = 0;
        myScore = 0;
        myLevel = 1;
        myLinesLabel.setText(LINES_LABEL_MSG + myLines);
        myScoreLabel.setText(SCORE_LABEL_MSG + myScore);
        myLevelLabel.setText(LEVEL_LABEL_MSG + myLevel);
        
    }
    
    /** Calculates how far away the user is from the next level. 
     * 
     * @param theLevelLines is the amount of lines a player has completed.
     */
    private void calculateNextLevel(final int theLevelLines) {
        
        final int result = theLevelLines - myLines;
        
        String resultString = NEXT_LEVEL_LABEL_MSG + result + " line";
        
        if (result > 1) {
            resultString += "s"; //make it plural if more than 1 line
        }
        
        myNextLevelLabel.setText(resultString);
        
    }
    

}
