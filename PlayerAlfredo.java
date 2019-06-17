package big_project;

import java.util.ArrayList;

public class PlayerAlfredo extends PlayerAbstract {
    
    public boolean doesPlayerHit(String dealerUpCard) {
        boolean decision=false;
        int mypoints = BlackjackRules.countPoints(hand);
        int dealerpoints = BlackjackRules.getCardPoints(dealerUpCard);
        //boolean dealer_hits = BlackjackRules.doesDealerHit(dealerUpCard);
        int face_cards = 0;
        boolean caution = (this.bank<=50);
        for(String i : hand){
            if(i == "J" ||i == "Q" ||i == "K" ||i == "A"){face_cards++;}
        }
        if(mypoints>=21){decision = false;}
        else if(mypoints >= 16 && face_cards > 0 &&caution){decision = false;}
        else if(mypoints <= 16 && dealerpoints <=10 && caution){ decision = false;}
        else{decision = true;}
        
        return decision;
    }
    
    @Override
    public int placeBetInitial() {
        bet = 20;
        bank -= bet;
        return 20;
    }
    
    public int placeBet(ArrayList<String> playedCards) {
        int amount = 0;
        boolean caution = (this.bank<=50);
        if(caution){amount = 0;}
        else{amount = 10;}
        bet = amount;
        bank -= bet;
        return amount;
    }

    public boolean quit() {
        boolean out = false;
        if(bank <= 20){out = true;}
        return out;
    }
}