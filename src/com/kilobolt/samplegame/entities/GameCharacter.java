package com.kilobolt.samplegame.entities;


import java.util.ArrayList;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.framework.physics.Mechanics;
import com.fifino.framework.tools.Time;
//import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.samplegame.Assets;

public class GameCharacter extends AndroidEntity {

    private Image image;
    private int speedY = 0;
    private int x = 201;
    private int y = 202;
    private double lastTime = Time.getCurrentTime();
    private int width = 100;
    private int height = 100;
    public GameCharacter() {
        this.image = Assets.character;

        ArrayList<Image> imagesList = new ArrayList<Image>();
        imagesList.add(image);
        this.setImages(imagesList);

        Bound b = new Bound();
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0).setY(0).setHeight(height).setWidth(width);

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);

        b.setRectangles(rectangles).setX(x).setY(y);

        this.setBound(b);
        setVisible(true);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

//    @Override
//    public void draw(Graphics g) {
//        g.drawImage(this.image, x, y, width, height);
//		super.draw(g);
//    }

    @Override
    public void update(float deltaTime) {
        fall(deltaTime);
    }

    public void setCharacterY(int y) {
        this.y = y;
    }

    public void fall(float deltaTime) {
        double now = Time.getCurrentTime();
        double time = now - lastTime;
        this.y += Mechanics.getSpeed(time, speedY) * deltaTime;
        this.getBound().setY(y);
        
        if (this.getY() + getHeight() > 1200) {
            this.setCharacterY(1200);
        } else if(this.getY() < 0){
            this.setCharacterY(0);
        }
    }

    public void jump() {
    	speedY = -20;
        lastTime = Time.getCurrentTime();
    }

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getAngle() {
		return 0;
	}

	@Override
	public DrawMode getDrawMode() {
		return DrawMode.SCALE;
	}
}
