package big_project;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class old_MyServer {
    static int player_num;
    static Game game = Game.getInstance();
public static void main(String args[]) throws UnknownHostException{

    InetAddress address=InetAddress.getLocalHost();
    Socket s=null;
    ServerSocket ss2=null;
    int count = 0;
    game.Start();
     Scanner sc = new Scanner(System.in);
        System.out.println("number of players: ");
        player_num = sc.nextInt();
    System.out.println("Server at ip: "+ address);
    System.out.println("Server Listening......");
    try{
        ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined

    }
    catch(IOException e){
    e.printStackTrace();
    System.out.println("Server error");

    }

    while(true && count <= player_num){
        try{
            s= ss2.accept();
            count++;
            System.out.println("connection Established");
            ServerThread st=new ServerThread(s);
            st.start();

        }

    catch(Exception e){
        e.printStackTrace();
        System.out.println("Connection Error");

    }
    }
    //if(count > player_num){
    //    System.out.println("connection rejected: max players");
    //}

}

}

class ServerThread extends Thread{  

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    int player_tot;
    Game game = Game.getInstance();
    String lineTest=null;
    //boolean firstPlayer=true;

    public ServerThread(Socket s){
        this.s=s;
        this.player_tot=old_MyServer.player_num;
        //game.Start();
    }

    public void run() {
    try{
        is= new BufferedReader(new InputStreamReader(s.getInputStream()));
        os=new PrintWriter(s.getOutputStream());

    }catch(IOException e){
        System.out.println("IO error in server thread");
    }
    int check = Integer.parseInt(this.getName().substring(this.getName().lastIndexOf("-") + 1))+1;
    if(!(check > player_tot)){
    try {
        line=is.readLine();
        System.out.println("New player: "+line);
        while(true){ //--game logic?
            System.out.println("INLINE_"+line);
            if(line.indexOf(":")<0){
                game.addPlayer(line);
                //firstPlayer = false;
                lineTest = "All:You are registered as '"+line+"'\tPlease wait for other players...";
                os.println(lineTest);os.flush();
            }else{
                if(game.getPlayerCount()!=player_tot){
                    lineTest = "All:Waiting for players to join... "+game.getPlayerCount()+"/"+player_tot;//System.out.println(lineTest);
                    os.println(lineTest);os.flush();
                }else{
                    game.Round();
                    lineTest = "All:test-run";
                    System.out.println(lineTest);
                   os.println(lineTest);os.flush();
                    //while(game.hasPlayers()){
                    //game.Round();
                    game.EndRound();
                    /*for(PlayerAbstract p : game.getPlayers()){
                        lineTest = p.getName()+":Round Summary - Bank= "+p.getBank()+" ,Bet="+p.getBet()+" ,Hand played= ";
                        for(String i : p.getHand()){
                        lineTest=lineTest+" "+i;
                        }
                        os.println(lineTest);os.flush();
                    }*/
                    //game.EndRound();
                    //}
                    //
                    //for(int i=0;i< 3;i++){
                    
                    //}
                       
                }
            }
            line=is.readLine();
        }   
    } catch (IOException e) {

        line=this.getName(); //reused String line for getting thread name
        System.out.println("IO Error/ Client "+line+" terminated abruptly");
    }
    catch(NullPointerException e){
        line=this.getName(); //reused String line for getting thread name
        System.out.println("Client "+line+" Closed");
    }
    
    finally{    
    try{
        System.out.println("Connection Closing..");
        if (is!=null){
            is.close(); 
            System.out.println(" Socket Input Stream Closed");
        }

        if(os!=null){
            os.close();
            System.out.println("Socket Out Closed");
        }
        if (s!=null){
        s.close();
        System.out.println("Socket Closed");
        }

        }
    catch(IOException ie){
        System.out.println("Socket Close Error");
    }
    }//end finally
    }//end if
    else{
        try{
        System.out.println("New Connections Closing..MAX PLAYERS already");
        if (is!=null){
            is.close(); 
            System.out.println(" Socket Input Stream Closed");
        }

        if(os!=null){
            os.close();
            System.out.println("Socket Out Closed");
        }
        if (s!=null){
        s.close();
        System.out.println("Socket Closed");
        }

        }
    catch(IOException ie){
        System.out.println("Socket Close Error");
    }
        }
    }
}
