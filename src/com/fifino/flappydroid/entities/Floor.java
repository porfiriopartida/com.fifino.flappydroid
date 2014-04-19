package com.fifino.flappydroid.entities;

import java.util.ArrayList;

import com.fifino.flappydroid.Assets;
import com.fifino.flappydroid.GameScreen;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.implementation.AndroidImage;

public class Floor extends AndroidEntity {
	private int x = 0;
	private int y;
	private int width = 100;
	public static int HEIGHT = 180;
//	Bitmap[] bitmapArray;
	AndroidImage image;
	private int pieces = 0;
	public Floor() {
		this.pieces = GameScreen.WIDTH/width + 1;
//		bitmapArray = new Bitmap[pieces];
		image = (AndroidImage)Assets.tileDirt;
		image.scale(width, Floor.HEIGHT);
//		ArrayList<AndroidImage> list = new ArrayList<AndroidImage>();
//		list.add(image);
//		this.setImages(list);
		y = GameScreen.HEIGHT - Floor.HEIGHT;
		Bound b = new Bound();
		Rectangle rectangleA = new Rectangle();
		rectangleA.setX(0).setY(0).setHeight(Floor.HEIGHT).setWidth(GameScreen.WIDTH)
				.setParentX(x).setParentY(y);

		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		rectangles.add(rectangleA);

		b.setRectangles(rectangles).setX(x).setY(y);
		this.setBound(b);
		setVisible(true);
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	public int getHeight() {
		return Floor.HEIGHT;
	}

	@Override
	public void update(float deltaTime) {
		// slide(deltaTime);
		x -= Pipe.speedX * deltaTime;
		if(x<=-width){
			x = 0;
		}
	}
	@Override
	public void draw(Graphics g){
		for(int i = 0; i<this.pieces; i++){
			g.drawImage(this.image, x + i*width, y);
		}
		super.drawBounds(g);
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
	public int getAngle() {
		return 0;
	}
}
