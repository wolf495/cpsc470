/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package big_project;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alfredo
 */
public class Main {
    
    
    public static void main(String args[]) {
    Game game = Game.getInstance();
    int maxClientsCount;
    ArrayList<String> names = new ArrayList<String>();
    //ArrayList<PlayerAbstract> plays = game
    Scanner sc = new Scanner(System.in);
    System.out.println("number of players: ");
    maxClientsCount = sc.nextInt();
    sc.nextLine();
    for(int i = 0; i < maxClientsCount; i++){
        System.out.println("player "+i+" name: ");
        names.add(sc.nextLine());
    }
    game.setUp(names);
    game.Start();
    System.out.println(game.getPlayerCount());
    for(PlayerAbstract i : game.getPlayers()){
        System.out.println();
    }
    game.runThrough();
    
    }
}
