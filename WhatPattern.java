import java.util.List;

abstract class WhatPattern {
    protected WhatPattern next;

    public void setNext(WhatPattern next) {
        this.next = next;
    }

    public HandPattern checkPattern(List<Card> playedCards) {
        if (isPattern(playedCards)) {
            return createHandPattern(playedCards);
        } else if (next != null) {
            return next.checkPattern(playedCards);
        }
        return new HandPattern(HandPattern.Type.INVALID, null);
    }

    protected abstract boolean isPattern(List<Card> playedCards);
    protected abstract HandPattern createHandPattern(List<Card> playedCards);
}