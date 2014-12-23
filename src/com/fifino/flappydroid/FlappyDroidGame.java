package com.fifino.flappydroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;

import com.fifino.framework.network.AnalyticsProvider;
import com.fifino.framework.network.ClientHandler;
import com.fifino.framework.network.DebugAnalyticsProvider;
import com.fifino.framework.network.GameAnalyticsProvider;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class FlappyDroidGame extends AndroidGame {
    public static final Env ENVIRONMENT = Env.TESTING;
//    public static final String REFERER = "Google Play";
    public static final String SERVER = "http://porfiriopartida.com/games/android/com.fifino.flappydroid";
    public static final String HIGHSCORE_FILE = "flappy-droid.txt";
    public static final String APP_DIR = "/flappy-droid";
    public static final String GA_KEY = "7c626ddc851d2c600b588abd287e6f0c";
    public static final String GA_SECRET_KEY = "8f6972a831f36d35c8cb2d4558b7820f44427c7c";
    public static int HIGH_SCORE = 0;
    public static boolean isSDPresent = false;
    private ClientHandler clientHandler;
    static AnalyticsProvider analyticsProvider;

    // Boolean isSDPresent =
    // android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    public enum DebugMode {
	OFF, FILL, DRAW
    }

    public enum Env {
	PROD, TESTING
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	try {
		clientHandler = new ClientHandler();
		analyticsProvider = FlappyDroidGame.ENVIRONMENT == Env.PROD ? new GameAnalyticsProvider():new DebugAnalyticsProvider();
		// clientHandler.start();
		FlappyDroidGame.isSDPresent = android.os.Environment
			.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		analyticsProvider.start(this);
		FlappyDroidGame.loadHighScore(this);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void onResume() {
	super.onResume();
	analyticsProvider.resume(this);
    }

    @Override
    public void onPause() {
	super.onPause();
	analyticsProvider.pause(this);
    }


    public AnalyticsProvider getAnalyticsProvider() {
	return FlappyDroidGame.analyticsProvider;
    }
    public ClientHandler getClient() {
	return this.clientHandler;
    }

    public static DebugMode debugMode = FlappyDroidGame.DebugMode.DRAW;

    @Override
    public Screen getInitScreen() {
	return new MainMenuScreen(this);
    }

    public static void loadHighScore(FlappyDroidGame game) {
	try {
	    FlappyDroidGame.HIGH_SCORE = game.getClient().getHighscore();
	}catch(Exception e){
	    FlappyDroidGame.analyticsProvider.exception(e, "load_highscore:server");
	}
	
	FileInputStream fis;
	try {
	    if (FlappyDroidGame.isSDPresent) {
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath()
			+ FlappyDroidGame.APP_DIR);
		// if(dir.exists()){
		// File files[] = dir.listFiles();
		// for(File file : files){
		// file.delete();
		// }
		// dir.delete();
		// }
		File file = new File(dir, FlappyDroidGame.HIGHSCORE_FILE);
		if (file.exists()) {
		    fis = new FileInputStream(file);
		    StringBuilder builder = new StringBuilder();
		    int ch;
		    while ((ch = fis.read()) != -1) {
			builder.append((char) ch);
		    }
		    String highScoreString = builder.toString();
		    fis.close();
		    FlappyDroidGame.HIGH_SCORE = Integer
			    .parseInt(highScoreString);
		} else {
		    dir.mkdir();
		    file.createNewFile();
		}
	    }
	} catch (IOException ex) {
	    // Error loading the file.
	    FlappyDroidGame.analyticsProvider.exception(ex, "load_highscore:file");
	    ex.printStackTrace();
	}
    }

    public static void saveHighScore(ClientHandler clientHandler) {
	String highScoreString = "" + FlappyDroidGame.HIGH_SCORE;
	try {
	    if (FlappyDroidGame.isSDPresent) {
		File sdCard = Environment.getExternalStorageDirectory();

		File dir = new File(sdCard.getAbsolutePath()
			+ FlappyDroidGame.APP_DIR);
		File file = new File(dir, FlappyDroidGame.HIGHSCORE_FILE);
		FileOutputStream f = new FileOutputStream(file);
		byte[] contentInBytes = highScoreString.getBytes();
		f.write(contentInBytes);
		f.close();
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    if (clientHandler != null) {
		clientHandler.sendHighscore(FlappyDroidGame.HIGH_SCORE);
	    }
	}
    }
}