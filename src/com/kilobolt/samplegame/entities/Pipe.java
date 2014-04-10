package com.kilobolt.samplegame.entities;


import java.util.ArrayList;
import java.util.Random;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
//import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
//import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.samplegame.Assets;

public class Pipe extends AndroidEntity {

    private int x = 800;
    private int y;
    private Image image;
    boolean upsideDown;
    private int speedX = 5;
    private int width;
    private int height;
    public static final int SEPARATION = 601;
    Random rnd;

    // private GameCharacter character;

    public Pipe(boolean upsideDown) {
    	rnd = new Random();
        this.image = Assets.bluePipe;
        this.width = image.getWidth();
        this.height = image.getHeight();
        Bound b = new Bound();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        this.setBound(b);
        b.setRectangles(rectangles);

        this.upsideDown = upsideDown;
//        if (upsideDown) {
//            this.y = -1;
//        }

        ArrayList<Image> list = new ArrayList<Image>();
        list.add(image);
        this.setImages(list);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(0).setY(0).setHeight(height).setWidth(width);
        rectangles.add(rectangle);
        generateY();
        b.setX(x);
    }
    public void generateY(){
    	int randomValue = rnd.nextInt(490);
    	this.y = this.upsideDown ? -randomValue: (590 + randomValue);
        this.getBound().setY(y);
    }
    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
	public void draw(Graphics g) {
		if (this.upsideDown) {
			g.drawRotatedImage(image, x, y, 180);
			g.drawImage(image, x, y + 500 + 590);
		} else {
			g.drawImage(image, x, y);
			g.drawRotatedImage(image, x, y - 500 - 590, 180);
		}
	}
    
    @Override
    public void update(float deltaTime) {
        slide(deltaTime);
        boolean outOfBoundsLeft = this.x + this.width <= 0;
        boolean outOfBoundsRight = this.x >= 800;
        boolean isVisible = !(outOfBoundsLeft || outOfBoundsRight);
        setVisible(isVisible);
        if(outOfBoundsLeft){
            this.setX(this.node.getX() + Pipe.SEPARATION);
            this.setChanged();
            this.notifyObservers();
            generateY();
        }
    }

    private void slide(float deltaTime) {
        this.x -= this.speedX * deltaTime;
        this.getBound().setX(x);
//         if (this.offsetX + this.width <= 0 || this.offsetX >= 800) {
        // out of bounds
        // setVisible(false);
        // }
    }

    public Pipe setX(int x) {
        this.x = x;
        this.getBound().setX(x);
        return this;
    }

	Pipe node;
	public Pipe setPipe(Pipe pipe) {
		this.node = pipe;
        return this;
	}

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
		return this.upsideDown ? 180:0;
	}

	@Override
	public DrawMode getDrawMode() {
		return this.upsideDown ? DrawMode.ROTATE:DrawMode.REGULAR;
	}

    // public void setCharacter(GameCharacter character) {
    // this.character = character;
    // }
}
