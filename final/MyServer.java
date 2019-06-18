
package big_project;

//Example 26 (updated)


import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * A chat server that delivers public and private messages.
 */
public class MyServer {

  // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static int maxClientsCount;
  private static clientThread[] threads;
  private static Game game = Game.getInstance();

  public static void main(String args[]) {

    // The default port number.
    int portNumber = 4445;
    /*if (args.length < 1) {
      System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
          + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }*/
    
    Scanner sc = new Scanner(System.in);
        System.out.println("number of players: ");
        maxClientsCount = sc.nextInt();
        
        threads = new clientThread[maxClientsCount];
        game.Start();

    /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
     */
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) {
            (threads[i] = new clientThread(clientSocket, threads)).start();
            break;
          }
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server at max players. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class clientThread extends Thread {

  private String clientName = null;
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;
  private Game game ;

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
    game =Game.getInstance();
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;

    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      String name;
      while (true) {
        os.println("Enter your name.");
        name = is.readLine().trim();
        if (name.indexOf('@') == -1) {
          break;
        } else {
          os.println("The name should not contain '@' character.");
        }
      }

      /* Welcome the new the client. */
      os.println("Welcome " + name
          + " to blackjack room.\nTo leave enter /quit in a new line.");
      game.addPlayer(name);
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            clientName = "@" + name;
            break;
          }
        }
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this) {
            threads[i].os.println("*** A new user " + name
                + " entered the blackjack room !!! ***");
          }
        }
      }
      /* Start the conversation. */
      while (game.hasPlayers()) {
          while(game.getPlayerCount()<maxClientsCount){
              synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("waiting for players... "+game.getPlayerCount()+"/"+maxClientsCount);
                }}
                try {
                            //System.out.println("SLEEP!");
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(old_MyClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
              }
            }
          String line = "_";
          game.Round();
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("<" + name + "> " + line);
              }}}
          game.EndRound();
          
      }

      os.println("*** Bye " + name + " ***");

      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
    try{
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }   catch (IOException ex) {   
          Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
      }   
      

}}