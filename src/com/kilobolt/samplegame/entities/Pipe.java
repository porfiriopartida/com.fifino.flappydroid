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
import com.kilobolt.samplegame.GameScreen;

public class Pipe extends AndroidEntity {

    private int x = 800;
    private int y;
    private Image image;
    private int speedX = 7;
    private int width = 170;
    private int height = 700;
    public static final int SEPARATION = 601;
    Random rnd;

    // private GameCharacter character;

    public Pipe() {
    	rnd = new Random();
        this.image = Assets.bluePipe;
        Bound b = new Bound();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        this.setBound(b);
        b.setRectangles(rectangles);

        ArrayList<Image> list = new ArrayList<Image>();
        list.add(image);
        this.setImages(list);

        Rectangle rectangleA = new Rectangle();
        Rectangle rectangleB = new Rectangle();
        rectangleA.setX(0).setY(0).setHeight(height).setWidth(width);
        rectangleB.setX(0).setY(-400 - height).setHeight(height).setWidth(width);
        rectangles.add(rectangleA);
        rectangles.add(rectangleB);
        generateY();
        b.setX(x);
    }
    public void generateY(){
    	this.y = rnd.nextInt(GameScreen.HEIGHT - 200);
        this.getBound().setY(y);
    }
    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        g.drawScaledRotatedImage(image, x, y -400  , width, height, 180);
        g.drawScaledImage(image, x, y, width, height);
        super.drawBounds(g);
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
		return height * 2 + 400;
	}

	@Override
	public int getAngle() {
		return 0;
	}

	@Override
	public DrawMode getDrawMode() {
		return DrawMode.REGULAR;
	}

    // public void setCharacter(GameCharacter character) {
    // this.character = character;
    // }
}
