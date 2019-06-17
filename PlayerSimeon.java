package big_project;

import java.util.ArrayList;

import big_project.BlackjackRules;

public class PlayerSimeon extends PlayerAbstract {
    
    public boolean doesPlayerHit(String dealerUpCard) {
        if(BlackjackRules.countPoints(hand) <= 14) {
            return true;
        }
        return false;
    }
    
    public int placeBet(ArrayList<String> playedCards) {
        if(bank <= bankStart * .1) {
            bet = bank;
        } else if(bank <= bankStart *.25) {
            bet = bank * .75;
        } else if(bank <= bankStart *.5) {
            bet = bank *.5;
        } else if(bank <= bankStart * .75) {
            bet = bank * .25;
        } else {
            bet = bank * .1;
        }
        bank -= bet;
        return bet;
    }

    public int placeBetInitial() {
        return bank * 0.1;
    }

    public boolean quit() {
        if(bank >= bankStart * 1.5) {
            return true;
        }
        return false;
    }
    
}
