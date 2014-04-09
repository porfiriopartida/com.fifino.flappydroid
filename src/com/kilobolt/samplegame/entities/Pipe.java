package com.kilobolt.samplegame.entities;


import java.util.ArrayList;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.samplegame.Assets;

public class Pipe extends AndroidEntity {

    private int offsetX = 800;
    private int offsetY = 590;
    AndroidImage image;
    boolean upsideDown;
    private int speedX = 10;
    private int width;
    private int height;
    public static final int SEPARATION = 601;

    // private GameCharacter character;

    public Pipe(boolean upsideDown) {
        this.image = (AndroidImage) Assets.bluePipe;
        this.width = image.getWidth();
        this.height = image.getHeight();

        this.upsideDown = upsideDown;
        if (upsideDown) {
            this.offsetY = -1;
        }

        ArrayList<Image> list = new ArrayList<Image>();
        list.add(image);
        this.setImages(list);

        Bound b = new Bound();
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0).setY(0).setHeight(height).setWidth(width)
                .setParentX(offsetX).setParentY(offsetY);

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);
        b.setRectangles(rectangles).setX(offsetX).setY(offsetY);

        this.setBound(b);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            if (this.upsideDown) {
                g.drawImage(image, offsetX, offsetY, 180);
            } else {
                g.drawImage(image, offsetX, offsetY);
            }
        }
    }
    @Override
    public void update(float deltaTime) {
        slide(deltaTime);
        boolean outOfBoundsLeft = this.offsetX + this.width <= 0;
        boolean outOfBoundsRight = this.offsetX >= 800;
        boolean isVisible = !(outOfBoundsLeft || outOfBoundsRight);
        setVisible(isVisible);
        if(outOfBoundsLeft){
            this.setX(this.node.getX() + Pipe.SEPARATION);
            this.setChanged();
            this.notifyObservers();
        }
    }

    private void slide(float deltaTime) {
        this.offsetX -= this.speedX * deltaTime;
        this.getBound().setX(offsetX);
//         if (this.offsetX + this.width <= 0 || this.offsetX >= 800) {
        // out of bounds
        // setVisible(false);
        // }
    }

    public Pipe setX(int x) {
        this.offsetX = x;
        this.getBound().setX(offsetX);
        return this;
    }

	public int getX() {
		return offsetX;
	}
	Pipe node;
	public Pipe setPipe(Pipe pipe) {
		this.node = pipe;
        return this;
	}

    // public void setCharacter(GameCharacter character) {
    // this.character = character;
    // }
}
