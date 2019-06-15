package big_project;

public class PlayerAlfredo extends PlayerAbstract {
    
    public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {
        return true;
    }
    
    public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
        return 0;
    }

    public boolean quit() {
        return false;
    }
    
}