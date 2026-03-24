import java.util.List;

public class SinglePattern extends WhatPattern{
    @Override
    protected boolean isPattern(List<Card> playedCards) {
        return playedCards.size() == 1;
    }

    @Override
    protected HandPattern createHandPattern(List<Card> playedCards) {
        return new HandPattern(HandPattern.Type.SINGLE, playedCards.get(0));
    }
}
