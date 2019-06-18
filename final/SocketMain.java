/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package big_project;

import java.util.Scanner;

/**
 *
 * @author alfredo
 */
public class SocketMain {
    public static void main(String args[]) {
        MyServer newServer = new MyServer();
        MyLocal newLocal = new MyLocal();
        
    Scanner sc = new Scanner(System.in);
    int port;
    String user;
    
    System.out.println("local or socket?");
    user = sc.nextLine();
    if(user.contains("local")){
        newLocal.init();
    }else{
    
        System.out.println("enter a port to use: ");
        port = sc.nextInt();
        newServer.init(port);
    }
    }
}
