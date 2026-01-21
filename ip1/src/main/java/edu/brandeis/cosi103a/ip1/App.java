package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * Dice Game - 2 Player Turn-Based Game
 * Players roll a die, decide whether to re-roll (max 2 times),
 * and accumulate points. First to play 10 turns wins based on highest score.
 */
public class App 
{
    private static final int TURNS_PER_PLAYER = 10;
    private static final int MAX_REROLLS = 2;
    private static final int DIE_SIDES = 6;
    
    private static class Player {
        String name;
        int score;
        int turnsPlayed;
        
        Player(String name) {
            this.name = name;
            this.score = 0;
            this.turnsPlayed = 0;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("=================================");
        System.out.println("    Welcome to the Dice Game!    ");
        System.out.println("=================================\n");
        
        // Get player names
        System.out.print("Enter Player 1 name: ");
        String player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }
        
        System.out.print("Enter Player 2 name: ");
        String player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }
        
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        
        System.out.println("\n=================================");
        System.out.println("Game Rules:");
        System.out.println("- Each player gets " + TURNS_PER_PLAYER + " turns");
        System.out.println("- On each turn, a " + DIE_SIDES + "-sided die is rolled");
        System.out.println("- You can re-roll up to " + MAX_REROLLS + " times");
        System.out.println("- After your turn, the die value is added to your score");
        System.out.println("- Player with the highest score wins!");
        System.out.println("=================================\n");
        
        // Play the game
        boolean gameActive = true;
        while (gameActive) {
            // Player 1's turn
            if (player1.turnsPlayed < TURNS_PER_PLAYER) {
                playTurn(player1, scanner, random);
            }
            
            // Check if game is over
            if (player1.turnsPlayed >= TURNS_PER_PLAYER && player2.turnsPlayed >= TURNS_PER_PLAYER) {
                gameActive = false;
                break;
            }
            
            // Player 2's turn
            if (player2.turnsPlayed < TURNS_PER_PLAYER) {
                playTurn(player2, scanner, random);
            }
            
            // Check if game is over
            if (player1.turnsPlayed >= TURNS_PER_PLAYER && player2.turnsPlayed >= TURNS_PER_PLAYER) {
                gameActive = false;
                break;
            }
        }
        
        // Display final results
        displayFinalResults(player1, player2);
        
        scanner.close();
    }
    
    private static void playTurn(Player player, Scanner scanner, Random random) {
        System.out.println("\n--- " + player.name + "'s Turn (Turn " + (player.turnsPlayed + 1) + " of " + TURNS_PER_PLAYER + ") ---");
        System.out.println("Current Score: " + player.score);
        
        int currentDieValue = rollDie(random);
        int rerollsUsed = 0;
        boolean turnEnded = false;
        
        while (!turnEnded && rerollsUsed <= MAX_REROLLS) {
            System.out.println("\nDie Roll: " + currentDieValue);
            
            if (rerollsUsed < MAX_REROLLS) {
                System.out.print("Do you want to (R)e-roll or (E)nd turn? [R/E]: ");
                String choice = scanner.nextLine().trim().toUpperCase();
                
                if (choice.equals("R")) {
                    currentDieValue = rollDie(random);
                    rerollsUsed++;
                    System.out.println("You re-rolled! Re-rolls used: " + rerollsUsed + "/" + MAX_REROLLS);
                } else if (choice.equals("E")) {
                    turnEnded = true;
                } else {
                    System.out.println("Invalid input. Please enter 'R' or 'E'.");
                }
            } else {
                System.out.println("Maximum re-rolls reached! Your turn ends.");
                turnEnded = true;
            }
        }
        
        // Add the final die value to the player's score
        player.score += currentDieValue;
        player.turnsPlayed++;
        
        System.out.println("Turn Ended! Final die value: " + currentDieValue);
        System.out.println(player.name + "'s Total Score: " + player.score);
    }
    
    private static int rollDie(Random random) {
        return random.nextInt(DIE_SIDES) + 1;
    }
    
    private static void displayFinalResults(Player player1, Player player2) {
        System.out.println("\n=================================");
        System.out.println("           GAME OVER!            ");
        System.out.println("=================================");
        System.out.println("\nFinal Scores:");
        System.out.println(player1.name + ": " + player1.score + " points");
        System.out.println(player2.name + ": " + player2.score + " points");
        
        System.out.println("\n=================================");
        if (player1.score > player2.score) {
            System.out.println("🎉 " + player1.name + " WINS! 🎉");
        } else if (player2.score > player1.score) {
            System.out.println("🎉 " + player2.name + " WINS! 🎉");
        } else {
            System.out.println("It's a TIE! Both players have " + player1.score + " points!");
        }
        System.out.println("=================================\n");
    }
}
