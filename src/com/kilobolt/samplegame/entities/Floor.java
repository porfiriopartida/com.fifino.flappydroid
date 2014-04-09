package com.kilobolt.samplegame.entities;

import java.util.ArrayList;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
//import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.samplegame.Assets;

public class Floor extends AndroidEntity {
//	private Image image;
	private int x = 0;
	private int y = 1200;
	// private int tileWidth = 40;
	// private int tileHeight = 40;
	// private int speedX = 10;
	private int width = 1200;
	private int height = 80;

	public Floor() {
		Image image = Assets.tileDirt;
		ArrayList<Image> list = new ArrayList<Image>();
		list.add(image);
		this.setImages(list);
		Bound b = new Bound();
		Rectangle rectangleA = new Rectangle();
		rectangleA.setX(0).setY(0).setHeight(height).setWidth(width)
				.setParentX(x).setParentY(y);
		// Rectangle rectangleB = new Rectangle();
		// rectangleB.setX(280).setY(0).setHeight(tileHeight)
		// .setWidth(10 * tileWidth).setParentX(offsetX)
		// .setParentY(offsetY);

		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		rectangles.add(rectangleA);
		// rectangles.add(rectangleB);

		b.setRectangles(rectangles).setX(x).setY(y);
		// this.width = 5*tileWidth + 80 + 10 * tileWidth;
		this.setBound(b);
		setVisible(true);
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

//	@Override
//	public void draw(Graphics g) {
//		super.draw(g);
//		g.drawImage(this.image, x, y, width, height);
//	}

	public int getHeight() {
		return height;
	}

	@Override
	public void update(float deltaTime) {
		// slide(deltaTime);
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

	@Override
	public DrawMode getDrawMode() {
		return DrawMode.SCALE;
	}

	// private void slide(float deltaTime) {
	// // this.offsetX -= this.speedX * deltaTime;
	// // if(this.offsetX + this.width <= 0){
	// // this.offsetX = 800;
	// // }
	// // this.getBound().setX(offsetX);
	// }
}
