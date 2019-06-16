/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package big_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import sun.tools.jar.resources.jar;


public class Game {

	private static Game theInstance; 
	private ArrayList<PlayerAbstract> players;
	private Factory playerFactory;
	private ArrayList<String> deck;
	private ArrayList<String> dealerHand;
	private ArrayList<String> discardPile;

    
	private Game() {
		players = new ArrayList<PlayerAbstract>();
		playerFactory = Factory.getInstance();
		deck = new ArrayList<String>();
		dealerHand = new ArrayList<String>();
	}
	
	public void Start() {
		populateDeck();
		for(PlayerAbstract p: players) {
			p.setBankStart(1000);
		}
	}

	public void Round() {
		for(PlayerAbstract p: players) {
			p.placeBetInitial();
			System.out.println(p.getName() + " makes a bet of " + p.getBet());
		}
		for(PlayerAbstract p: players) {
			dealCard(p.getHand());
			System.out.println(p.getName() + " is dealt a " + p.getHand().get(0));
		}
		dealCard(dealerHand);
		System.out.println("Dealer got a " + dealerHand.get(0));
		for(PlayerAbstract p: players) {
			dealCard(p.getHand());
			System.out.println(p.getName() + " is dealt a " + p.getHand().get(0));
		}
		dealCard(dealerHand);
		for(PlayerAbstract p: players) {
			if(checkBlackjack(p)) {
			} else {
				boolean stay = false;
				while(!stay) {
					if(p.doesPlayerHit(dealerHand.get(0))) {
						stay = false;
						dealCard(p.getHand());
						stay = checkBlackjack(p);
					} else {
						stay = true;
					}
				}
			}
		}
		while(BlackjackRules.doesDealerHit(dealerCards)) {
			dealCard(dealerHand);
		}
		for(PlayerAbstract p: players) {
			int dealerPoints = BlackjackRules.countPoints(dealerHand);
			int playerPoints = BlackjackRules.countPoints(p.getHand());
			if(dealerPoints > playerPoints) {
				p.setBet(0);
			} else if(dealerPoints == playerPoints) {
				p.setBank(p.getBank() + p.getBet());
				p.setBet(0);
			} else {
				p.setBank(p.getBank() + p.getBet() * 2);
				p.setBet(0);
			}
		}
		collectCards();
		clearHand(dealerHand);
		for(PlayerAbstract p : players) {
			if(p.quit() || p.getBank() <= 0) {
				players.remove(p);
				System.out.println(p.getName() + " has left the table");
			}
		}

	}

	public void End() {

	}
	
	public static Game getInstance() {
		if(theInstance == null) {
			theInstance = new Game();
		}
		return theInstance;
	}

	private void collectCards() {
		for(PlayerAbstract p: players) {
			for(String c : p.getHand()) {
				discardPile.add(c);
			}
		}
		Collections.shuffle(deck);
	}

	private void populateDeck() {
		String[] cards = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				deck.add(cards[i]);
			}
		}
		Collections.shuffle(deck);
	}

	public void dealCard(ArrayList<String> s) {
		if(deck.isEmpty()) {
			deck = Collections.shuffle(discardPile);
			discardPile.clear();
		}
		s.add(deck.get(0));
		deck.remove(0);
	}

	public void addPlayer(String playerName) {
		players.add(playerFactory.Create(playerName));
	}

	public String getDealerTopCard() {
		return dealerHand.get(0);
	}
	
	public boolean hasPlayers() {
		return players.size() > 0;
	}

	private boolean checkBlackjack(PlayerAbstract p) {
		if(p.getHand().contains("A") && (p.getHand().contains("K") || p.getHand().contains("Q") || p.getHand().contains("J"))) {
			p.setBank((int)(p.getBank() + p.getBet() * 2.5));
			clearHand(p.getHand());
			return true;
		}
		return false;
	}
	private void clearHand(ArrayList<String> hand) {
		for(String c : hand) {
			discardPile.add(c);
		}
		hand.clear();
	}

	public int getPlayerCount() {
		return players.size();
	}

	public ArrayList<PlayerAbstract> getPlayers() {
		return players;
	}

	public void removePlayer(PlayerAbstract player) {
		players.remove(player);
	}


    
/*
	public static void main(String[] args) {
		// create and initialize player
		SamplePlayerAbstract player1 = new SamplePlayer(); // change this to your class name like YourLastNamePlayer
		int bank1 = 100;
		
		// generate a random list of cards for a sample deck of 20
		int shoeSize = 20;
		int numCardsLeft = shoeSize;
		String[] deck = createNewDeck(shoeSize);
		String[] playedCards = new String[shoeSize];
		
		// place bets
		int bet1 = player1.placeBet(bank1, playedCards, numCardsLeft);
		System.out.println("Player Bank = " + bank1);
		System.out.println("Player bets " + bet1 + "\n==========");
		
		//deal initial cards
		String[] playerCards = createEmptyHand(5);
		String[] dealerCards = createEmptyHand(5);
		String dealerUpCard = "";
		int nextShoeIndex = 0;
		int nextPlayerHandIndex = 2;
		int nextDealerHandIndex = 2;
		playerCards[0] = deck[0];
		dealerCards[0] = deck[1];
		playerCards[1] = deck[2];
		dealerCards[1] = deck[3];
		numCardsLeft -= 4;
		dealerUpCard = dealerCards[1];
		nextShoeIndex = 4;
		
		// player's hand
		boolean doesPlayerHit = true;
		while (doesPlayerHit) {
			printCurrentHandInfo(playerCards);
			doesPlayerHit = player1.doesPlayerHit(playerCards, dealerUpCard);
			if (doesPlayerHit) {
				playerCards[nextPlayerHandIndex] = deck[nextShoeIndex];
				nextShoeIndex++;
				numCardsLeft--;
				nextPlayerHandIndex++;
				System.out.println("Player hits.");
			}
			else
				System.out.println("Player stands.");
		}
		
		// dealer's hand
		boolean doesDealerHit = true;
		while (doesDealerHit) {
			printCurrentHandInfo(dealerCards);
			doesDealerHit = BlackjackRules.doesDealerHit(dealerCards);
			if (doesDealerHit) {
				dealerCards[nextDealerHandIndex] = deck[nextShoeIndex];
				nextShoeIndex++;
				numCardsLeft--;
				nextDealerHandIndex++;
				System.out.println("Dealer hits.");
			}
			else
				System.out.println("Dealer stands.");
		}

		int dealerPoints = BlackjackRules.countPoints(dealerCards);
		int playerPoints = BlackjackRules.countPoints(playerCards);
		System.out.println("==========\nDealer has: " + dealerPoints);
		System.out.println("Player has: " + playerPoints);
		
		// figure out winner - this is not correct for all cases, but it is close enough to test with		
		if (dealerPoints > playerPoints && dealerPoints < 22 
				|| playerPoints > 21) {
			System.out.println("Player lost!");
			bank1-=bet1;
		} else if (dealerPoints == 21) {// check dealer blackjack
			if (dealerCards[2]=="0") {
				if (playerPoints == 21 && playerCards[2]=="0") {
					System.out.println("Push game!");
				}
				else {
					System.out.println("Dealer has Blackjack!!!");
					bank1-=bet1;
				}
			} else if (playerPoints == dealerPoints){
				System.out.println("Push game!");
			}
		} else if(playerPoints == 21 && playerCards[2]=="0") { // check player blackjack
			System.out.println("Player has Blackjack!!!");
			bank1+= 1.5*bet1;
		} else if (playerPoints == dealerPoints){
			System.out.println("Push game!");
		} else {
			System.out.println("Player won!");
			bank1+= bet1;
		}
		System.out.println("Player bank = " + bank1);
		
	}


	private static String[] createEmptyHand(int handSize) {
		// create empty array
		String[] hand = new String[handSize];
		for (int i=0; i<handSize; i++) {
			hand[i] = "0";
		}
		return hand;
	}


	private static String[] createNewDeck(int shoeSize) {

		String[] deck = new String[shoeSize];
		Random random = new Random();
		for (int i=0; i<shoeSize; i++) {
			int rNum = random.nextInt(13);
			switch (rNum) {
			case 0:
				deck[i] = "K";
				break;
			case 1:
				deck[i] = "A";
				break;
			case 2:
				deck[i] = "2";
				break;
			case 3:
				deck[i] = "3";
				break;
			case 4:
				deck[i] = "4";
				break;
			case 5:
				deck[i] = "5";
				break;
			case 6:
				deck[i] = "6";
				break;
			case 7:
				deck[i] = "7";
				break;
			case 8:
				deck[i] = "8";
				break;
			case 9:
				deck[i] = "9";
				break;
			case 10:
				deck[i] = "10";
				break;
			case 11:
				deck[i] = "J";
				break;
			case 12:
				deck[i] = "Q";
				break;
			}
		}
		return deck;
	}
	
	private static void printCurrentHandInfo(String[] cards) {
		
		int totalPoints = BlackjackRules.countPoints(cards);
		System.out.print("\nCurrent Hand\nCards:");
		for (String card : cards) {
			if (!card.equals("0")) {
				System.out.print(" " + card);
			}
		}
		System.out.println("\nPoints: " + totalPoints);
	}*/
}
