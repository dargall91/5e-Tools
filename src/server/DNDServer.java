package server;

import library.*;
import monster.*;
import encounter.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class DNDServer extends Thread {
	private static final int buffSize = 6144;
	private Socket conn;
	private DNDServerSkeleton skeleton;
	
	DNDServer(Socket sock, DNDLibrary lib) {
		conn = sock;
		skeleton = new DNDServerSkeleton(lib);
	}
	
	public void run() {
		try {
			OutputStream os = conn.getOutputStream();
			InputStream is = conn.getInputStream();
			byte input[] = new byte[buffSize];
			int num = is.read(input, 0, buffSize);
			
			if (num != -1) {
				String request = new String(input, 0 , num);
				System.out.println("Client requested: " + request);
				String response = skeleton.callMethod(request);
				byte output[] = response.getBytes();
				os.write(output, 0, output.length);
				System.out.println("Response is: " + response);
			}
			
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
				DNDServer thread = new DNDServer(sock, lib);
				thread.start();
			}
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
		}
	}
}
