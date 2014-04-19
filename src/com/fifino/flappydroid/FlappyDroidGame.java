package com.fifino.flappydroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class FlappyDroidGame extends AndroidGame {
	public static final String HIGHSCORE_FILE = "flappy-droid.txt";
	public static final String APP_DIR = "/flappy-droid";
	public static int HIGH_SCORE = 0;

	public enum DebugMode {
		OFF, FILL, DRAW
	}

	public static DebugMode debugMode = FlappyDroidGame.DebugMode.OFF;

	@Override
	public Screen getInitScreen() {
		FlappyDroidGame.loadHighScore();
		return new MainMenuScreen(this);
	}

	public static void loadHighScore() {
		FileInputStream fis;
		try {
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
		} catch (IOException ex) {
			// Error loading the file.
			ex.printStackTrace();
		}
	}

	public static void saveHighScore() {
		try {
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File(sdCard.getAbsolutePath()
					+ FlappyDroidGame.APP_DIR);
			File file = new File(dir, FlappyDroidGame.HIGHSCORE_FILE);
			FileOutputStream f = new FileOutputStream(file);
			String highScoreString = "" + FlappyDroidGame.HIGH_SCORE;
			byte[] contentInBytes = highScoreString.getBytes();
			f.write(contentInBytes);
			f.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}