/*
 * TCSS 305 - Tetris
 * Deline Zent (Madeline)
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import model.Block;
import model.Board;
import model.Point;
import model.TetrisPiece;

/** Creates a JPanel which displays the next Tetris piece.
 * 
 * @author Deline Zent (Madeline)
 * @version 41, 12/13/19
 */
public class NextPiecePanel extends JPanel implements Observer {


    /** A generated serial ID. */
    private static final long serialVersionUID = 8825621975217096512L;
    
    /** The size of the panel. */
    private static final Dimension PANEL_SIZE = new Dimension(210, 101);
    
    /** The font large for the title. */
    private static final Font LABEL_FONT = new Font("Courier", Font.PLAIN, 22);

    /** The size of the blocks in the next piece panel. */
    private static final int BLOCK_SIZE = 20;
    
    /** The length the grid needs to shift in the X-Direction. */
    private static final int SHIFT_X = 65;
    
    /** The length the grid needs to shift in the Y-Direction. */
    private static final int SHIFT_Y = 99;
    
    /** The next Tetris piece to fall. */
    private TetrisPiece myNextPiece;

    /** Public constructor initialies all local variables. */
    public NextPiecePanel() {
        super();
        
        setLayout(new GridBagLayout());
        
        final Color bg = new Color(6, 3, 17);
        setBackground(bg);
        setPreferredSize(PANEL_SIZE);
        
        setupBorder();
    }
    
    /** Sets up a titled border. */
    private void setupBorder() {
        
        final TitledBorder border = BorderFactory.createTitledBorder(null, 
                                               "Next Piece", TitledBorder.CENTER, 
                                               TitledBorder.TOP, LABEL_FONT, 
                                               Color.WHITE);
        setBorder(border);
    }
    

    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        Image image = null;
        if (myNextPiece != null) {
            
            image = getBlockImage(myNextPiece.getBlock());
            
            for (int i = 0; i < myNextPiece.getPoints().length; i++) {
                
                final Point corner = myNextPiece.getPoints()[i];

                g2d.drawImage(image, (corner.x() * BLOCK_SIZE) + SHIFT_X, 
                              getHeight() - (corner.y() * BLOCK_SIZE) - SHIFT_Y, this);
            }
        }
        
        final int height = 101;
        final int width = 160;
        final int wSize = 100;
        final int hSize = 140;
        
        final BasicStroke gridStroke = new BasicStroke(1);
        
        g2d.setStroke(gridStroke);
        g2d.setColor(Color.WHITE);
        
        final int offSet = 25;
        
        // Draw vertical lines
        for (int column = BLOCK_SIZE; column < width; column += BLOCK_SIZE) {
            
            g2d.drawLine(offSet + column, offSet + BLOCK_SIZE, offSet + column, 
                         offSet + wSize);
            
        }
        
        // Draw horizontal lines
        for (int row = BLOCK_SIZE; row < height; row += BLOCK_SIZE) {
            
            g2d.drawLine(offSet + BLOCK_SIZE, offSet + row, offSet +  hSize, offSet + row);
            
        }
        
    }
    

    /** Method updates the panel everytime an inner change happens in the board.
     * 
     * @param theObservable is the board
     * @param theData is the next TetrisPiece
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theObservable instanceof Board && theData instanceof TetrisPiece) {
           // System.out.println("update from next piece if condition" + theData.toString());
            myNextPiece = (TetrisPiece) theData;
        }
        repaint();
    }
    
    /**
     * Returns an image of the next piece to fall. 
     * 
     * @param theNextBlock is the next piece to fall
     * @return the image for the next piece
     */
    private Image getBlockImage(final Block theNextBlock) {
        
        Image blockImage = null;
        
        if (theNextBlock != null) {
            
            if (theNextBlock == Block.J) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniBabyPink.png");
           
            } else if (theNextBlock == Block.S) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniLavendar.png");
            
            } else if (theNextBlock == Block.I) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniSeaGreen.png");

            } else if (theNextBlock == Block.L) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniAqua.png");
            
            } else if (theNextBlock == Block.Z) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniDaffodil.png");
            
            } else if (theNextBlock == Block.O) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniHotPink.png");
            
           
            } else if (theNextBlock == Block.T) {
                blockImage = Toolkit.getDefaultToolkit().
                                getImage("res/images/miniMint.png");
            }
        }     
        return blockImage;
        
    }
}
