package edu.brandeis.cosi103a.ip1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the Player class in Automation: The Game.
 * 
 * Tests verify:
 * - Initial hand size is 5 cards
 * - playCryptos() correctly sums cryptocurrency card values
 * - cleanup() properly discards the hand
 * - getTotalAutomationPoints() correctly sums automation cards
 */
public class PlayerTest {
    
    /**
     * Helper method to create a standard starter deck.
     * Creates 7 Bitcoin cards and 3 Method cards.
     * 
     * @return a starter deck for testing
     */
    private List<Card> createStarterDeck() {
        List<Card> deck = new ArrayList<>();
        
        // Add 7 Bitcoin cards (cost 0, value 1, isCrypto true)
        for (int i = 0; i < 7; i++) {
            deck.add(new Card("Bitcoin", 0, 1, true));
        }
        
        // Add 3 Method cards (cost 2, value 1, isCrypto false)
        for (int i = 0; i < 3; i++) {
            deck.add(new Card("Method", 2, 1, false));
        }
        
        return deck;
    }
    
    /**
     * Test that a player draws an initial hand of 5 cards.
     * 
     * The Player constructor should draw 5 cards from the shuffled
     * starter deck into the player's hand.
     */
    @Test
    public void testInitialHandSize() {
        List<Card> starterDeck = createStarterDeck();
        Player player = new Player(starterDeck);
        
        // Verify hand has exactly 5 cards
        assertEquals(5, player.getHand().size(), 
                     "Player should start with 5 cards in hand");
    }
    
    /**
     * Test that playCryptos() returns the correct total value.
     * 
     * playCryptos() should sum the values of all cryptocurrency cards
     * in the hand and remove them from the hand.
     */
    @Test
    public void testPlayCryptosReturnsCorrectValue() {
        List<Card> starterDeck = createStarterDeck();
        Player player = new Player(starterDeck);
        
        // The initial hand should contain 5 cards from the starter deck
        // which is 7 Bitcoin and 3 Method, so the hand will have some mix
        // All Bitcoin cards are crypto with value 1
        
        // Play all cryptos
        int cryptoValue = player.playCryptos();
        
        // Crypto value should be >= 0 (depends on hand composition after shuffle)
        assertTrue(cryptoValue >= 0, "Crypto value should be non-negative");
        
        // After playing cryptos, remaining hand should only have non-crypto cards
        List<Card> remainingHand = player.getHand();
        for (Card card : remainingHand) {
            assertFalse(card.isCrypto(), 
                       "Remaining hand should not contain crypto cards");
        }
    }
    
    /**
     * Test that cleanup() discards the hand correctly.
     * 
     * cleanup() should move all cards from hand to discard pile,
     * leaving the hand empty.
     */
    @Test
    public void testCleanupDiscardsHand() {
        List<Card> starterDeck = createStarterDeck();
        Player player = new Player(starterDeck);
        
        // Verify hand is not empty before cleanup
        assertTrue(player.getHand().size() > 0, 
                  "Hand should have cards before cleanup");
        
        // Perform cleanup
        player.cleanup();
        
        // Verify hand is now empty
        assertEquals(0, player.getHand().size(), 
                    "Hand should be empty after cleanup");
        
        // Verify discard pile is not empty
        assertTrue(player.getDiscardPileSize() > 0, 
                  "Discard pile should contain cards after cleanup");
    }
    
    /**
     * Test that getTotalAutomationPoints() returns correct value.
     * 
     * getTotalAutomationPoints() should sum the values of all
     * non-crypto (automation) cards in the hand.
     */
    @Test
    public void testGetTotalAutomationPoints() {
        List<Card> starterDeck = createStarterDeck();
        Player player = new Player(starterDeck);
        
        // Get total automation points (non-crypto cards)
        int automationPoints = player.getTotalAutomationPoints();
        
        // Automation points should be >= 0
        assertTrue(automationPoints >= 0, 
                  "Automation points should be non-negative");
        
        // All non-crypto cards in hand should be Method cards with value 1
        List<Card> hand = player.getHand();
        int expectedPoints = 0;
        for (Card card : hand) {
            if (!card.isCrypto()) {
                expectedPoints += card.getValue();
            }
        }
        
        assertEquals(expectedPoints, automationPoints, 
                    "Automation points should match sum of non-crypto card values");
    }
    
    /**
     * Test that drawHand() adds cards to the hand.
     * 
     * After cleanup, calling drawHand() should refill the hand with 5 cards.
     */
    @Test
    public void testDrawHandRefillsHand() {
        List<Card> starterDeck = createStarterDeck();
        Player player = new Player(starterDeck);
        
        // Clean up the initial hand
        player.cleanup();
        assertEquals(0, player.getHand().size(), 
                    "Hand should be empty after cleanup");
        
        // Draw a new hand
        player.drawHand();
        
        // Verify hand has 5 cards again
        assertEquals(5, player.getHand().size(), 
                    "Hand should have 5 cards after drawHand()");
    }
}

