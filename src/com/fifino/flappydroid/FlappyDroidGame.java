package com.fifino.flappydroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;

import com.fifino.framework.network.ClientHandler;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;
import com.gameanalytics.android.GameAnalytics;

public class FlappyDroidGame extends AndroidGame {
	public static final String HIGHSCORE_FILE = "flappy-droid.txt";
	public static final String APP_DIR = "/flappy-droid";
	public static final String GA_KEY = "7c626ddc851d2c600b588abd287e6f0c";
	public static final String GA_SECRET_KEY = "8f6972a831f36d35c8cb2d4558b7820f44427c7c";
	public static int HIGH_SCORE = 0;
	public static boolean isSDPresent = false;
	private ClientHandler clientHandler;
//	Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	public enum DebugMode {
		OFF, FILL, DRAW
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientHandler = new ClientHandler();
        clientHandler.start();
        FlappyDroidGame.isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
//	    GameAnalytics.setAutoBatch(false);
        GameAnalytics.logFPS();
		GameAnalytics.initialise(this, FlappyDroidGame.GA_SECRET_KEY, FlappyDroidGame.GA_KEY);
		GameAnalytics.startSession(this);
	}

    @Override
    public void onResume() {
        super.onResume();
//        GameAnalytics.logFPS();
        GameAnalytics.startSession(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        GameAnalytics.stopSession();
    }
	public ClientHandler getClient(){
		return this.clientHandler;
	}
	public static DebugMode debugMode = FlappyDroidGame.DebugMode.DRAW;

	@Override
	public Screen getInitScreen() {
		FlappyDroidGame.loadHighScore();
		return new MainMenuScreen(this);
	}

	public static void loadHighScore() {
		FileInputStream fis;
			
		try {
			if(FlappyDroidGame.isSDPresent){
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
					FlappyDroidGame.HIGH_SCORE = Integer.parseInt(highScoreString);
				} else {
					dir.mkdir();
					file.createNewFile();
				}
			}
		} catch (IOException ex) {
			// Error loading the file.
			ex.printStackTrace();
		}
	}

	public static void saveHighScore(ClientHandler clientHandler) {
		String highScoreString = "" + FlappyDroidGame.HIGH_SCORE;
		try {
			if(FlappyDroidGame.isSDPresent){
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
		}finally{
			if(clientHandler != null){
				clientHandler.send("0 "+FlappyDroidGame.HIGH_SCORE);
			}
		}
	}
}