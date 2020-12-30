/*
 * TCSS 305 - Tetris
 * Deline Zent (Madeline)
 */

package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.Board;

/** Implements all key controls for the Tetris game. 
 * 
 * @author Deline Zent
 * @version 41, 12/13/19
 * 
 */
public class TetrisKeyControls implements KeyListener {
    
    /** An int for the left key. */
    private static final int MY_LEFT_1 = KeyEvent.VK_A;
    
    /** An int for the numperpad left key. */
    private static final int MY_LEFT_2 = KeyEvent.VK_KP_LEFT;

    /** An int for the left key. */
    private static final int MY_LEFT_3 = KeyEvent.VK_LEFT;
    
    /** An int for the right key. */
    private static final int MY_RIGHT_1 = KeyEvent.VK_D;
    
    /** An int for the numberpad right key. */
    private static final int MY_RIGHT_2 = KeyEvent.VK_KP_RIGHT;

    /** An int for the right key. */
    private static final int MY_RIGHT_3 = KeyEvent.VK_RIGHT;
    
    /** An int for the W key. */
    private static final int MY_ROTATE_1 = KeyEvent.VK_W;
    
    /** An int for the keypad up key. */
    private static final int MY_ROTATE_2 = KeyEvent.VK_KP_UP;

    /** An int for the P key. */
    private static final int MY_ROTATE_3 = KeyEvent.VK_UP;
    
    /** An int for the S key. */
    private static final int MY_DOWN_1 = KeyEvent.VK_S;
    
    /** An int for the numberpad down key. */
    private static final int MY_DOWN_2 = KeyEvent.VK_KP_DOWN;

    /** An int for the down key. */
    private static final int MY_DOWN_3 = KeyEvent.VK_DOWN;
    
    /** An int for the space key. */
    private static final int MY_DROP = KeyEvent.VK_SPACE;
    
    /** An int for the P key. */
    private static final int MY_PAUSE = KeyEvent.VK_P;

    /** A Board for the class to interact with. */
    private final Board myBoard;
   
    /** The boolean variable of if the pieces are movable or not. */
    private Boolean myPiecesMovable;
    

    /** Public constructor initializes local variables.
     * 
     * @param theBoard is the current tetris game board.
     */
    public TetrisKeyControls(final Board theBoard) {
        
        super();
        
        myBoard = theBoard;
        myPiecesMovable = true;
    }
    

    /** This method allows outside classes to pause the game.
     * 
     * @param theState is if the piece is movable.
     */
    public void pauseGame(final Boolean theState) {
        myPiecesMovable = theState;
    }
    
    
    /**
     * Plays audio clip for the game.
     * 
     * @param theFileName Audio file to be played.
     */
    public void play(final String theFileName) {
        
        try {
            final Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(theFileName)));
            clip.start();
        } catch (final LineUnavailableException | IOException 
                        | UnsupportedAudioFileException e) {
            System.out.println("File not found");
        }

        
    }


    /** Method implements actions for left key press. 
     * 
     * @param theSound is the music to play for the press.
     */
    public void left(final String theSound) {
        myBoard.left();
        play(theSound);
    }

    /** Method implements actions for right key press. 
     * 
     * @param theSound is the music to play for the press.
     */
    public void right(final String theSound) {
        myBoard.right();
        play(theSound);
    }
    
    /** Method implements actions for down key press. 
     * 
     * @param theSound is the music to play for the press.
     */
    public void down(final String theSound) {
        myBoard.down();
        
    }
    
    /** Method implements actions for rotate key press. 
     * 
     * @param theSound is the music to play for the press.
     */
    public void rotate(final String theSound) {
        myBoard.rotateCW();
        
    }
    
    /** Method implements actions for drop key press. 
     * 
     * @param theSound is the music to play for the press.
     */
    public void drop(final String theSound) {
        myBoard.drop();
    }
    
    /** Method implements actions for a pause key press. */
    public void pause() {
        
        if (myPiecesMovable) {
            
            myPiecesMovable = false;
            
        } else {
            
            myPiecesMovable = true;
        }
    }
    

    /**
     * Creates an action for each key pressed.
     * 
     * @param theEvent is the key pressed.
     */
    @Override
    public void keyPressed(final KeyEvent theEvent) {

        if (theEvent.getKeyCode() == MY_PAUSE) {
            pause();
        } else {
            if (myPiecesMovable) {
                pieceControls(theEvent);
            }
        }
    }
    
    
    /** Directs tetris-piece keys to their actions. 
     * 
     * @param theEvent is the key we are checking.
     */
    public void pieceControls(final KeyEvent theEvent) {
        
        final String sound1 = "res/sounds/rec1.wav";

        final String sound2 = "res/sounds/rec2.wav";
        
        final int event = theEvent.getKeyCode();

        
        switch (event) {
            
            case MY_LEFT_1:
            case MY_LEFT_2:
            case MY_LEFT_3:
                left(sound1);
                break;

            case MY_RIGHT_1:
            case MY_RIGHT_2:
            case MY_RIGHT_3:
                right(sound1);
                break;

            case MY_ROTATE_1:
            case MY_ROTATE_2:
            case MY_ROTATE_3:
                rotate(sound1);
                break;

            case MY_DOWN_1:
            case MY_DOWN_2:
            case MY_DOWN_3:
                down(sound2);
                break;

            case MY_DROP:
                drop(sound2);
                break;
            
            default:
                break;
                
        }
    }
    
    /**
     * Unimplemented method for when a key is typed.
     * 
     * @param theEvent is the key typed.
     */
    @Override
    public void keyTyped(final KeyEvent theEvent) {
    
    }


    /**
     * Unimplemented method for when a key is released.
     * 
     * @param theEvent is the key released.
     */
    @Override
    public void keyReleased(final KeyEvent theEvent) {
     
    }
}
