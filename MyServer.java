package big_project;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {

	public class Connection implements Runnable {

		@Override
		public void run() {
			if (hasConnections) {
				System.out.println("Connection already established");
			} else {
				try {
					listener = new ServerSocket(port);
					System.out.println("Attempting connection on port " + port);
					socket = listener.accept();
					hasConnections = true;
					writer = new PrintWriter(socket.getOutputStream(),true);
					System.out.println("Connection established");
					//writer.println("Argh! I smell pirates!!!");
                                        
                                        
				} catch (IOException e) {
					System.out.println("Failure establishing connection");
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean hasConnections;
	public ServerSocket listener;
	private int port = 6000;
	public Socket socket;
	private PrintWriter writer;
        private DataInputStream in  =  null; 

	public MyServer() {
		this.hasConnections = false;
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		threadPool.execute(new Connection());
		System.out.println("Finished initialization for proxy on server side");
	}

	public void write(String msg) {
		
		if (hasConnections) {
			System.out.println("Writing data to client");
			writer.println(msg);			
		} else {
			System.out.println("No client to write data");
		}
	}
        public static void main(String args[]) 
    { 
        MyServer server = new MyServer(); 
    } 
}
