package com.fifino.framework.network;

import fifis.network.client.ClientReporter;
import fifis.network.client.FifisClient;

//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;

//public class ClientHandler implements Runnable {
public class ClientHandler extends FifisClient{
	private static final int SERVERPORT = 5000;
	private static final String SERVER_IP = "192.168.1.67";
//	private static final String SERVER_IP = "189.170.114.232"; 
	public ClientHandler(){
		this(new FlappyDroidClientReporter(), ClientHandler.SERVER_IP, ClientHandler.SERVERPORT);
	}
	public ClientHandler(ClientReporter reporter, String server, int port) {
		super(reporter, server, port);
//		send("1");
	}
}