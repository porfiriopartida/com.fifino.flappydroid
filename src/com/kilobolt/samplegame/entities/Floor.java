package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.samplegame.Assets;

public class Floor extends AndroidEntity {
	private int x = 0;
	private int y = 1200;
	private int width = 1200;
	private int height = 80;

	public Floor() {
		AndroidImage image = (AndroidImage)Assets.tileDirt;
		image.scale(width, height);
		ArrayList<AndroidImage> list = new ArrayList<AndroidImage>();
		list.add(image);
		this.setImages(list);
		Bound b = new Bound();
		Rectangle rectangleA = new Rectangle();
		rectangleA.setX(0).setY(0).setHeight(height).setWidth(width)
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
}
