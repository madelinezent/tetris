/*
 * TCSS 305 - Tetris
 * Deline Zent (Madeline)
 */

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/** The help panel for the tetris game. 
 * 
 * @author Deline Zent
 * @version 41, 12/13/19
 */
public class HelpPanel extends JPanel {

    /** Serial ID number. */
    private static final long serialVersionUID = 4139927452231552344L;

    /** The size of the control panel. */
    private static final Dimension PANEL_SIZE = new Dimension(210, 80);
    
    /** The color of the panel. */
    private static final Color MY_COLOR = new Color(6, 3, 17);
    
    /** The string font. */
    private static final String MY_FONT = "Courier";
    
    /** The font of the panel's title. */
    private static final Font LABEL_FONT = new Font(MY_FONT, Font.PLAIN, 22);
    
    /** The front of the panel's labels. */
    private static final Font SMALL_LABEL_FONT = new Font(MY_FONT, Font.PLAIN, 16);
    

    /** Public constructor initalizes all local varaibles. */
    public HelpPanel() {
        
        super();
        
        setBackground(MY_COLOR);
        setPreferredSize(PANEL_SIZE);
        setLayout(new GridBagLayout());
        
        
        setupBorder();
        setup();
    }
    
    /**
     * Creates a titled border.
     */
    private void setupBorder() {
        final TitledBorder border = BorderFactory.createTitledBorder(null, 
                                           "Controls", TitledBorder.CENTER, 
                                           TitledBorder.TOP, LABEL_FONT, 
                                           Color.WHITE);
        
        setBorder(border);
    }
    
    /** Creates the JLabels filled with control help. */
    private void setup() {
        
        final Box box = Box.createVerticalBox();
        
        final List<JLabel> labelList = new ArrayList<>(); 
        
        final JLabel left = new   JLabel("  Left:    ←  A"); 
        final JLabel right =  new JLabel(" Right:    →  D");
        final JLabel rotate = new JLabel("Rotate:    ↑  W");
        final JLabel down =   new JLabel("  Down:    ↓  S");
        final JLabel drop =   new JLabel("  Drop:    Space");
        final JLabel pause =  new JLabel(" Pause:      P");
    
        labelList.add(left);
        labelList.add(right);
        labelList.add(rotate);
        labelList.add(down);
        labelList.add(drop);
        labelList.add(pause);
        
        for (JLabel label : labelList) {  
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setFont(SMALL_LABEL_FONT);
            label.setForeground(Color.WHITE);
            box.add(label);
        }
        
        add(box);

    }
}
