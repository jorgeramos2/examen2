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
    
    public Alien(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        speedX = 2;
        speedY = 5;
        bomb = new Bomb(x,y, 20,50, game);
    }

    public int getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public void act(int direction){
        setX(getX() + direction);
    }
    
    public Bomb getBomb(){
        return bomb;
    }
    
    public void setBomb(Bomb bomb){
        this.bomb = bomb;
    }
    
    public String toString(){
        return ""+getX()+","+getY()+","+game.getDirection()+","+getBomb().isDestroyed() + ","+getBomb().getX() + ","+getBomb().getY();
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

    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
