package big_project;

import java.util.ArrayList;

public abstract class PlayerAbstract {
    String name;
    int bank;
    ArrayList<String> hand; 
    int bet;

    public abstract boolean doesPlayerHit(String dealerUpCard);
    
    public abstract int placeBet(ArrayList<String> playedCards);

    public abstract int placeBetInitial();

    //Used at the beginning of every round to check if a player is going to quit the game
    public abstract boolean quit();
    
    public void setBank(int newBank){ bank = newBank;}
    
    public int getBank(){return bank;}

    public String getName() {
        return name;
    }

    //Used to add a card to the player's hand
    public void takeCard(String card) {
        hand.add(card);
    }

    public ArrayList<String> getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clear();
    }


}
