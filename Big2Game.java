import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Big2Game {
    private final List<Player> players = new ArrayList<>();
    private HandPattern topPlay = null;
    private Player topPlayer;
    private int currentPlayerIndex = 0;
    private final WhatPattern patternChain;

    public Big2Game(List<String> playerNames, WhatPattern patternChain, List<Card> predefinedDeck) {
        this.patternChain = patternChain;
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        dealCards(predefinedDeck);
        findStartingPlayer(); // Set the starting player based on C[3]
    }

    private void dealCards(List<Card> predefinedDeck) {
        int playerCount = players.size();
        for (int i = 0; i < predefinedDeck.size(); i++) { // Start from the top of the deck
            players.get(i % playerCount).receiveCard(predefinedDeck.get(i));
        }
        for (Player player : players) {
            player.sortHand(); // Sort each player's hand after dealing
        }
    }

    private void findStartingPlayer() {
        Card startingCard = new Card(Card.Suit.CLUBS, Card.Rank.THREE);
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).hasCard(startingCard)) {
                currentPlayerIndex = i;
                break;
            }
        }
    }

    public void playGame(List<String> actions) {
        System.out.println("新的回合開始了。");
        int actionIndex = 0;
        int passCount = 0;
        boolean isNewRound = true;

        while (true) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("輪到" + currentPlayer.getName() + "了");

            // Display player's hand
            displayPlayerHand(currentPlayer);

            // Get the action for the current player
            if (actionIndex >= actions.size()) break;
            String action = actions.get(actionIndex++);
            if (action.equals("-1")) {
                if (isNewRound) {
                    System.out.println("你不能在新的回合中喊 PASS");
                    actionIndex--; // Retry the same player's action
                    continue;
                }
                System.out.println("玩家 " + currentPlayer.getName() + " PASS.");
                passCount++;
            } else {
                // Parse the cards to play
                String[] cardIndices = action.split(" ");
                List<Card> playedCards = new ArrayList<>();
                for (String index : cardIndices) {
                    playedCards.add(currentPlayer.getHand().get(Integer.parseInt(index)));
                }

                // Attempt to play the cards
                if (playCards(currentPlayer, playedCards)) {
                    System.out.println("玩家 " + currentPlayer.getName() + " 打出了 " + describePlay(playedCards));
                    passCount = 0; // Reset pass count on a valid play
                    isNewRound = false; // Not a new round anymore
                } else {
                    System.out.println("此牌型不合法，請再嘗試一次。");
                    actionIndex--; // Retry the same player's action
                    continue;
                }
            }

            // Check if the player has won
            if (currentPlayer.hasNoCards()) {
                System.out.println("遊戲結束，遊戲的勝利者為 " + currentPlayer.getName());
                break;
            }

            // Check if the round should reset
            if (passCount >= players.size() - 1) {
                System.out.println("新的回合開始了。");
                topPlay = null; // Reset the top play
                passCount = 0;
                isNewRound = true;
            }

            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private void displayPlayerHand(Player player) {
        List<Card> hand = player.getHand();
    
        // Display indices
        for (int i = 0; i < hand.size(); i++) {
            System.out.printf("%-5d", i); // Left-align indices with a width of 5
        }
        System.out.println();
    
        // Display cards
        for (Card card : hand) {
            System.out.printf("%-5s", formatCard(card)); // Left-align cards with a width of 5
        }
        System.out.println();
    }
    
    // Helper method to format a card as <Suit>[<Rank>]
    private String formatCard(Card card) {
        String suit;
        switch (card.getSuit()) {
            case CLUBS:
                suit = "C";
                break;
            case DIAMONDS:
                suit = "D";
                break;
            case HEARTS:
                suit = "H";
                break;
            case SPADES:
                suit = "S";
                break;
            default:
                throw new IllegalArgumentException("Invalid suit: " + card.getSuit());
        }
    
        String rank;
        switch (card.getRank()) {
            case THREE:
                rank = "3";
                break;
            case FOUR:
                rank = "4";
                break;
            case FIVE:
                rank = "5";
                break;
            case SIX:
                rank = "6";
                break;
            case SEVEN:
                rank = "7";
                break;
            case EIGHT:
                rank = "8";
                break;
            case NINE:
                rank = "9";
                break;
            case TEN:
                rank = "10";
                break;
            case JACK:
                rank = "J";
                break;
            case QUEEN:
                rank = "Q";
                break;
            case KING:
                rank = "K";
                break;
            case ACE:
                rank = "A";
                break;
            case TWO:
                rank = "2";
                break;
            default:
                throw new IllegalArgumentException("Invalid rank: " + card.getRank());
        }
    
        return suit + "[" + rank + "]";
    }

    private String describePlay(List<Card> playedCards) {
        if (playedCards.size() == 1) {
            return "單張 " + playedCards.get(0);
        } else if (playedCards.size() == 2) {
            return "對子 " + playedCards.get(0) + " " + playedCards.get(1);
        } else if (playedCards.size() == 5) {
            return "順子 " + playedCards;
        }
        // Add more descriptions for other patterns if needed
        return "牌組 " + playedCards;
    }

    private HandPattern evaluateHand(List<Card> playedCards) {
        playedCards.sort(Comparator.naturalOrder()); // Ensure cards are sorted
        return patternChain.checkPattern(playedCards);
    }

    public boolean playCards(Player player, List<Card> playedCards) {
        HandPattern newPattern = evaluateHand(playedCards);
        if (newPattern.getType() == HandPattern.Type.INVALID) return false;
        if (topPlay == null || isStrongerThan(newPattern, topPlay)) {
            player.getHand().removeAll(playedCards);
            topPlay = newPattern;
            topPlayer = player;
            return true;
        }
        return false;
    }

    private boolean isStrongerThan(HandPattern newPattern, HandPattern topPattern) {
        if (newPattern.getType() == topPattern.getType()) {
            return newPattern.getHighestCard().compareTo(topPattern.getHighestCard()) > 0;
        }
        return false;
    }
}