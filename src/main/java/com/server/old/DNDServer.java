package com.server.old;


import java.net.*;
import java.io.*;

public class DNDServer extends Thread {
	private static final int buffSize = 1024;
	private Socket conn;
	private DNDServerSkeleton skeleton;
	
	DNDServer(Socket sock, DNDLibrary lib, ServerCombatScreen scs) {
		conn = sock;
		skeleton = new DNDServerSkeleton(lib, scs);
	}
	
	public void run() {
		try {
			String request = "";
			OutputStream os = conn.getOutputStream();
			InputStream is = conn.getInputStream();
			byte input[] = new byte[buffSize];

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			request = reader.readLine();

			//TODO: update response to use PrintWriter to maintain consistency with client
			System.out.println("Client requested: " + request);
			String response = skeleton.callMethod(request);
			byte bytesToSend[] = response.getBytes();
			os.write(bytesToSend, 0, bytesToSend.length);
			System.out.println("Response is: " + response);
			
			is.close();
			os.close();
			conn.close();
		} catch (IOException e) {
			System.out.println("I/OException: " + e.getMessage());
		}
	}
	
	public static void main(String args[]) {
		Socket sock;
		DNDLibrary lib = new DNDLibraryImpl();
		ServerCombatScreen scs = new ServerCombatScreen(lib);
		
		if (args.length != 1) {
			System.out.println("No port selected. Defaulting to 8000.");
			args[0]= "8000";
		}
		
		try {
			int port = Integer.parseInt(args[0]);
			
			if (port <= 1024) {
				port = 8888;
				System.out.println("Invalid port. Shifting to default port " + port);
			}
			
			ServerSocket server = new ServerSocket(port);
			
			while(true) {
				System.out.println("DNDServer waiting for connections on port " + port);
				sock = server.accept();
				DNDServer thread = new DNDServer(sock, lib, scs);
				thread.start();
			}
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
		}
	}
}
