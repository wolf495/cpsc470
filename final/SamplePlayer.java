/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package big_project;
import java.util.ArrayList;

/**
 *
 */
public class SamplePlayer extends PlayerAbstract{

	public boolean doesPlayerHit(String dealerUpCard) {

		int points = BlackjackRules.countPoints(hand);
		// implement your strategy for deciding whether to hit or not

		return false;
	}

	public int placeBet(ArrayList<String> playedCards) {
		 bet = 10;
		// change your bet amount here if you wish
				
		if (bet>bank)
			bet = bank;
		return bet;
	}

    public boolean quit() {
		return true;
        
		}
		
		public int placeBetInitial() {
                    bet = 10;
                    return 10;
		}

}
