/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 *
 * @author Jorge
 */
public class Alien extends Item {
    private int direction;
    private int width;
    private int height;
    private int speedX;
    private int speedY;
    private Game game;
    private Bomb bomb;
    private Animation explosionAnimation;
    private boolean dead;
    private int deadCounter;
    private boolean dying;
   /**
    * 
    * @param x position
    * @param y position
    * @param direction
    * @param width of alien 
    * @param height of alien
    * @param game 
    */ 
    public Alien(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        speedX = 2;
        speedY = 5;
        bomb = new Bomb(x,y, 20,50, game);
        explosionAnimation = new Animation(Assets.explosion, 100);
        dead = false;
        dying = false;
    }
    /**
     * Get the direction
     * @return an <code>int</code>direction
     */
    public int getDirection() {
        return direction;
    }
    /**
     * Get the width
     * @return an <code>int</code> width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Get the height
     * @return an <code>int</code>height
     */
    public int getHeight() {
        return height;
    }
    /**
     * set direction
     * @param direction an <code>int</code> direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
    /**
     * Set width
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * Set the height
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * Get the perimeter
     * @return an <code>Rectangle</code> with perimeter
     */
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    /**
     * Set direction
     * @param direction 
     */
    public void act(int direction){
        setX(getX() + direction);
    }
    /**
     * Get bomb
     * @return an <code>Bomb</code>
     */
    public Bomb getBomb(){
        return bomb;
    }
    /**
     * Set bomb
     * @param bomb 
     */
    public void setBomb(Bomb bomb){
        this.bomb = bomb;
    }
    /**
     * Convert values to string
     * @return an <code>string</code>
     */
    public String toString(){
        return ""+getX()+","+getY()+","+game.getDirection()+","+getBomb().isDestroyed() + ","+getBomb().getX() + ","+getBomb().getY();
    }
    /**
     * Get if alien is dead
     * @return an <code>boolean</code>
     */
    public boolean isDead() {
        return dead;
    }
    /**
     * Set if alien is dead
     * @param dead 
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    /**
     * Get deadcounter
     * @return an <code>int</code>
     */
    public int getDeadCounter() {
        return deadCounter;
    }
    /**
     * Set deadCounter
     * @param deadCounter 
     */
    public void setDeadCounter(int deadCounter) {
        this.deadCounter = deadCounter;
    }
    /**
     * Get if alien is dying
     * @return an <code>boolean</code>
     */
    public boolean isDying() {
        return dying;
    }
    /**
     * Set dying
     * @param dying 
     */
    public void setDying(boolean dying) {
        this.dying = dying;
    }
    
    /**
     * Method used to update the Alien's bomb. Used when loading a previous game state.
     * @param bombDestroyed
     * @param bombX
     * @param bombY 
     */
    
    public void loadBomb(boolean bombDestroyed, int bombX, int bombY){
        Bomb b = getBomb();
        b.load(bombDestroyed, bombX, bombY);
    }
    
    public void tick() {
        if(isDying()){
            if(deadCounter != 0){
                deadCounter--;
            } else {
                setDead(true);
                setDying(false);
            }
        }
    }
    /**
     * Render alien
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        if(isDying()){
            g.drawImage(explosionAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        } else {
            g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
}
