package com.fifino.framework.network;

import java.util.HashMap;

import com.loopj.twicecircled.android.http.AsyncHttpClient;
import com.loopj.twicecircled.android.http.AsyncHttpResponseHandler;
import com.loopj.twicecircled.android.http.RequestParams;

//
//import fifis.network.client.ClientReporter;
//import fifis.network.client.FifisClient;
//
////import java.io.BufferedWriter;
////import java.io.IOException;
////import java.io.OutputStreamWriter;
////import java.io.PrintWriter;
////import java.net.InetAddress;
////import java.net.Socket;
////import java.net.UnknownHostException;
//
////public class ClientHandler implements Runnable {
//public class ClientHandler extends FifisClient{
//	public static final int SERVERPORT = 5000;
//	public static final String SERVER_IP = "192.168.1.67";
////	public static final String SERVER_IP = "189.170.114.232"; 
//	public ClientHandler(){
//		this(new FlappyDroidClientReporter(), ClientHandler.SERVER_IP, ClientHandler.SERVERPORT);
//	}
//	public ClientHandler(ClientReporter reporter, String server, int port) {
//		super(reporter, server, port);
////		send("1");
//	}
//}
public class ClientHandler implements FifinoHttpClient {
    @Override
    public String post(String host, String url) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String get(String host, String url) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String post(String host, String url, String[] args) {
	AsyncHttpClient client = new AsyncHttpClient();
	HashMap<String, String> map = new HashMap<String, String>();
        for(int i=0;i<args.length; i++){
            map.put(args[i], args[++i]);
        }
	RequestParams params = new RequestParams(map);
        client.post(host + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String res) {
                System.out.println("RESPONSE: " + res);
            }
        });
        return "";
    }

    @Override
    public String get(String host, String url, String[] args) {
	AsyncHttpClient client = new AsyncHttpClient();
        client.get(host + url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String res) {
                System.out.println("RESPONSE: " + res);
            }
        });
        return "";
    }
    public void sendHighscore(int highScore) {
	// TODO Auto-generated method stub
	String url = "/com.fifino/flappydroid/highscore.php";
	String res = this.post("http://192.168.1.67", url, new String[]{ "highscore", ""+highScore });
	System.out.println("Send highscore: " + res);
    }

    public int getHighscore() {
	// TODO Auto-generated method stub
	return 0;
    }

} 