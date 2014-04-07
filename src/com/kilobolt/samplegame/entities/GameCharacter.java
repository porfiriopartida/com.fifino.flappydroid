package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public class GameCharacter extends AndroidEntity{
	private Image image;
	private int speedX = 10;
	private int characterX = 0;
	private int characterY = 202;
	private boolean isMoving;
	private int destinationX;

	public GameCharacter(Image image) {
		this.image = image;
		
		ArrayList<Image> imagesList = new ArrayList<Image>();
		imagesList.add(image);
		this.setImages(imagesList);
		
		Bound b = new Bound();
		Rectangle rectangle = new Rectangle();
		rectangle.setX(0).setY(45).setHeight(2).setWidth(47);
		
		ArrayList<Rectangle> rectangles  = new ArrayList<Rectangle>();
		rectangles.add(rectangle);
		
		b.setRectangles(rectangles).setX(characterX).setY(characterY);
		
		this.setBound(b);
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(this.image, characterX, characterY);
		
		if(AndroidEntity.DEBUG_MODE){
			getBound().draw(g);
		}
	}

    private int getStep(){
    	int step = 0;
    	if(this.destinationX < characterX){
    		//left
    		step = -speedX; 
    	}else{
    		//right
    		step = speedX;
    	}
    	
    	return step;
    }

	public boolean isMoving() {
		return isMoving;
	}
	
	public void update(){
        characterX += isMoving ? getStep():0;
		this.getBound().setX(characterX).setY(characterY);
	}

	public void setMoving(boolean b) {
		this.isMoving = b;
	}
	public void setDestinationX(int x){
		this.destinationX = x;
	}
	public void setCharacterY(int y){
		this.characterY = y;
	}

	public void fall() {
		this.characterY+=10;
	}
}
