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
public class MainFacade {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("number of players: ");
        String number = sc.nextLine();
        System.out.println("list of player name(s): (ex. john,jake,sam)");
        String Playerlist = sc.nextLine();
        System.out.println(number+" "+Playerlist);
        /*
        new MyClient("127.0.0.1", 6000);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
    }
}
