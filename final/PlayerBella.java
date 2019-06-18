/*
*
*@author Isabella
*/
package big_project;

import java.util.ArrayList;

public class PlayerBella extends PlayerAbstract {

    public boolean doesPlayerHit(String dealerUpCard) {
      BlackjackRules rules = new BlackjackRules();
      Game game = Game.getInstance();
        int handPoints = rules.countPoints(hand);
        if(handPoints < 10) {
          return true;
        }
        else if(handPoints < 16 && rules.countPoints(game.getDealerHand()) < 12) {
          return true;
        }
        else if(handPoints < 20) {
          return false;
        }
        else {
          return false;
        }
    }

    public int placeBet(ArrayList<String> playedCards) {
        if( bet < bank) {
          bet = (int)(bank * .25);
          return bet
        }
        else {
          return bet;
        }
    }
    //initial bet will be 15 dollars
    public int placeBetInitial() {
        bet = 15;
        return bet;
    }
    //when I want to leave the game
    public boolean quit() {
        if(bank == 100) {
          return true;
        }
        else {
          return false;
        }
    }

}
