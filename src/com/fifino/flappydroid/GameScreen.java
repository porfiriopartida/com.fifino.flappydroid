package com.fifino.flappydroid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

//import java.util.Vector;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Environment;

import com.fifino.flappydroid.entities.Coin;
import com.fifino.flappydroid.entities.Floor;
import com.fifino.flappydroid.entities.GameCharacter;
import com.fifino.flappydroid.entities.MenuItem;
import com.fifino.flappydroid.entities.Pipe;
import com.fifino.framework.BitmapTransform;
import com.fifino.framework.Entity;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.entities.Rectangle.CollisionSpot;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;

public class GameScreen extends Screen implements Observer {
	enum GameState {
		Ready, Running, Paused, GameOver
	};

	public static int HEIGHT = 1280;
	public static int WIDTH = 800;

	// GameState state = GameState.Ready;
	GameState state = GameState.Running;
	// Variable Setup
	// You would create game objects here.
	Random rnd;
	int livesLeft = 1;
	int score = 0;
	Paint paint;
	ArrayList<Entity> entities;
	GameCharacter character;
	private Floor floor;
	AndroidImage skyImage;
	int skySpeed = 1;
	int skyX = 0;
	int skyY = 0;
	AndroidImage mountainsImage;
	int mountainsSpeed = 3;
	int mountainsX = 0;
	int mountainsY;
	int mountainsHeight = 400;
	private MenuItem debugButton;

	public GameScreen(Game game) {
		super(game);
		GameScreen.HEIGHT = game.getGraphics().getHeight();
		GameScreen.WIDTH = game.getGraphics().getHeight();

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		rnd = new Random();

		// Setup entities
		entities = new ArrayList<Entity>();
		initializeAssets();
		setupEntities();
	}

	protected void initializeAssets() {
		if (Assets.background == null) {
			Graphics graphics = game.getGraphics();
			Assets.background = graphics.newImage("bg-vertical.png",
					ImageFormat.RGB565);
			skyImage = (AndroidImage) Assets.background;
			skyImage.setBitmap(BitmapTransform.scale(skyImage.getBitmap(),
					GameScreen.WIDTH, GameScreen.HEIGHT));
			Assets.mountains = graphics.newImage("mountains.png",
					ImageFormat.RGB565);
			mountainsImage = (AndroidImage) Assets.mountains;
			mountainsImage.setBitmap(BitmapTransform.scale(
					mountainsImage.getBitmap(), GameScreen.WIDTH,
					mountainsHeight));

			Assets.bluePipe = graphics.newImage("blue-pipe.png",
					ImageFormat.RGB565);

			Assets.tileDirt = graphics.newImage("tile-dirt.png",
					ImageFormat.RGB565);

			Assets.character = graphics.newImage("character.png",
					ImageFormat.RGB565);

			Assets.gameOver = graphics.newImage("game-over.png",
					ImageFormat.RGB565);

			Assets.coin = graphics.newImage("coin.png", ImageFormat.RGB565);
			Assets.debugButton = graphics.newImage("debug.png",
					ImageFormat.RGB565);

			Assets.jumpSound = game.getAudio().createSound("jump.wav");
			Assets.hitSound = game.getAudio().createSound("hit.wav");
			Assets.coinSound = game.getAudio().createSound("coin.wav");
			Assets.tenCoinsSound = game.getAudio().createSound("10-coins.wav");
		}
		AndroidImage debugButtonImage = (AndroidImage) Assets.debugButton;
		debugButton = new MenuItem(debugButtonImage, 10, 10);
		entities.add(debugButton);
		mountainsY = GameScreen.HEIGHT - mountainsHeight - Floor.HEIGHT;
	}

	@Override
	protected void setupEntities() {
		setupPipes();
		setupFloor();
		setupCharacter();
	}

	Pipe pipe1, pipe2;

	private void setupPipes() {
		pipe1 = new Pipe();
		pipe2 = new Pipe();
		entities.add(pipe1);
		entities.add(pipe2);
		pipe1.setX(1200).setPipe(pipe2).addObserver(this);
		pipe2.setX(pipe1.getX() + Pipe.SEPARATION).setPipe(pipe1)
				.addObserver(this);
		Coin c1 = new Coin();
		Coin c2 = new Coin();
		entities.add(c1);
		entities.add(c2);
		pipe1.setCoin(c1);
		pipe2.setCoin(c2);
	}

	private void setupFloor() {
		floor = new Floor();
		entities.add(floor);
	}

	private void setupCharacter() {
		character = new GameCharacter();
		entities.add(character);
		character.addObserver(this);
	}

	@Override
	public void update(float deltaTime) {
		this.skyX -= skySpeed * deltaTime;
		if (skyX <= -GameScreen.WIDTH) {
			this.skyX = 0;
		}
		this.mountainsX -= mountainsSpeed * deltaTime;
		if (mountainsX <= -GameScreen.WIDTH) {
			this.mountainsX = 0;
		}

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		// if (touchEvents.size() > 0) {
		// Assets.click.play();
		// state = GameState.Running;
		// }
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		// 1. All touch input is handled here:
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (debugButton.collides(new Point(event.x, event.y))) {
					FlappyDroidGame.debugMode = FlappyDroidGame.debugMode == FlappyDroidGame.DebugMode.OFF ? FlappyDroidGame.DebugMode.FILL
							: FlappyDroidGame.DebugMode.OFF;
				}
				character.jump();
				Assets.jumpSound.play();
			}
		}
		// 2. Check miscellaneous events like death:
		if (livesLeft == 0) {
			state = GameState.GameOver;
		}

		// 3. Call individual update() methods here.
		// This is where all the game updates happen.
		// For example, robot.update();
		updateEntities(deltaTime);
		checkCollisions();
	}

	private void updateEntities(float delta) {
		for (Entity entity : entities) {
			entity.update(delta);
		}
	}

	protected void collisionDetected(AndroidEntity entity,
			Rectangle[] collisionRectangles) {
		livesLeft--;
		if (collisionRectangles == null || collisionRectangles.length <= 1
				|| collisionRectangles.length > 2) {
			return;
		}
		CollisionSpot collisionSpot = Rectangle.getCollisionSpot(
				collisionRectangles[0], collisionRectangles[1]);
		System.out.println(collisionSpot);
		switch (collisionSpot) {
		case LEFT:
			character.setX(entity.getX() - character.getWidth());
			break;
		case RIGHT:
			character.setX(entity.getX() + entity.getWidth());
			break;
		case TOP:
			character.setY(entity.getY() - character.getHeight());
			break;
		case BOTTOM:
			character.setY(entity.getY() + entity.getHeight());
			break;
		case UPPER_LEFT:
			character.setX(entity.getX() - character.getWidth());
			character.setY(entity.getY() - character.getHeight());
			break;
		case BOTTOM_LEFT:
			character.setY(entity.getY() + entity.getHeight());
			character.setX(entity.getX() - character.getWidth());
			break;
		case UPPER_RIGHT:
			character.setY(entity.getY() - character.getHeight());
			character.setX(entity.getX() + entity.getWidth());
			break;
		case BOTTOM_RIGHT:
			character.setY(entity.getY() + entity.getHeight());
			character.setX(entity.getX() + entity.getWidth());
			break;
		default:
			break;
		}
	}

	private void collisionDetected(AndroidEntity entity) {
		Assets.hitSound.play();
		livesLeft--;
	}

	private void checkCollisions() {
		// Rectangle[] collisionRectangles = null;
		// if (null != (collisionRectangles =
		// character.getCollisionRectangles(floor))) {
		// collisionDetected(floor, collisionRectangles);
		// return;
		// }
		// if (null != (collisionRectangles =
		// character.getCollisionRectangles(pipe1))) {
		// collisionDetected(floor, collisionRectangles);
		// return;
		// }
		// if (null != (collisionRectangles =
		// character.getCollisionRectangles(pipe2))) {
		// collisionDetected(floor, collisionRectangles);
		// return;
		// }

		if (character.collides(floor)) {
			collisionDetected(floor);
			return;
		}
		if (character.collides(pipe1)) {
			collisionDetected(pipe1);
			return;
		}
		if (character.collides(pipe2)) {
			collisionDetected(pipe2);
			return;
		}
		if (pipe1.getCoin().isVisible() && character.collides(pipe1.getCoin())) {
			pipe1.getCoin().setVisible(false);
			scored();
			return;
		}
		if (pipe2.getCoin().isVisible() && character.collides(pipe2.getCoin())) {
			pipe2.getCoin().setVisible(false);
			scored();
			return;
		}
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		drawGameOverUI();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nullify();
		FlappyDroidGame.saveHighScore();
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		// First draw the game elements.
		// Example:
		// g.drawImage(Assets.background, 0, 0);
		g.drawImage(Assets.background, skyX, skyY);
		g.drawImage(Assets.background, skyX + GameScreen.WIDTH, skyY);

		g.drawImage(Assets.mountains, mountainsX, mountainsY);
		g.drawImage(Assets.mountains, mountainsX + GameScreen.WIDTH, mountainsY);

		g.drawImage(Assets.mountains, mountainsX, mountainsY);
		g.drawImage(Assets.mountains, mountainsX + GameScreen.WIDTH, mountainsY);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready) {
			drawReadyUI();
		}
		if (state == GameState.Running) {
			drawRunningUI();
		}
		if (state == GameState.Paused) {
			drawPausedUI();
		}
		// if (state == GameState.GameOver) {
		// drawGameOverUI();
		// }

	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		entities = null;
		pipe1 = null;
		pipe2 = null;
		character = null;
		floor = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap again to start.", 0, 0, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		// Vector<Entity> entities = (Vector<Entity>) this.entities.clone();
		for (Entity entity : entities) {
			entity.draw(g);
		}

		// Defining a paint object
		Paint paint = new Paint();
		paint.setTextSize(40);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		g.drawString("Score: " + score, 10, 100, paint);
		g.drawString("Highest Score: " + FlappyDroidGame.HIGH_SCORE, 10, 200, paint);
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.fillRect(0, 0, g.getWidth(), g.getHeight(), Color.BLACK);
		int offsetX = 800 / 2 - Assets.gameOver.getWidth() / 2;
		int offsetY = 1200 / 2 - Assets.gameOver.getHeight() / 2;
		g.drawImage(Assets.gameOver, offsetX, offsetY);
	}

	@Override
	public void pause() {
		if (state == GameState.Running) {
			state = GameState.Paused;
		}
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	@Override
	public void update(Observable observable, Object arg) {
		throw new UnsupportedOperationException(
				"Observable might going to be removed.");
		// if (observable instanceof Pipe) {
		// // Pipe pipe = (Pipe) observable;
		// // pipe.deleteObservers();
		// // this.entities.remove(pipe);
		// scored();
		// } else if (observable instanceof GameCharacter) {
		// // user hit something
		// livesLeft--;
		// }
	}

	private void scored() {
		score++;
		if (score % 10 == 0) {
			Assets.tenCoinsSound.play();
		} else {
			Assets.coinSound.play();
		}
		if (score > FlappyDroidGame.HIGH_SCORE) {
			FlappyDroidGame.HIGH_SCORE = score;
		}
	}
}