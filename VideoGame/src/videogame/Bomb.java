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
    public Bomb(int x, int y , int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.destroyed=true;
        speed = 1;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
     public void setDestroyed(boolean destroyed) {
        
            this.destroyed = destroyed;
        }

    public boolean isDestroyed() {
        
            return destroyed;
        }
    
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
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
            setY(getY() + speed);
            if (getY() == game.getHeight()) {
                setDestroyed(true);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if(!isDestroyed()){
            g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
}
