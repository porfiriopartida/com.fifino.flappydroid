package com.kilobolt.samplegame;


import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
import android.graphics.Paint;
import com.fifino.framework.Entity;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;

public class MainGameSkeleton extends Screen {

    enum GameState {
        Ready, Running, Paused, GameOver
    }

    // GameState state = GameState.Ready;
    GameState state = GameState.Running;

    // Variable Setup
    // You would create game objects here.
    ArrayList<Entity> entities;
    int livesLeft = 1;
    Paint paint;

    public MainGameSkeleton(Game game) {
        super(game);
        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        // Setup entities
        entities = new ArrayList<Entity>();
        initializeAssets();
        setupEntities();
    }

    protected void initializeAssets() {
        // Graphics graphics = game.getGraphics();
        // Assets.character = graphics.newImage("character.png",
        // ImageFormat.RGB565);
        // Assets.click = game.getAudio().createSound("explode.ogg");
    }

    @Override
    protected void setupEntities() {
        // character = new GameCharacter();
        // entities.add(character);
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
//            Assets.clickSound.play();
            state = GameState.Running;
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        // This is identical to the update() method from our Unit 2/3 game.
        // 1. All touch input is handled here:
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_DOWN) {
                // On tap down
                // Assets.click.play();
            } else if (event.type == TouchEvent.TOUCH_DRAGGED) {
                // On Drag
            } else if (event.type == TouchEvent.TOUCH_UP) {
                // On tap up
            }
        }

        // 2. Check miscellaneous events like death:
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }

        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        // For example, robot.update();
        updateCharacter();
        checkCollisions();
    }

    private void updateCharacter() {
    }

    private void checkCollisions() {
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                resume();
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                game.setScreen(new MainMenuScreen(game));
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

//    private void nullify() {
//
//        // Set all variables to null. You will be recreating them in the
//        // constructor.
//        paint = null;
//
//        // Call garbage collector to clean up memory.
//        System.gc();
//    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Ready.", 0, 0, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        for (Entity entity : entities) {
            entity.draw(g);
        }
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to resume.", 0, 0, paint);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running) {
            state = GameState.Paused;
        }
    }

    @Override
    public void resume() {
        state = GameState.Running;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }

}