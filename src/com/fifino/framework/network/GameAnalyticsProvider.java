package com.fifino.framework.network;

import org.json.JSONException;
import org.json.JSONObject;

import com.fifino.flappydroid.FlappyDroidGame;
import com.gameanalytics.android.GameAnalytics;
import com.kilobolt.framework.implementation.AndroidGame;

/**
 * Game Analytics Provider for tracking.
 * 
 * @author porfiriopartida
 * 
 */
public class GameAnalyticsProvider implements AnalyticsProvider {

    @Override
    public void track(String event, String value) {
	int evt = getSwitchStr(event);
	switch (evt) {
    	case 0:
    	    break;
    	default:
    	    break;
	}
    }
    private int getSwitchStr(String str){
	if("Acquired".equals(str)){
	    return 0;
	}else if("Acquired".equals(str)){
	    return 1;
	}
	return -1;
    }

    @Override
    public void track(String event, double value, float x, float y, float z) {
	this.track(event, new JSONObject(), -1, x, y, z);
    }

    @Override
    public void track(String event, float x, float y, float z) {
	this.track(event, -1, x, y, z);
    }

    @Override
    public void stop(Object args) {
	GameAnalytics.stopSession();
    }

    @Override
    public void start(Object obj) {
	AndroidGame game = (AndroidGame) obj;
	GameAnalytics.initialise(game, FlappyDroidGame.GA_SECRET_KEY,
		FlappyDroidGame.GA_KEY);
	GameAnalytics.startSession(game);
    }

    @Override
    public void pause(Object args) {
	this.stop(args);
    }

    @Override
    public void resume(Object obj) {
	AndroidGame game = (AndroidGame) obj;
	GameAnalytics.startSession(game);
    }

    @Override
    public void track(String event, JSONObject obj, float value, float x,
	    float y, float z) {
	// TODO Auto-generated method stub
	String level;
	String area;
	try {
	    level = obj.getString("level");
	    area = obj.getString("area");
	    GameAnalytics.newDesignEvent(event + ":Level" + level + ":x:" + x,
		    value, area, x, y, 0f);
	    GameAnalytics
		    .newDesignEvent("Coord:Level" + level + ":" + event, x);
	    GameAnalytics
		    .newDesignEvent("Coord:Level" + level + ":" + event, y);
	} catch (JSONException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    @Override
    public void exception(Exception ex, String caller) {
	GameAnalytics.newQualityEvent("Exception:"+caller+":"+ex.getMessage(),ex.getLocalizedMessage());
    }
    @Override
    public void error(String error, JSONObject track) {
	try {
	    String message = track.getString("message");
	    GameAnalytics.newQualityEvent("Error:"+error, message);
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

}
