package com.fifino.framework.network;
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
public class ClientHandler implements HtmlClient {
    @Override
    public String post(String url) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String get(String url) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String post(String url, String[] args) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String get(String url, String[] args) {
	// TODO Auto-generated method stub
	return null;
    }
    public void sendHighscore(int highScore) {
	// TODO Auto-generated method stub
	
    }

    public int getHighscore() {
	// TODO Auto-generated method stub
	return 0;
    }

} 