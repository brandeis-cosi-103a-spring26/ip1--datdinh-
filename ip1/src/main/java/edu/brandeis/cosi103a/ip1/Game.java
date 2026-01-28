package edu.brandeis.cosi103a.ip1;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the overall game logic for Automation: The Game.
 * 
 * The game involves two automated players competing to accumulate
 * automation points by purchasing and playing cards.
 * The game ends when all Framework cards are purchased.
 */
public class Game {
    
    private static final int MAX_TURNS = 1000;
    
    private Supply supply;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player otherPlayer;
    
    /**
     * Constructs a Game and initializes all game state.
     * 
     * - Creates supply with all cards
     * - Creates two players with starter decks
     * - Randomly selects the starting player
     */
    public Game() {
        this.supply = new Supply();
        this.player1 = new Player(createStarterDeck());
        this.player2 = new Player(createStarterDeck());
        
        // Randomly select starting player
        if (new Random().nextBoolean()) {
            this.currentPlayer = player1;
            this.otherPlayer = player2;
        } else {
            this.currentPlayer = player2;
            this.otherPlayer = player1;
        }
    }
    
    /**
     * Creates a starter deck of 10 cards: 7 Bitcoin + 3 Method.
     * 
     * @return a list of cards for the starter deck
     */
    private List<Card> createStarterDeck() {
        List<Card> deck = new ArrayList<>();
        
        // Add 7 Bitcoin cards
        for (int i = 0; i < 7; i++) {
            deck.add(new Card("Bitcoin", 0, 1, true));
        }
        
        // Add 3 Method cards
        for (int i = 0; i < 3; i++) {
            deck.add(new Card("Method", 2, 1, false));
        }
        
        return deck;
    }
    
    /**
     * Plays one complete turn for the current player.
     * 
     * Turn sequence:
     * 1. Play all cryptocurrency cards to get money
     * 2. Buy the most expensive affordable card (goes directly to discard pile)
     * 3. Cleanup (discard hand and draw new hand)
     * 4. Switch to the other player
     */
    private void playTurn() {
        // Step 1: Play cryptos to get money
        int money = currentPlayer.playCryptos();
        
        // Step 2: Buy the most expensive affordable card
        Card boughtCard = buyMostExpensiveAffordable(money);
        
        // Step 3: Bought cards go directly to discard pile
        if (boughtCard != null) {
            currentPlayer.addToDiscardPile(boughtCard);
        }
        
        // Step 4: Cleanup - discard hand and draw new hand
        currentPlayer.cleanup();
        currentPlayer.drawHand();
        
        // Step 5: Switch players
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }
    
    /**
     * Finds and buys the most expensive card from supply that the player can afford.
     * 
     * Checks cards in order of typical cost (highest to lowest):
     * Framework (8), Dogecoin (6), Module (5), Ethereum (3), Method (2), Bitcoin (0)
     * 
     * @param budget the amount of money the player has
     * @return the purchased card, or null if no card is affordable
     */
    private Card buyMostExpensiveAffordable(int budget) {
        String[] cardOrder = {"Framework", "Dogecoin", "Module", "Ethereum", "Method", "Bitcoin"};
        
        for (String cardName : cardOrder) {
            if (supply.isAvailable(cardName)) {
                int cost = supply.getCardCost(cardName);
                if (cost >= 0 && cost <= budget) {
                    return supply.buyCard(cardName);
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if the game is over.
     * Game ends when all Framework cards have been purchased.
     * 
     * @return true if the game is over, false otherwise
     */
    private boolean isGameOver() {
        return supply.isGameOver();
    }
    
    /**
     * Determines the winner based on automation points.
     * 
     * @return the player with the highest automation points
     */
    private Player getWinner() {
        int points1 = player1.getTotalAutomationPoints();
        int points2 = player2.getTotalAutomationPoints();
        
        return points1 >= points2 ? player1 : player2;
    }
    
    /**
     * Plays the complete game from start to finish.
     * Continues until all Framework cards are purchased or max turns reached.
     * 
     * @return the winning player
     */
    public Player playGame() {
        int turnCount = 0;
        while (!isGameOver() && turnCount < MAX_TURNS) {
            playTurn();
            turnCount++;
        }
        
        return getWinner();
    }
    
    /**
     * Gets player 1 (for game result display).
     * @return player 1
     */
    public Player getPlayer1() {
        return player1;
    }
    
    /**
     * Gets player 2 (for game result display).
     * @return player 2
     */
    public Player getPlayer2() {
        return player2;
    }
    
    /**
     * Main method to run the game.
     */
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   Automation: The Game Start    ");
        System.out.println("=================================\n");
        
        Game game = new Game();
        Player winner = game.playGame();
        
        System.out.println("\n=================================");
        System.out.println("           GAME OVER!            ");
        System.out.println("=================================");
        System.out.println("\nFinal Results:");
        System.out.println("Player 1 Automation Points: " + game.getPlayer1().getTotalAutomationPoints());
        System.out.println("Player 2 Automation Points: " + game.getPlayer2().getTotalAutomationPoints());
        System.out.println("\n🎉 Winner: " + (winner == game.getPlayer1() ? "Player 1" : "Player 2") + " 🎉");
        System.out.println("=================================\n");
    }
}

