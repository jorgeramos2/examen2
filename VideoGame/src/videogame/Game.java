/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player;          // to use a player
    private KeyManager keyManager;  // to manage the keyboard
    private LinkedList<Alien> aliens;  // to manage aliens in a Linked List
    private Shot shot;
    private boolean shotVisible;
    private Bomb bomb;
    private boolean bombInAir;
    private int direction;
    private boolean gameOver;
    
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        aliens = new LinkedList<Alien>();
        keyManager = new KeyManager();
        shotVisible = false;
        bombInAir = false;
        direction = 1;
        gameOver=true;
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         player = new Player(getWidth() / 2, getHeight() - 100, 1, 90, 60, this);
         for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                aliens.add(new Alien(150 + 50 * j, 50 + 50 * i,1,40,40,this));
            }
         }
         display.getJframe().addKeyListener(keyManager);
    }
    
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta --;
            }
        }
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    public void deleteLaser(){
        shotVisible = false;
    }
    
    public void shoot(){
        if(!shotVisible){
            shot = new Shot(player.getX() + player.getWidth() / 2 - 5, player.getY(), this);
            shotVisible = true;
        }
    }
   
    private void tick() {
     if(gameOver)
     {
      keyManager.tick();
        // advancing player with colision
        player.tick();
        //if there's a shot.
        
        int alienBombIndex = (int) (Math.random() * aliens.size());
        for(int i = 0; i < aliens.size(); i++){
            Alien alien = aliens.get(i);
            alien.tick();
            if( shotVisible&&shot.intersectAlien(alien))
            {
                aliens.remove(i);
                shotVisible=false;
                if(aliens.size()==0)
                {
                    
                    gameOver=false;
                }
            }
            
            alien.act(direction);
            
            if(i == alienBombIndex && !bombInAir){
                System.out.println("Bombed");
                bombInAir = true;
                bomb = new Bomb(alien.getX(), alien.getY(), 30,30, false, this);
            }
        }
        
        for (Alien alien: aliens) {

            int x = alien.getX();

            if (x >= getWidth() - 30 && direction != -1) {

                direction = -1;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + 15);
                }
            }

            if (x <= 5 && direction != 1) {

                direction = 1;

                Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + 15);
                }
            }
        }
        if(bombInAir){
            bomb.tick();
            if(bomb.isDestroyed()){
                bombInAir = false;
            }
        }
        if(shotVisible){
            shot.tick();
        } 
       if(keyManager.spacebar){
            shoot();
        }   
     }
        
       
    }
    public void gameOver() {

        g.setColor(Color.red);
        Font small = new Font("Helvetica", Font.BOLD, 30);
        g.setFont(small);
        g.drawString("GAME OVER", 250, 300);
    }

    
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
           
            g.drawImage(Assets.background, 0, 0, width, height, null);
             g.setColor(Color.white);
            g.drawLine(0, 290, 358, 290);
            player.render(g);
            for (int i = 0; i < aliens.size(); i++) {
                Alien al = aliens.get(i);
                al.render(g);
            }
            if(shotVisible){
                shot.render(g);
            }
            if(bombInAir){
                bomb.render(g);
            }
            if(gameOver==false)
            {
                gameOver();
            }
            bs.show();
            g.dispose();
        }
       
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }           
        }
    }

    private FontMetrics getFontMetrics(Font small) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    


}
