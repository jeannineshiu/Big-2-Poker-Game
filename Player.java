import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public void sortHand() {
        hand.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));
    }

    public List<Card> getHand() { return hand; }
    public String getName() { return name; }
    
    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    // Add the hasCard method to check if the player's hand contains a specific card
    public boolean hasCard(Card card) {
        return hand.contains(card);
    }
}
