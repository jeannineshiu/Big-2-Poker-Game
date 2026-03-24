import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullHousePattern extends WhatPattern{
    @Override
    protected boolean isPattern(List<Card> playedCards) {
        if (playedCards.size() != 5) return false;
        Map<Card.Rank, Integer> count = new HashMap<>();
        for (Card c : playedCards) {
            count.put(c.getRank(), count.getOrDefault(c.getRank(), 0) + 1);
        }
        return count.containsValue(3) && count.containsValue(2);
    }

    @Override
    protected HandPattern createHandPattern(List<Card> playedCards) {
        return new HandPattern(HandPattern.Type.FULL_HOUSE, playedCards.get(2));
    }
    
}
