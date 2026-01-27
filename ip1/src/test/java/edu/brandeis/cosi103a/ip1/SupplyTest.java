package edu.brandeis.cosi103a.ip1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Supply class in Automation: The Game.
 * 
 * Tests verify:
 * - Cards can be purchased and counts are decremented
 * - isGameOver() correctly detects when Framework cards are exhausted
 * - Cards are available as expected
 */
public class SupplyTest {
    
    private Supply supply;
    
    /**
     * Set up a fresh Supply before each test.
     */
    @BeforeEach
    public void setUp() {
        supply = new Supply();
    }
    
    /**
     * Test that buying a card reduces its available count.
     * 
     * After purchasing a card, isAvailable() should still return true
     * if there are more copies, or false if it was the last one.
     */
    @Test
    public void testBuyingCardReducesCount() {
        // Verify Bitcoin is available before purchase
        assertTrue(supply.isAvailable("Bitcoin"), 
                  "Bitcoin should be available initially");
        
        // Buy a Bitcoin card
        Card boughtCard = supply.buyCard("Bitcoin");
        
        // Verify a card was returned
        assertNotNull(boughtCard, "buyCard should return a card");
        assertEquals("Bitcoin", boughtCard.getName(), 
                    "Bought card should be Bitcoin");
        
        // Bitcoin should still be available (60 initial - 1 = 59)
        assertTrue(supply.isAvailable("Bitcoin"), 
                  "Bitcoin should still be available after one purchase");
    }
    
    /**
     * Test that isGameOver() returns false initially.
     * 
     * With 8 Framework cards in supply, game should not be over.
     */
    @Test
    public void testGameNotOverInitially() {
        assertFalse(supply.isGameOver(), 
                   "Game should not be over when Framework cards remain");
    }
    
    /**
     * Test that isGameOver() returns true when all Framework cards are exhausted.
     * 
     * After buying all 8 Framework cards, isGameOver() should return true.
     */
    @Test
    public void testGameOverWhenFrameworkExhausted() {
        // Buy all 8 Framework cards
        for (int i = 0; i < 8; i++) {
            assertTrue(supply.isAvailable("Framework"), 
                      "Framework should be available before purchase " + (i + 1));
            Card card = supply.buyCard("Framework");
            assertNotNull(card, "Should successfully buy Framework card " + (i + 1));
        }
        
        // Verify game is now over
        assertTrue(supply.isGameOver(), 
                  "Game should be over when all Framework cards are purchased");
        
        // Verify Framework is no longer available
        assertFalse(supply.isAvailable("Framework"), 
                   "Framework should not be available after all are purchased");
    }
    
    /**
     * Test that buying an unavailable card returns null.
     * 
     * After exhausting a card type, attempting to buy it should return null.
     */
    @Test
    public void testBuyingUnavailableCardReturnsNull() {
        // Buy all 60 Bitcoin cards
        for (int i = 0; i < 60; i++) {
            supply.buyCard("Bitcoin");
        }
        
        // Bitcoin should no longer be available
        assertFalse(supply.isAvailable("Bitcoin"), 
                   "Bitcoin should not be available after all purchased");
        
        // Attempting to buy should return null
        Card result = supply.buyCard("Bitcoin");
        assertNull(result, "buyCard should return null for unavailable cards");
    }
    
    /**
     * Test that getCardCost() returns correct costs.
     * 
     * Verify that each card type returns the expected cost.
     */
    @Test
    public void testGetCardCostReturnsCorrectValues() {
        // Test automation cards
        assertEquals(2, supply.getCardCost("Method"), 
                    "Method should cost 2");
        assertEquals(5, supply.getCardCost("Module"), 
                    "Module should cost 5");
        assertEquals(8, supply.getCardCost("Framework"), 
                    "Framework should cost 8");
        
        // Test cryptocurrency cards
        assertEquals(0, supply.getCardCost("Bitcoin"), 
                    "Bitcoin should cost 0");
        assertEquals(3, supply.getCardCost("Ethereum"), 
                    "Ethereum should cost 3");
        assertEquals(6, supply.getCardCost("Dogecoin"), 
                    "Dogecoin should cost 6");
    }
    
    /**
     * Test that isAvailable() correctly reflects card availability.
     * 
     * Verify that cards are initially available and become unavailable
     * after being exhausted.
     */
    @Test
    public void testIsAvailableAccuracy() {
        // All cards should be available initially
        assertTrue(supply.isAvailable("Method"), "Method should be available");
        assertTrue(supply.isAvailable("Module"), "Module should be available");
        assertTrue(supply.isAvailable("Framework"), "Framework should be available");
        assertTrue(supply.isAvailable("Bitcoin"), "Bitcoin should be available");
        assertTrue(supply.isAvailable("Ethereum"), "Ethereum should be available");
        assertTrue(supply.isAvailable("Dogecoin"), "Dogecoin should be available");
        
        // Buy all Method cards (14 total)
        for (int i = 0; i < 14; i++) {
            supply.buyCard("Method");
        }
        
        // Method should no longer be available
        assertFalse(supply.isAvailable("Method"), 
                   "Method should not be available after exhausted");
        
        // Other cards should still be available
        assertTrue(supply.isAvailable("Module"), "Module should still be available");
        assertTrue(supply.isAvailable("Bitcoin"), "Bitcoin should still be available");
    }
}

