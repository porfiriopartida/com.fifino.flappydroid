package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.framework.physics.Mechanics;
import com.fifino.framework.tools.Time;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.samplegame.Assets;

public class GameCharacter extends AndroidEntity {

	private int jumpInitialSpeed = 0;
	private int x = 201;
	private int y = 202;
	private double lastTime = Time.getCurrentTime();
	private int width = 100;
	private int height = 93;

	public GameCharacter() {
		AndroidImage image = ((AndroidImage)Assets.character);
		image.scale(width, height);

		ArrayList<AndroidImage> imagesList = new ArrayList<AndroidImage>();
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

	@Override
	public void update(float deltaTime) {
		fall(deltaTime);
	}

	public void setY(int y) {
		this.y = y;
	}

	public void fall(float deltaTime) {
		double now = Time.getCurrentTime();
		double time = now - lastTime;
		this.y += Mechanics.getSpeed(time, jumpInitialSpeed) * deltaTime;
		this.getBound().setY(y);

		if (this.getY() + getHeight() > 1200) {
			this.setY(1200);
		} else if (this.getY() < 0) {
			this.setY(0);
		}
	}

	public void jump() {
		double now = Time.getCurrentTime();
		double time = now - lastTime;
		if(time<0.5){
			jumpInitialSpeed = -11;
		}
		jumpInitialSpeed = -8;
		lastTime = now;
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

	public void setX(int x) {
		this.x = x;
	}
}
