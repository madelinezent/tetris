/* 
 * TCSS 305 - Tetris
 * Deline Zent (Madeline)
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Block;
import model.Board;
import model.Point;

/**
 * The GUI panel for main grid of the Tetris game.
 * 
 * @author Deline Zent (Madeline)
 * @version 41, 12/13/19
 */
public class TetrisPanel extends JPanel implements Observer {

    /** A generated serial ID. */
    private static final long serialVersionUID = 6940400905791940469L;

    /** A string to refer to the black tetris piece. */
    private static final String MY_BLACK_PIECE = "res/images/black.png";

    /** The dimensions of the drawing for the Tetris board pieces. */
    private static final Dimension BOARD_SIZE = new Dimension(301, 601);

    /** The size of every Tetris block. */
    private static final int BLOCK_SIZE = 30;

    /** A list of blocks to draw. */
    private final List<Block[]> myList;

    /** Public constructor initializes all local variables. */
    public TetrisPanel() {
        
        super();
        
        myList = new ArrayList<Block[]>();
        
        final Color bg = new Color(0, 0, 0);
        setBackground(bg);
        setPreferredSize(BOARD_SIZE);
    }
    
    /** This method draws the grid and pieces for the Tetris game. 
     * 
     * @param theG is the Graphics component.
     */
    @Override
    protected void paintComponent(final Graphics theG) {
        
        super.paintComponent(theG);

        Image image = Toolkit.getDefaultToolkit().getImage(MY_BLACK_PIECE);

        for (int i = 0; i < myList.size(); i++) {

            final Block[] colorArray = myList.get(i);

            for (int j = 0; j < colorArray.length; j++) {

                final Point point = new Point(j * BLOCK_SIZE, i * BLOCK_SIZE);
                final Block color = colorArray[j];

                image = getBlockImage(color);

                if (colorArray[j] != null) {
                    theG.fillRect(point.x(), getHeight() - point.y() - BLOCK_SIZE, BLOCK_SIZE,
                               BLOCK_SIZE);
                }
                
                theG.drawImage(image, point.x(), getHeight() - point.y() - BLOCK_SIZE, this);

            }
        }

        final int height = getHeight();
        final int width = getWidth();

        final Color gridColor = new Color(255, 255, 255);

        theG.setColor(gridColor);

        // Draw vertical lines
        for (int column = 0; column < width; column += BLOCK_SIZE) {
            theG.drawLine(column, 0, column, height);
        }

        // Draw horizontal lines
        for (int row = 0; row < height; row += BLOCK_SIZE) {
            theG.drawLine(0, row, width, row);
        }

        // }
        repaint();
    }                
 
    /**
     * Returns an image for each type of tetris piece.
     * 
     * @param theBlock is the Tetris Block
     * @return an image of that Tetris piece
     */
    private Image getBlockImage(final Block theBlock) {
        
        Image image = Toolkit.getDefaultToolkit().getImage(MY_BLACK_PIECE);
         
        if (theBlock != null) {
            // J
            if (theBlock == Block.J) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/babyPink.png");

            // The I-piece
            } else if (theBlock == Block.I) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/seaGreen.png");
            
            // The T-piece
            } else if (theBlock == Block.T) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/mint.png");
            
            // The L-Piece
            } else if (theBlock == Block.L) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/aqua.png");
            
            // The O-Piece
            } else if (theBlock == Block.O) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/hotPink.png");
            
            // The S-Piece
            } else if (theBlock == Block.S) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/lavendar.png");
            
            // The Z-Piece
            } else if (theBlock == Block.Z) {
                image = Toolkit.getDefaultToolkit().getImage("res/images/daffodil.png");
            }
        }     
        return image;
    }

    /** Updates the Tetris Panel everytime the Board class changes. 
     * 
     * @param theObservable is the object we are monitoring change from
     * @param theData is the 2D array passed from the Board class
     */
    public void update(final Observable theObservable, final Object theData) {

        if (theObservable instanceof Board && theData instanceof Block[][]) {
            
            final Block[][] a = (Block[][]) theData;
            final int size = a.length;
            myList.clear();
            
            for (int i = 0; i < size; i++) {
                final Block[] boardArray = a[i];
                myList.add(boardArray);
            }
                  
        } 
        repaint(); 
    }
     
    
}