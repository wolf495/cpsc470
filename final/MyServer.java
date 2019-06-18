
package big_project;




import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
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

  //public static void main(String args[]) {
  public void init(int port){

    try {
        
        int portNumber = port;

        InetAddress address=InetAddress.getLocalHost();
        Scanner sc = new Scanner(System.in);
        System.out.println("SERVER ADDRESS: "+address+"\nSERVER PORT: "+portNumber+"\nnumber of players: ");
        maxClientsCount = sc.nextInt();
        
        threads = new clientThread[maxClientsCount];
        game.Start();
        

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }
        

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
    } catch (UnknownHostException ex) {
          Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null,ex);
    }
  }
}


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

      os.println("Welcome " + name
          + " to blackjack room.");
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

      while (game.hasPlayers()) {
          while(game.getPlayerCount()<maxClientsCount && game.hasPlayers()){
              synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("waiting for players... "+game.getPlayerCount()+"/"+maxClientsCount);
                }}
                try {
                            //System.out.println("SLEEP!");
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
              }
            }
          int count=1;
            game.Start();
            String line = "";
            while(game.hasPlayers()){
                line = "-------Round "+count+"-------";
                System.out.println(line);  
                synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                  if (threads[i] != null && threads[i].clientName != null) {
                    threads[i].os.println(line);
                }}}
                game.Round();
                synchronized (this) {
                String dealer_out;
                dealer_out="Dealer's Hand =";
                    for(String o:game.getDealerHand()){
                    dealer_out=dealer_out+" "+o;
                    }
                    dealer_out+=" (Points)=> "+BlackjackRules.countPoints(game.getDealerHand());
                    
                    System.out.println(dealer_out);
                    synchronized (this) {
                    for (int i = 0; i < maxClientsCount; i++) {
                      if (threads[i] != null && threads[i].clientName != null) {
                        threads[i].os.println(dealer_out);
                    }}}
                }
                for(PlayerAbstract i: game.getPlayers()){
                    String out;
                    out=i.getName()+"'s Hand =";
                    for(String o:i.getHand()){
                    out=out+" "+o;
                    }
                    out+=" (Points)=> "+BlackjackRules.countPoints(i.getHand());
                    out+=" (Bank)=> "+i.getBank();
                    System.out.println(out);
                    synchronized (this) {
                    for (int p = 0; p < maxClientsCount; p++) {
                      if (threads[p] != null && threads[p].clientName != null) {
                        threads[p].os.println(out);
                    }}}
                }
                game.EndRound();
                count++;
            }
          

          
      }

      os.println("*** Bye " + name + " ***");

      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }

    try{
      is.close();
      os.close();
      clientSocket.close();
      //System.exit(0);
    } catch (IOException e) {
    }
  }   catch (IOException ex) {   
          Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
      }   
      

}}