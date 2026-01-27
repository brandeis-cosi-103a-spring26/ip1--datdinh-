package edu.brandeis.cosi103a.ip1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a player in Automation: The Game.
 * 
 * Each player manages:
 * - Draw pile: Cards available to draw
 * - Hand: Cards currently in play
 * - Discard pile: Cards that have been used
 * 
 * A player starts with a deck of 10 cards (7 Bitcoin + 3 Method)
 * and draws 5 cards as their initial hand.
 */
public class Player {
    
    private List<Card> drawPile;
    private List<Card> hand;
    private List<Card> discardPile;
    
    /**
     * Constructs a Player with a starter deck of cards.
     * Shuffles the draw pile and draws an initial hand of 5 cards.
     *
     * @param starterCards the initial deck (should contain 7 Bitcoin + 3 Method cards)
     */
    public Player(List<Card> starterCards) {
        this.drawPile = new ArrayList<>(starterCards);
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        
        // Shuffle the draw pile
        Collections.shuffle(drawPile);
        
        // Draw initial hand of 5 cards
        drawHand(5);
    }
    
    /**
     * Draws a specified number of cards into the player's hand.
     * If the draw pile is empty, reshuffles the discard pile into the draw pile.
     * 
     * This is called with 5 at the start of each turn.
     *
     * @param numCards the number of cards to draw
     */
    private void drawHand(int numCards) {
        for (int i = 0; i < numCards; i++) {
            // If draw pile is empty, reshuffle discard pile
            if (drawPile.isEmpty()) {
                if (discardPile.isEmpty()) {
                    // No cards left in either pile
                    break;
                }
                drawPile.addAll(discardPile);
                discardPile.clear();
                Collections.shuffle(drawPile);
            }
            
            // Draw a card if available
            if (!drawPile.isEmpty()) {
                hand.add(drawPile.remove(0));
            }
        }
    }
    
    /**
     * Public method to draw hand at the start of a turn (5 cards).
     */
    public void drawHand() {
        drawHand(5);
    }
    
    /**
     * Plays all cryptocurrency cards in hand.
     * Removes played cards from hand and returns their total value.
     *
     * @return the sum of values of all cryptocurrency cards in hand
     */
    public int playCryptos() {
        int totalValue = 0;
        Iterator<Card> iterator = hand.iterator();
        
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.isCrypto()) {
                totalValue += card.getValue();
                iterator.remove();
            }
        }
        
        return totalValue;
    }
    
    /**
     * Performs cleanup at the end of a turn.
     * Moves all cards in hand to the discard pile.
     */
    public void cleanup() {
        discardPile.addAll(hand);
        hand.clear();
    }
    
    /**
     * Calculates the total automation points from all automation cards in hand.
     * Automation cards are non-crypto cards.
     *
     * @return the sum of values of all automation cards in hand
     */
    public int getTotalAutomationPoints() {
        int totalPoints = 0;
        for (Card card : hand) {
            if (!card.isCrypto()) {
                totalPoints += card.getValue();
            }
        }
        return totalPoints;
    }
    
    /**
     * Gets the current hand of cards.
     * Useful for testing and game state inspection.
     *
     * @return a copy of the hand list
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    
    /**
     * Gets the number of cards in the draw pile.
     * Useful for testing and game state inspection.
     *
     * @return the size of the draw pile
     */
    public int getDrawPileSize() {
        return drawPile.size();
    }
    
    /**
     * Gets the number of cards in the discard pile.
     * Useful for testing and game state inspection.
     *
     * @return the size of the discard pile
     */
    public int getDiscardPileSize() {
        return discardPile.size();
    }
}

