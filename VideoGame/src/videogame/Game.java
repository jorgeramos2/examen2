/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

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
    private LinkedList<Bomb> bombs;
    private Shot shot;
    private boolean shotVisible;
    private Bomb bomb;
    private boolean bombInAir;
    private int direction;
    private int CHANCE;
    private Vector vec;
    private String arr[];   //Array used to load game
    private boolean gameOver;
    
    
    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        aliens = new LinkedList<Alien>();
        bombs = new LinkedList<Bomb>();
        vec = new Vector();
        keyManager = new KeyManager();
        shotVisible = false;
        bombInAir = false;
        direction = 1;
        shot = new Shot(0, 0, false, this);
        CHANCE = 5;
        gameOver=true;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
                aliens.add(new Alien(150 + 50 * j, 50 + 50 * i, 1, 40, 40, this));
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
                delta--;
            }
        }
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public void deleteLaser() {
        shotVisible = false;
        shot.setVisible(false);
    }

    public void shoot() {
        if (!shotVisible) {
            shot = new Shot(player.getX() + player.getWidth() / 2 - 5, player.getY(), true, this);
            shotVisible = true;
        }
    }

    private void tick() {
        keyManager.tick();
        if(keyManager.pause==false)
        {
           if (gameOver) {
           
            // advancing player with colision
            player.tick();
            //if there's a shot.

            int alienBombIndex = (int) (Math.random() * aliens.size());
            for (int i = 0; i < aliens.size(); i++) {
                Alien alien = aliens.get(i);
                alien.tick();
                if (shotVisible && shot.intersectAlien(alien)) {
                    aliens.remove(i);
                    shotVisible = false;
                    if (aliens.size() == 0) {
                        gameOver = false;
                    }
                }

                alien.act(direction);
            }

            //Controlar el movimiento de los aliens
            for (Alien alien : aliens) {
                int x = alien.getX();
                if (x >= getWidth() - 30 && direction != -1) {
                    direction = -1;
                    alien.setDirection(-1);
                    Iterator i1 = aliens.iterator();
                    while (i1.hasNext()) {
                        Alien a2 = (Alien) i1.next();
                        a2.setY(a2.getY() + 15);
                    }
                }
                if (x <= 5 && direction != 1) {
                    direction = 1;
                    alien.setDirection(1);
                    Iterator i2 = aliens.iterator();
                    while (i2.hasNext()) {
                        Alien a = (Alien) i2.next();
                        a.setY(a.getY() + 15);
                    }
                }
            }

            //Controlar el spawning de las bombas
            Random generator = new Random();
            for (Alien alien : aliens) {
                int num = generator.nextInt(15);
                Bomb b = alien.getBomb();

                if (num == CHANCE && b.isDestroyed()) {
                    b.setDestroyed(false);
                    b.setX(alien.getX());
                    b.setY(alien.getY());
                }

                b.tick();
                if (b.intersecta(player)) {
                    player.die();
                    gameOver = false;
                }

            }

            if (shotVisible) {
                shot.tick();
            }
            if (!player.isDead() && keyManager.spacebar) {
                shoot();
            }

            if (keyManager.save) {
                try {

                    vec.add(player);
                    vec.add(shot);
                    for(Alien alien: aliens){
                        vec.add(alien);
                    }
                    //Graba el vector en el archivo.
                    grabaArchivo();
                } catch (IOException e) {
                    System.out.println("ErroR");
                }
            }
            if (keyManager.load == true) {
                try {
                    //Graba el vector en el archivo.
                    leeArchivo();
                } catch (IOException e) {
                    System.out.println("Error en cargar");
                }
            }
           for(int i=0;i<aliens.size();i++)
           {
               if(aliens.get(i).getY()>500)
               {
                   gameOver=false;
               }
           }

        } 
        }
        
      }
       
    public void gameOver() {

        g.setColor(Color.red);
        Font small = new Font("Helvetica", Font.BOLD, 30);
        g.setFont(small);
        g.drawString("GAME OVER", 250, 300);
        g.drawString("PRESS R TO RESTART GAME", 150,400 );
        if(keyManager.restart==true)
        {
            gameOver=true;
           aliens.clear();
           shotVisible=false;
            Assets.init();
          
            player = new Player(getWidth() / 2, getHeight() - 100, 1, 90, 60, this);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                aliens.add(new Alien(150 + 50 * j, 50 + 50 * i, 1, 40, 40, this));
            }
        }
        
        
        }
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
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            g.setColor(Color.white);
            g.drawLine(0, 500, 595, 500);
            player.render(g);
            for (int i = 0; i < aliens.size(); i++) {
                Alien al = aliens.get(i);
                al.render(g);
            }
            if (shotVisible) {
                shot.render(g);
            }

            if(gameOver==false)
            {
                gameOver();
            }
            for(Alien alien: aliens){
                Bomb b = alien.getBomb();
                b.render(g);
            }
            if(keyManager.pause)
            {
               g.setColor(Color.red);
                Font small = new Font("Helvetica", Font.BOLD, 30);
                g.setFont(small);
                g.drawString("PAUSE", 250, 300); 
            }
            bs.show();
            g.dispose();
        }

    }

    /**
     * It saves the current state of the game on a file
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException {

        System.out.println("Grabando archivo");
        PrintWriter fileOut = new PrintWriter(new FileWriter("src/images/save.txt"));
        for (int i = 0; i < vec.size(); i++) {

            Object x = vec.get(i);

            if (x instanceof Player) {
                x = (Player) vec.get(i);
                fileOut.println(x.toString());
            } else if (x instanceof Shot) {
                x = (Shot) vec.get(i);
                fileOut.println(x.toString());
            } else if (x instanceof Alien){
                x = (Alien) vec.get(i);
                fileOut.println(x.toString());
            }
        }

        vec.clear();
        fileOut.close();
    }

    public void leeArchivo() throws IOException {

        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader("src/images/save.txt"));
        } catch (FileNotFoundException e) {
            File save = new File("src/images/save.txt");
            PrintWriter fileOut = new PrintWriter(save);
            fileOut.println(player.getX());
            fileOut.close();
            fileIn = new BufferedReader(new FileReader("src/images/save.txt"));
        }
        //Primera linea del archivo (Datos del jugador)
        String dato = fileIn.readLine();
        
        int playerXPos = (Integer.parseInt(dato));
        player.load(playerXPos);
        //Segunda linea del archivo (Datos del shot)
        dato = fileIn.readLine();
        arr = dato.split(",");
        int shotXPos = Integer.parseInt(arr[0]);
        int shotYPos = Integer.parseInt(arr[1]);
        boolean shotVisibility = arr[2].equals("true") ? true : false;
        shotVisible = shotVisibility;
        shot.load(shotXPos,shotYPos,shotVisibility);
        //Lineas de los Aliens
        dato = fileIn.readLine();
        aliens.clear();
        while (dato != null) {
            arr = dato.split(",");
            int alienX = Integer.parseInt(arr[0]);
            int alienY = Integer.parseInt(arr[1]);
            int alienDirection = Integer.parseInt(arr[2]);
            direction = alienDirection;
            boolean bombDestroyed = Boolean.parseBoolean(arr[3]);
            int bombX = Integer.parseInt(arr[4]);
            int bombY = Integer.parseInt(arr[5]);
            Alien alien = new Alien(alienX, alienY, alienDirection, 40,40, this);
            alien.loadBomb(bombDestroyed, bombX, bombY);
            aliens.add(alien);
            dato = fileIn.readLine();
        }
        fileIn.close();
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

}
