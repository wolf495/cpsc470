package big_project;

import java.util.ArrayList;


public class PlayerBella extends PlayerAbstract {
    
    public boolean doesPlayerHit(String dealerUpCard) {
        return true;
    }
    
    public int placeBet(ArrayList<String> playedCards) {
        //bet = 10;
        return 0;
    }

    public int placeBetInitial() {
        return 0;
    }

    public boolean quit() {
        return false;
    }
    
}
