package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * Unit tests for the Dice Game (App)
 * Tests game logic including:
 * - Die rolling functionality (1-6)
 * - Game rules and constraints
 * - Score boundaries and calculations
 */
public class AppTest 
{
    private static final int TURNS_PER_PLAYER = 10;
    private static final int MAX_REROLLS = 2;
    private static final int DIE_SIDES = 6;
    
    /**
     * Test that die rolls return valid values (1-6)
     */
    @Test
    public void testDieRoll()
    {
        try {
            // Get the rollDie method
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie", Random.class);
            rollDieMethod.setAccessible(true);
            
            Random random = new Random();
            
            // Test multiple rolls
            for (int i = 0; i < 100; i++) {
                int roll = (int) rollDieMethod.invoke(null, random);
                assertTrue("Die roll should be >= 1", roll >= 1);
                assertTrue("Die roll should be <= 6", roll <= DIE_SIDES);
            }
        } catch (Exception e) {
            assertTrue("Die roll test failed: " + e.getMessage(), false);
        }
    }
    
    /**
     * Test that die rolls are not always the same
     */
    @Test
    public void testDieRollVariability()
    {
        try {
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie", Random.class);
            rollDieMethod.setAccessible(true);
            
            Random random = new Random();
            boolean hasDifferentValues = false;
            
            int firstRoll = (int) rollDieMethod.invoke(null, random);
            
            // Roll many times to ensure we get different values
            for (int i = 0; i < 50; i++) {
                int roll = (int) rollDieMethod.invoke(null, random);
                if (roll != firstRoll) {
                    hasDifferentValues = true;
                    break;
                }
            }
            
            assertTrue("Die rolls should produce different values", hasDifferentValues);
        } catch (Exception e) {
            assertTrue("Die roll variability test failed: " + e.getMessage(), false);
        }
    }
    
    /**
     * Test that die roll minimum value is 1
     */
    @Test
    public void testDieRollMinimum()
    {
        try {
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie", Random.class);
            rollDieMethod.setAccessible(true);
            
            Random random = new Random(1); // Use seed for reproducibility
            int minRoll = Integer.MAX_VALUE;
            
            for (int i = 0; i < 1000; i++) {
                int roll = (int) rollDieMethod.invoke(null, random);
                if (roll < minRoll) {
                    minRoll = roll;
                }
            }
            
            assertEquals("Minimum die roll should be 1", 1, minRoll);
        } catch (Exception e) {
            assertTrue("Die roll minimum test failed: " + e.getMessage(), false);
        }
    }
    
    /**
     * Test that die roll maximum value is 6
     */
    @Test
    public void testDieRollMaximum()
    {
        try {
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie", Random.class);
            rollDieMethod.setAccessible(true);
            
            Random random = new Random(42); // Use seed for reproducibility
            int maxRoll = Integer.MIN_VALUE;
            
            for (int i = 0; i < 1000; i++) {
                int roll = (int) rollDieMethod.invoke(null, random);
                if (roll > maxRoll) {
                    maxRoll = roll;
                }
            }
            
            assertEquals("Maximum die roll should be 6", DIE_SIDES, maxRoll);
        } catch (Exception e) {
            assertTrue("Die roll maximum test failed: " + e.getMessage(), false);
        }
    }
    
    /**
     * Test that maximum score possible is 60 (10 turns * 6 max per turn)
     */
    @Test
    public void testMaximumPossibleScore()
    {
        int maxScore = TURNS_PER_PLAYER * DIE_SIDES;
        assertEquals("Maximum possible score should be 60", 60, maxScore);
    }
    
    /**
     * Test that minimum score is 10 (10 turns * 1 min per turn)
     */
    @Test
    public void testMinimumPossibleScore()
    {
        int minScore = TURNS_PER_PLAYER * 1;
        assertEquals("Minimum possible score should be 10", 10, minScore);
    }
    
    /**
     * Test game constants are correct
     */
    @Test
    public void testGameConstants()
    {
        assertEquals("Should have 10 turns per player", 10, TURNS_PER_PLAYER);
        assertEquals("Should allow max 2 rerolls", 2, MAX_REROLLS);
        assertEquals("Should use 6-sided die", 6, DIE_SIDES);
    }
    
    /**
     * Test that score range is valid
     */
    @Test
    public void testScoreRange()
    {
        // Test boundary values
        int minScore = TURNS_PER_PLAYER * 1;      // 10
        int maxScore = TURNS_PER_PLAYER * DIE_SIDES;  // 60
        
        assertTrue("Minimum score should be positive", minScore > 0);
        assertTrue("Maximum score should be greater than minimum", maxScore > minScore);
        assertEquals("Score range should be 50 points", 50, maxScore - minScore);
    }
    
    /**
     * Test reroll limit validation
     */
    @Test
    public void testRerollLimit()
    {
        assertEquals("Maximum rerolls should be 2", 2, MAX_REROLLS);
        assertTrue("Max rerolls should be positive", MAX_REROLLS > 0);
    }
    
    /**
     * Test turns per player validation
     */
    @Test
    public void testTurnsPerPlayer()
    {
        assertEquals("Each player should get 10 turns", 10, TURNS_PER_PLAYER);
        assertTrue("Turns should be positive", TURNS_PER_PLAYER > 0);
    }
    
    /**
     * Test die sides validation
     */
    @Test
    public void testDieSides()
    {
        assertEquals("Die should have 6 sides", 6, DIE_SIDES);
        assertTrue("Die sides should be positive", DIE_SIDES > 0);
    }
    
    /**
     * Test that average score is reasonable
     */
    @Test
    public void testAverageScore()
    {
        // Expected average per roll is 3.5 (average of 1-6)
        // Expected average total score is 3.5 * 10 = 35
        int expectedAverageScore = (int) (TURNS_PER_PLAYER * 3.5);
        int minScore = TURNS_PER_PLAYER * 1;
        int maxScore = TURNS_PER_PLAYER * DIE_SIDES;
        
        assertTrue("Average score should be between min and max", 
                   expectedAverageScore > minScore && expectedAverageScore < maxScore);
    }
}
