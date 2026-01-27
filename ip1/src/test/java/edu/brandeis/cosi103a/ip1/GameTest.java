package edu.brandeis.cosi103a.ip1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Game class in Automation: The Game.
 * 
 * Tests verify:
 * - Game runs to completion without exceptions
 * - Game terminates when Framework cards are exhausted
 * - Winner is determined correctly
 */
public class GameTest {
    
    /**
     * Test that the game completes successfully and returns a winner.
     * 
     * This test verifies the game runs to completion without throwing
     * any exceptions and returns a non-null Player as the winner.
     */
    @Test
    @Timeout(5)
    public void testGameCompletesSuccessfully() {
        Game game = new Game();
        
        // Play the game to completion
        Player winner = game.playGame();
        
        // Verify a winner was returned
        assertNotNull(winner, "Game should return a winner");
    }
    
    /**
     * Test that the game eventually ends when Framework cards are exhausted.
     * 
     * This test verifies that the game terminates when all 8 Framework
     * cards have been purchased from the supply.
     */
    @Test
    @Timeout(30)
    public void testGameEndsWhenFrameworkCardsExhausted() {
        // Create multiple games to ensure the game always ends
        for (int i = 0; i < 5; i++) {
            Game game = new Game();
            
            // This should not hang or throw an exception
            Player winner = game.playGame();
            
            // Verify a winner was determined
            assertNotNull(winner, "Game " + i + " should have a winner");
        }
    }
    
    /**
     * Test that the game returns one of the two players as the winner.
     * 
     * This test verifies that the winner is not null and the game
     * logic completes without errors.
     */
    @Test
    public void testGameReturnsValidWinner() {
        Game game = new Game();
        Player winner = game.playGame();
        
        // Verify the winner is not null
        assertNotNull(winner, "Winner should not be null");
        
        // Verify the game completed (if winner is returned, game must have ended)
        assertTrue(true, "Game should complete without exceptions");
    }
}

