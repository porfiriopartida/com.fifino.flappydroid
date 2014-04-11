package com.kilobolt.samplegame.entities;


import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;

import com.fifino.framework.BitmapTransform;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.samplegame.Assets;
import com.kilobolt.samplegame.GameScreen;

public class Pipe extends AndroidEntity {

    private int x = 800;
    private int y;
    private Bitmap bitmapA, bitmapB;
    private int speedX = 6;
    private int width = 170;
    private int height;
    private int gap = 400;
    private int minVisible = 200;
    public static final int SEPARATION = 651;
    private Random rnd;
    private Coin coin;
    
	public Pipe() {
    	this.height = GameScreen.HEIGHT * 680 / 1280;
    	rnd = new Random();
    	AndroidImage image = ((AndroidImage) Assets.bluePipe);
        this.bitmapB = BitmapTransform.scale(image.getBitmap(), width, height);
        this.bitmapA = BitmapTransform.rotate(bitmapB, 180);
        Bound b = new Bound();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        this.setBound(b);
        b.setRectangles(rectangles);

        Rectangle rectangleA = new Rectangle();
        Rectangle rectangleB = new Rectangle();
        rectangleA.setX(0).setY(0).setHeight(height).setWidth(width);
        rectangleB.setX(0).setY(height + gap).setHeight(height).setWidth(width);
        rectangles.add(rectangleA);
        rectangles.add(rectangleB);
        generateY();
        b.setX(x);
    }
    public void generateY(){
    	this.y = -rnd.nextInt(height - minVisible);
        this.getBound().setY(y);
    }
    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
//        g.drawBitmap(bitmapA, x, y);
//        g.drawBitmap(bitmapB, x, y + height + gap);
//        g.drawScaledImage(image, getX(),  getY(), width, height, 0, 100 * frame, width, height);
        g.drawScaledBitmap(bitmapA, x, 5, width, height + y, 0, -y, width, height + y);
        g.drawBitmap(bitmapB, x, y + height + gap);
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
            generateY();
    		coin.setVisible(true);
        }
        coin.setX(this.x);
        coin.setY(this.y + height + 150);
    }

    private void slide(float deltaTime) {
        this.x -= this.speedX * deltaTime;
        this.getBound().setX(x);
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

    public Coin getCoin() {
		return coin;
	}
	public void setCoin(Coin coin) {
		this.coin = coin;
	}
}
