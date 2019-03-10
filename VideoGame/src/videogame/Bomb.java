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
public class Bomb extends Item{
    private boolean destroyed;
    private int width;
    private int height;
    private Game game;
    private int speed;
    private Animation animation;
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game 
     */
    public Bomb(int x, int y , int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.destroyed=true;
        this.animation = new Animation(Assets.alienBlast, 100);
        speed = 1;
    }
    /**
     * Get the width of the bomb
     * @return  an <code>int</code> width of bomb
     */
    public int getWidth() {
        return width;
    }
    /**
     * Get the height of the bomb
     * @return  an <code>int</code> height of bomb
     */
    public int getHeight() {
        return height;
    }
    /**
     * Set the width of the bomb
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * Set the height of the bomb
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * Set if the bomb is destroyed
     * @param destroyed 
     */
     public void setDestroyed(boolean destroyed) {
        
            this.destroyed = destroyed;
        }
     /**
      * Get if the bomb is destroyed
      * @return 
      */
    public boolean isDestroyed() {
        
            return destroyed;
        }
    /**
     * Get the perimeter
     * @return  an <code>Rectangle</code> perimeter
     */
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    /**
     * Check collsion with player
     * @param obj
     * @return 
     */
    public boolean intersecta(Object obj){
        return obj instanceof Player && getPerimeter().intersects(((Player) obj).getPerimeter());
    }
    
    /**
     * Method used to update the bomb. Used when laoding a previous game state
     * @param isDestroyed
     * @param posX
     * @param posY 
     */
    public void load(boolean isDestroyed, int posX, int posY){
        setDestroyed(isDestroyed);
        setX(posX);
        setY(posY);
    }

    @Override
    public void tick() {
        if (!isDestroyed()) {
            animation.tick();
            setY(getY() + speed);
            if (getY() == game.getHeight()) {
                setDestroyed(true);
            }
        }
    }
/**
 * Render bomb
 * @param g 
 */
    @Override
    public void render(Graphics g) {
        if(!isDestroyed()){
            g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
}
