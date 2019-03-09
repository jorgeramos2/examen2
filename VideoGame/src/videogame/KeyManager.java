/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author antoniomejorado
 */
public class KeyManager implements KeyListener {
    
  
    public boolean left;    // flag to move left the player
    public boolean right;   // flag to move right the player
    public boolean spacebar; // flag to shoot 
    public boolean pause;   // flag to pause game;
    public boolean restart; // flag to restart game;
    public boolean save;    // flag to save game;
    public boolean load;  
    private boolean keys[];  // to store all the flags for every key
    
    public KeyManager() {
        keys = new boolean[256];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
       
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        spacebar=keys[KeyEvent.VK_SPACE];
        pause=keys[KeyEvent.VK_P];
        restart=keys[KeyEvent.VK_R];
        save=keys[KeyEvent.VK_G];
        load=keys[KeyEvent.VK_C];
    }
}
