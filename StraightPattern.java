import java.util.Comparator;
import java.util.List;

public class StraightPattern extends WhatPattern{
    @Override
    protected boolean isPattern(List<Card> playedCards) {
        playedCards.sort(Comparator.naturalOrder());
        for (int i = 0; i < playedCards.size() - 1; i++) {
            if (playedCards.get(i + 1).getRank().ordinal() - playedCards.get(i).getRank().ordinal() != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected HandPattern createHandPattern(List<Card> playedCards) {
        return new HandPattern(HandPattern.Type.STRAIGHT, playedCards.get(playedCards.size() - 1));
    }
}
