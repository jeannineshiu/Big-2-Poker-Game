class Card implements Comparable<Card> {
    enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    enum Rank { THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE, TWO }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    
    @Override
    public int compareTo(Card other) {
        if (this.rank != other.rank) {
            return this.rank.ordinal() - other.rank.ordinal();
        }
        return this.suit.ordinal() - other.suit.ordinal();
    }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}