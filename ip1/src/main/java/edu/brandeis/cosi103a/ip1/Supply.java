package edu.brandeis.cosi103a.ip1;


import java.util.HashMap;
import java.util.Map;

/**
 * Manages the supply of cards available for purchase in Automation: The Game.
 * 
 * The supply includes automation and cryptocurrency cards with specific
 * quantities and properties. Players can check availability and purchase cards,
 * which decrements the supply count.
 */
public class Supply {
    
    // Store card definitions: name -> {cost, value, isCrypto}
    private static class CardDefinition {
        int cost;
        int value;
        boolean isCrypto;
        
        CardDefinition(int cost, int value, boolean isCrypto) {
            this.cost = cost;
            this.value = value;
            this.isCrypto = isCrypto;
        }
    }
    
    private Map<String, Integer> cardCounts;
    private Map<String, CardDefinition> cardDefinitions;
    
    /**
     * Constructs a Supply with initial quantities of all cards.
     * 
     * Automation cards: Method (14), Module (8), Framework (8)
     * Cryptocurrency cards: Bitcoin (60), Ethereum (40), Dogecoin (30)
     */
    public Supply() {
        this.cardCounts = new HashMap<>();
        this.cardDefinitions = new HashMap<>();
        
        // Initialize automation cards
        addCardDefinition("Method", 2, 1, false);
        addCardDefinition("Module", 5, 3, false);
        addCardDefinition("Framework", 8, 6, false);
        
        // Initialize cryptocurrency cards
        addCardDefinition("Bitcoin", 0, 1, true);
        addCardDefinition("Ethereum", 3, 2, true);
        addCardDefinition("Dogecoin", 6, 3, true);
        
        // Set initial quantities
        cardCounts.put("Method", 14);
        cardCounts.put("Module", 8);
        cardCounts.put("Framework", 8);
        cardCounts.put("Bitcoin", 60);
        cardCounts.put("Ethereum", 40);
        cardCounts.put("Dogecoin", 30);
    }
    
    /**
     * Helper method to add a card definition.
     *
     * @param name the card name
     * @param cost the cost of the card
     * @param value the value of the card
     * @param isCrypto true if this is a cryptocurrency card
     */
    private void addCardDefinition(String name, int cost, int value, boolean isCrypto) {
        cardDefinitions.put(name, new CardDefinition(cost, value, isCrypto));
    }
    
    /**
     * Checks if a card is available in the supply.
     *
     * @param cardName the name of the card to check
     * @return true if the card is available, false otherwise
     */
    public boolean isAvailable(String cardName) {
        return cardCounts.containsKey(cardName) && cardCounts.get(cardName) > 0;
    }
    
    /**
     * Purchases a card from the supply and returns it.
     * Decrements the card count by 1.
     *
     * @param cardName the name of the card to purchase
     * @return a new Card object, or null if the card is not available
     */
    public Card buyCard(String cardName) {
        if (!isAvailable(cardName)) {
            return null;
        }
        
        CardDefinition def = cardDefinitions.get(cardName);
        Card card = new Card(cardName, def.cost, def.value, def.isCrypto);
        
        cardCounts.put(cardName, cardCounts.get(cardName) - 1);
        
        return card;
    }
    
    /**
     * Gets the cost of a card by name.
     *
     * @param cardName the name of the card
     * @return the cost of the card, or -1 if the card doesn't exist
     */
    public int getCardCost(String cardName) {
        CardDefinition def = cardDefinitions.get(cardName);
        return def != null ? def.cost : -1;
    }
    
    /**
     * Checks if the game is over.
     * The game ends when all Framework cards have been purchased.
     *
     * @return true if no Framework cards remain, false otherwise
     */
    public boolean isGameOver() {
        return cardCounts.get("Framework") <= 0;
    }
}
