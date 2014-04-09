package com.kilobolt.samplegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

//import java.util.Vector;
import android.graphics.Color;
import android.graphics.Paint;

import com.fifino.framework.Entity;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.samplegame.entities.Floor;
//import com.kilobolt.samplegame.entities.Floor;
import com.kilobolt.samplegame.entities.GameCharacter;
import com.kilobolt.samplegame.entities.Pipe;

public class GameScreen extends Screen implements Observer {

	enum GameState {
		Ready, Running, Paused, GameOver
	}

	public static int HIGH_SCORE = 0;
	// GameState state = GameState.Ready;
	GameState state = GameState.Running;

	// Variable Setup
	// You would create game objects here.

	int livesLeft = 1;
	int score = 0;
	Paint paint;
	ArrayList<Entity> entities;
	// Pipe[] pipes;
	GameCharacter character;
	// private Floor floor;
	Random rnd;

	private Floor floor;

	public GameScreen(Game game) {
		super(game);

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
		Graphics graphics = game.getGraphics();
		Assets.background = graphics.newImage("bg-vertical.png",
				ImageFormat.RGB565);
		Assets.bluePipe = graphics
				.newImage("blue-pipe.png", ImageFormat.RGB565);
		Assets.tileWater = graphics.newImage("tile-water.png",
				ImageFormat.RGB565);
		Assets.tileDirt = graphics
				.newImage("tile-dirt.png", ImageFormat.RGB565);
		Assets.character = graphics.newImage("character.png",
				ImageFormat.RGB565);
		Assets.click = game.getAudio().createSound("explode.ogg");
	}

	@Override
	protected void setupEntities() {
		setupFloor();
		setupCharacter();
		setupPipes();
	}

	Pipe pipe1, pipe2;

	private void setupPipes() {
		// pipes1 = new Pipe[3];
		// for (int i = 0; i < pipes.length; i++) {
		// boolean upsideDown = i%2 == 0;
		// Pipe pipe = new Pipe(upsideDown);
		// entities.add(pipe);
		// pipe.setVisible(false);
		// pipe.setX(i*800 + 800);
		// // pipe.setCharacter(character);
		// pipe.addObserver(this);
		// pipes[i] = pipe;
		// }
		pipe1 = new Pipe(true);
		pipe2 = new Pipe(false);
		entities.add(pipe1);
		entities.add(pipe2);
		pipe1.setX(801).setPipe(pipe2).addObserver(this);
		pipe2.setX(pipe1.getX() + Pipe.SEPARATION).setPipe(pipe1)
				.addObserver(this);
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

		if (touchEvents.size() > 0) {
			Assets.click.play();
			state = GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		// 1. All touch input is handled here:
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if (event.type == TouchEvent.TOUCH_DOWN) {
				character.jump();
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

	private void hitSomething() {
//		character.setCharacterY(0);
		livesLeft--;
	}

	private void checkCollisions() {
		if (character.collides(floor)) {
			hitSomething();
			return;
		}
		if (character.collides(pipe1)) {
			hitSomething();
			return;
		}
		if (character.collides(pipe2)) {
			hitSomething();
			return;
		}
		// for (Pipe pipe : pipes) {
		// if (character.collides(pipe)) {
		// hitSomething();
		// return;
		// }
		// }
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
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 300 && event.x < 980 && event.y > 100
						&& event.y < 500) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		// First draw the game elements.
		// Example:
		g.drawImage(Assets.background, 0, 0);
		// g.drawImage(Assets.character, characterX, characterY);

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
		if (state == GameState.GameOver) {
			drawGameOverUI();
		}

	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;

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
		g.drawString("Highest Score: " + GameScreen.HIGH_SCORE, 10, 200, paint);
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.fillRect(0, 0, 801, 1201, Color.BLACK);
		g.drawString("GAME OVER.", 350, 300, paint);
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
		System.out.println(observable.getClass().toString());
		if (observable instanceof Pipe) {
			// Pipe pipe = (Pipe) observable;
			// pipe.deleteObservers();
			// this.entities.remove(pipe);
			score++;
			if (score > GameScreen.HIGH_SCORE) {
				GameScreen.HIGH_SCORE = score;
			}
		} else if (observable instanceof GameCharacter) {
			// user hit something
			livesLeft--;
		}
	}
}