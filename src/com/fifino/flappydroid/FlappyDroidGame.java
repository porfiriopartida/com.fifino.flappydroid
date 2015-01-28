package com.fifino.flappydroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
//import android.provider.MediaStore.Files;



import com.fifino.framework.network.AnalyticsProvider;
import com.fifino.framework.network.ClientHandler;
import com.fifino.framework.network.DebugAnalyticsProvider;
import com.fifino.framework.network.NullAnalyticsProvider;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class FlappyDroidGame extends AndroidGame {
	public static boolean isSDPresent = false;
    public static final Env ENVIRONMENT = Env.PROD;
    // public static final String REFERER = "Google Play";
    public static final String SERVER = "http://porfiriopartida.com/games/android/com.fifino.flappydroid";
    public static final String HIGHSCORE_FILE = "flappy-droid.txt";
    public static final String APP_DIR = "/flappy-droid";
    public static final String GA_KEY = "7c626ddc851d2c600b588abd287e6f0c";
    public static final String GA_SECRET_KEY = "8f6972a831f36d35c8cb2d4558b7820f44427c7c";
    public static int HIGH_SCORE = 0;
    public static int HIGHEST_SCORE = 0;
    public static boolean IS_SD_PRESENT = false;
    public static boolean IS_WIFI_CONNECTED = false;
    public static AnalyticsProvider ANALYTICS_PROVIDER;
    public static DebugMode DEBUG_MODE = FlappyDroidGame.DebugMode.OFF;
    private ClientHandler clientHandler;

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
            ANALYTICS_PROVIDER = FlappyDroidGame.ENVIRONMENT == Env.PROD
                    ? new NullAnalyticsProvider()
                    : new DebugAnalyticsProvider();
            // clientHandler.start();
            FlappyDroidGame.IS_SD_PRESENT = android.os.Environment
                    .getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED);
            ANALYTICS_PROVIDER.start(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isWifiConnected(AndroidGame game){
        ConnectivityManager connManager = (ConnectivityManager) game.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConnected = mWifi.isConnected();
        FlappyDroidGame.IS_WIFI_CONNECTED = isWifiConnected;
        if(isWifiConnected){
            ANALYTICS_PROVIDER.pause(game);
        }
        return isWifiConnected;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FlappyDroidGame.IS_WIFI_CONNECTED){
            ANALYTICS_PROVIDER.resume(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ANALYTICS_PROVIDER.pause(this);
    }

    public AnalyticsProvider getAnalyticsProvider() {
        return FlappyDroidGame.ANALYTICS_PROVIDER;
    }
    public ClientHandler getClient() {
        return this.clientHandler;
    }

    @Override
    public Screen getInitScreen() {
        return new MainMenuScreen(this);
    }

    public static void loadHighScore(FlappyDroidGame game) {
        try {
            FlappyDroidGame.IS_WIFI_CONNECTED = FlappyDroidGame.isWifiConnected(game);
            if(FlappyDroidGame.IS_WIFI_CONNECTED){
                FlappyDroidGame.HIGHEST_SCORE = game.getClient().getHighscore();
            }
        } catch (Exception e) {
            e.printStackTrace();
            FlappyDroidGame.ANALYTICS_PROVIDER.exception(e,
                    "load_highscore:server");
        }

        FileInputStream fis = null;
        File file;
        String filePath;
        try {
            if (FlappyDroidGame.IS_SD_PRESENT) {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath()
                        + FlappyDroidGame.APP_DIR);
                file = new File(dir, FlappyDroidGame.HIGHSCORE_FILE);
                if (file.exists()) {
                    fis = new FileInputStream(file);
                } else {
                    dir.mkdir();
                    file.createNewFile();
                }
            }else{
                String internalFile = FlappyDroidGame.HIGHSCORE_FILE;
                file = new File(internalFile);
                filePath = game.getFilesDir().getPath().toString() + File.separator + FlappyDroidGame.HIGHSCORE_FILE;
                File ff = new File(filePath);
                boolean fExists = ff.exists();
                if (!fExists) {
                	ff.createNewFile();
                    ff = new File(filePath);
                    fExists = ff.exists();
	                FileOutputStream outputStream = game.openFileOutput(FlappyDroidGame.HIGHSCORE_FILE, Context.MODE_PRIVATE);
	            	outputStream.write("0".getBytes());
	            	outputStream.close();
                }
                fis = game.openFileInput(FlappyDroidGame.HIGHSCORE_FILE);
            }
            StringBuilder builder = new StringBuilder();
            int ch;
            char c;
            while ((ch = fis.read()) != -1) {
            	c = (char) ch;
                builder.append(c);
            }
            String highScoreString = builder.toString();
            fis.close();
            FlappyDroidGame.HIGH_SCORE = Integer
                    .parseInt(highScoreString);
        } catch (IOException ex) {
            // Error loading the file.
            FlappyDroidGame.ANALYTICS_PROVIDER.exception(ex,
                    "load_highscore:file");
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            filePath = game.getFilesDir().getPath().toString() + File.separator + FlappyDroidGame.HIGHSCORE_FILE;
            File ff = new File(filePath);
            boolean fExists = ff.exists();
            if (fExists) {
            	ff.delete();
            }
            ex.printStackTrace();
        }
    }

    public static void saveHighScore(FlappyDroidGame game) {
        ClientHandler clientHandler = game.getClient();
        FlappyDroidGame.IS_WIFI_CONNECTED = FlappyDroidGame.isWifiConnected(game);
        String highScoreString = "" + FlappyDroidGame.HIGH_SCORE;
        try {
            if (FlappyDroidGame.IS_SD_PRESENT) {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath()
                        + FlappyDroidGame.APP_DIR);
                File file = new File(dir, FlappyDroidGame.HIGHSCORE_FILE);
                FileOutputStream f = new FileOutputStream(file);
                byte[] contentInBytes = highScoreString.getBytes();
                f.write(contentInBytes);
                f.close();
            }else{
                FileOutputStream outputStream = null;
                try {
                    String internalFile = FlappyDroidGame.HIGHSCORE_FILE; //game.getFilesDir().toString() + File.separator + FlappyDroidGame.HIGHSCORE_FILE;
                    outputStream = game.openFileOutput(internalFile, Context.MODE_PRIVATE);
                    outputStream.write((FlappyDroidGame.HIGH_SCORE + "").getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (clientHandler != null) {
                if(FlappyDroidGame.IS_WIFI_CONNECTED){
                    clientHandler.sendHighscore(FlappyDroidGame.HIGH_SCORE);
                }
            }
        }
    }
}