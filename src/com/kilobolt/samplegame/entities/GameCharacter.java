package com.kilobolt.samplegame.entities;


import java.util.ArrayList;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.framework.physics.Mechanics;
import com.fifino.framework.tools.Time;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.samplegame.Assets;

public class GameCharacter extends AndroidEntity {

    private Image image;
    private int speedY = 0;
    private int characterX = 201;
    private int characterY = 202;
    private double lastTime = Time.getCurrentTime();

    public GameCharacter() {
        this.image = Assets.character;

        ArrayList<Image> imagesList = new ArrayList<Image>();
        imagesList.add(image);
        this.setImages(imagesList);

        Bound b = new Bound();
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0).setY(0).setHeight(46).setWidth(42);

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);

        b.setRectangles(rectangles).setX(characterX).setY(characterY);

        this.setBound(b);
        setVisible(true);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, characterX, characterY, 100, 100);
//        g.drawScaledImage(this.image, characterX, characterY, this.image.getWidth(), this.image.getHeight(), characterX, characterY, 200, 200);

        if (AndroidEntity.DEBUG_MODE) {
            getBound().draw(g);
        }
    }

    @Override
    public void update(float deltaTime) {
        fall(deltaTime);
    }

    public void setCharacterY(int y) {
        this.characterY = y;
    }

    public void fall(float deltaTime) {
        double now = Time.getCurrentTime();
        double time = now - lastTime;
        this.characterY += Mechanics.getSpeed(time, speedY) * deltaTime;
        this.getBound().setY(characterY);
        
        if (this.getBound().getY() > 1281) {
            this.setCharacterY(0);
        }
    }

    public void jump() {
    	speedY = -20;
        lastTime = Time.getCurrentTime();
    }
}
