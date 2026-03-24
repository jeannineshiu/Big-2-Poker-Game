import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test/straight.in"));
            List<String> playerNames = new ArrayList<>();
            List<Card> deckCards = new ArrayList<>();
            List<String> actions = new ArrayList<>();

            // Read deck cards
            String[] cardStrings = reader.readLine().split(" ");
            for (String cardString : cardStrings) {
                char suitChar = cardString.charAt(0);
                String rankString = cardString.substring(2, cardString.length() - 1);

                // Map suitChar to Card.Suit enum
                Card.Suit suit;
                switch (suitChar) {
                    case 'S':
                        suit = Card.Suit.SPADES;
                        break;
                    case 'H':
                        suit = Card.Suit.HEARTS;
                        break;
                    case 'D':
                        suit = Card.Suit.DIAMONDS;
                        break;
                    case 'C':
                        suit = Card.Suit.CLUBS;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid suit: " + suitChar);
                }

                // Map rankString to Card.Rank enum
                Card.Rank rank;
                switch (rankString) {
                    case "3":
                        rank = Card.Rank.THREE;
                        break;
                    case "4":
                        rank = Card.Rank.FOUR;
                        break;
                    case "5":
                        rank = Card.Rank.FIVE;
                        break;
                    case "6":
                        rank = Card.Rank.SIX;
                        break;
                    case "7":
                        rank = Card.Rank.SEVEN;
                        break;
                    case "8":
                        rank = Card.Rank.EIGHT;
                        break;
                    case "9":
                        rank = Card.Rank.NINE;
                        break;
                    case "10":
                        rank = Card.Rank.TEN;
                        break;
                    case "J":
                        rank = Card.Rank.JACK;
                        break;
                    case "Q":
                        rank = Card.Rank.QUEEN;
                        break;
                    case "K":
                        rank = Card.Rank.KING;
                        break;
                    case "A":
                        rank = Card.Rank.ACE;
                        break;
                    case "2":
                        rank = Card.Rank.TWO;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid rank: " + rankString);
                }

                deckCards.add(new Card(suit, rank));
            }

            // Read player names
            for (int i = 0; i < 4; i++) {
                playerNames.add(reader.readLine());
            }

            // Read actions
            String line;
            while ((line = reader.readLine()) != null) {
                actions.add(line);
            }

            // Initialize responsibility chain
            WhatPattern single = new SinglePattern();
            WhatPattern pair = new PairPattern();
            WhatPattern straight = new StraightPattern();
            WhatPattern fullHouse = new FullHousePattern();

            fullHouse.setNext(straight);
            straight.setNext(pair);
            pair.setNext(single);

            // Initialize game
            Big2Game game = new Big2Game(playerNames, single, deckCards);

            // Redirect output to file
            PrintStream fileOut = new PrintStream(new FileOutputStream("straight.out"));
            System.setOut(fileOut);

            // Play the game
            game.playGame(actions);

            // Close resources
            reader.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}