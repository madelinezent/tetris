/**
 * TCSS 305 - Assignment 5 Tetris
 * Deline Zent (Madeline)
 */

package view;

import java.awt.EventQueue;

/**
 * Creates a new Tetris game. 
 * 
 * @author Deline Zent (Madeline)
 * @version 41, 12/13/19
 *
 */
public final class Main {
    
    /** A private constructor which prevents external instantiation. */
    private Main() {
        throw new IllegalStateException();
    }
    
    /** The main() method creates a queue invokes a Tetris game. 
     * 
     * @param theArgs is the String of command-line arguments for the application
     */
    public static void main(final String... theArgs) {
        
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                
                new TetrisFrame();
    
            }
            
        });
    }
    
}
