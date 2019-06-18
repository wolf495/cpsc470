/**
 * CPSC 470
 * @author Isabella
 */
package big_project;
import java.util.ArrayList;

/**
 *
 */
public class SamplePlayer extends PlayerAbstract{

	public boolean doesPlayerHit(String dealerUpCard) {

		int points = BlackjackRules.countPoints(this.getHand());
		if(points > 18) {
			return false;
		}
		else {
			return true;
		}
	}

	public int placeBet(ArrayList<String> playedCards) {
		if (bet>bank) {
			bet = (int)(bank * .5);
		}
		else {
			return 15;
		}
		return bet;
	}

    public boolean quit() {
			if(this.getBank() == 0) {
				return false;
			}
			else {
				return true;
			}
		}

		public int placeBetInitial() {
			return 10;
		}

}
