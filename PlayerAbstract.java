package big_project;

public abstract class PlayerAbstract {
    int bank;
    
    public abstract boolean doesPlayerHit(String[] playerCards, String dealerUpCard);
    
    public abstract int placeBet(int bank, String[] playedCards, int numCardsLeft);
    
    public void setBank(int newBank){ bank = newBank;}
    
    public int getBank(){return bank;}
}
