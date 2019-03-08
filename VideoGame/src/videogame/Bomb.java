/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;
import java.awt.Graphics;
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
    public Bomb(int x, int y , int width, int height,boolean destroyed, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.destroyed=false;
        speed = 5;
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

    @Override
    public void tick() {
        setY(getY() + speed);
        if(getY() == game.getHeight()){
            setDestroyed(true);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
