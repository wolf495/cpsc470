package big_project;

import java.util.ArrayList;

public abstract class PlayerAbstract {
    String name;
    int bank;
    int bankStart;
    ArrayList<String> hand;
    int bet;
    boolean hasBlackJack;

    public abstract boolean doesPlayerHit(String dealerUpCard);

    public abstract int placeBet(ArrayList<String> playedCards);

    public abstract int placeBetInitial();

    //Used at the beginning of every round to check if a player is going to quit the game
    public abstract boolean quit();

    public void setBank(int newBank){ this.bank = newBank;}

    public int getBank(){return this.bank;}

    public String getName() {
        return this.name;
    }

    //Used to add a card to the player's hand
    public void takeCard(String card) {
        hand.add(card);
    }

    public ArrayList<String> getHand() {
        return this.hand;
    }

    public int getBet() {
        return this.bet;
    }

    public void setBet(int newBet) {
        this.bet = newBet;
    }

    public void setBankStart(int bank) {
        this.bank = bank;
        this.bankStart = bank;
    }

    public void setBlackjack(boolean hasBlackJack) {
      this.hasBlackJack = hasBlackJack;
    }

    public boolean gethasBlackjack() {
      return hasBlackJack;
    }

}
