package edu.brandeis.cosi103a.ip1;


/**
 * A simple Card class representing a card in Automation: The Game.
 * 
 * Each card has:
 * - name: The name of the card
 * - cost: The cost to play this card
 * - value: The value/benefit the card provides
 * - isCrypto: Whether this card is a crypto-related card
 */
public class Card {
    private String name;
    private int cost;
    private int value;
    private boolean isCrypto;
    
    /**
     * Constructs a Card with the specified attributes.
     *
     * @param name the name of the card
     * @param cost the cost to play this card
     * @param value the value/benefit provided by this card
     * @param isCrypto true if this is a crypto-related card, false otherwise
     */
    public Card(String name, int cost, int value, boolean isCrypto) {
        this.name = name;
        this.cost = cost;
        this.value = value;
        this.isCrypto = isCrypto;
    }
    
    /**
     * Gets the name of the card.
     *
     * @return the card's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the cost of the card.
     *
     * @return the card's cost
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Gets the value of the card.
     *
     * @return the card's value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Checks if the card is crypto-related.
     *
     * @return true if the card is crypto, false otherwise
     */
    public boolean isCrypto() {
        return isCrypto;
    }
}
