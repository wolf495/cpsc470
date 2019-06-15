package big_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyClient {
public static void main(String args[]) throws IOException{


    InetAddress address=InetAddress.getLocalHost();
    Socket s1=null;
    String line=null;
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;
    String name = "";
    boolean locked = false;
    
    try {
        s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
        br= new BufferedReader(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    System.out.println("Client Address : "+address);
    //System.out.println("Enter Data to echo Server ( Enter QUIT to end):");
    System.out.println("Enter Name: ");
    String response=null;
    try{
        line=br.readLine();
        //name = line;
        while(line.indexOf("QUIT")<0){
                if(name == ""){
                    //System.out.println("Enter Player Name: ");
                    name = line;locked = true;
                    os.println(line);
                    os.flush();
                }
                else{
                    if(locked){
                        response=is.readLine();
                        System.out.println("Game>>"+response);
                    }else{
                        line=br.readLine();
                        line = name+":"+line;
                        os.println(line);
                        os.flush();
                    }
                }
                
  
                
                
            }



    }
    catch(IOException e){
        e.printStackTrace();
    System.out.println("Socket read Error");
    }
    finally{

        is.close();os.close();br.close();s1.close();
                System.out.println("Connection Closed");

    }

}
}
