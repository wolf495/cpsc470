/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package big_project;

import java.util.ArrayList;

/**
 *
 * @author alfredo
 */
public class PlayerDefault extends PlayerAbstract{

    @Override
    public boolean doesPlayerHit(String dealerUpCard) {
        return true;
    }

    @Override
    public int placeBet(ArrayList<String> playedCards) {
        return 1;
    }

    @Override
    public int placeBetInitial() {
        return 1;
    }

    @Override
    public boolean quit() {
        return true;
    }
    
}
