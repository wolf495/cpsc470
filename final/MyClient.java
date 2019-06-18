
package big_project;



import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient implements Runnable {


  private static Socket clientSocket = null;

  private static PrintStream os = null;

  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
  public static void main(String[] args) {


      int portNumber;
      String host;
      
      Scanner sc = new Scanner(System.in);
      System.out.println("SERVER ADDRESS: ");
        host = sc.nextLine();
      
        System.out.println("SERVER PORT: ");
        portNumber = sc.nextInt();
        sc.nextLine();
    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }


    if (clientSocket != null && os != null && is != null) {
      try {
        new Thread(new MyClient()).start();
        while (!closed) {
          os.println(inputLine.readLine().trim());
        }

        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }


  public void run() {

    String responseLine;
    try {
      while ((responseLine = is.readLine()) != null) {
        System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}