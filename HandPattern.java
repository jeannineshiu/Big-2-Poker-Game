

public class HandPattern {

    enum Type { SINGLE, PAIR, STRAIGHT, FULL_HOUSE, INVALID }
    
    private final Type type;
    private final Card highestCard;

    public HandPattern(Type type, Card highestCard) {
        this.type = type;
        this.highestCard = highestCard;
    }

    public Type getType() { return type; }
    public Card getHighestCard() { return highestCard; }
    
}
