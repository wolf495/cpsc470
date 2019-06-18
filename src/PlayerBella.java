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

        return true;
    }

    public int placeBet(ArrayList<String> playedCards) {
        return 0;
    }
    //initial bet will be 15 dollars
    public int placeBetInitial() {
        return 15;
    }
    //when I want to leave the game
    public boolean quit() {
        return false;
    }

}
