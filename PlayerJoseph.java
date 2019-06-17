package big_project;

import java.util.ArrayList;


public class PlayerJoseph extends PlayerAbstract {
    
    public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {
        return true;
    }
    
    public int placeBet(ArrayList<String> playedCards) {
        return 0;
    }

    public int placeBetInitial() {
        
    }

    public boolean quit() {
        return false;
    }
}
