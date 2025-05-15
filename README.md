# Big-2 Poker Game

## Requirements Overview
You are tasked with developing a **Big-2 Poker Game**. The game is named **Big-2** because the highest card in the game is the **2**. When developing the **Big-2**, you must implement every game rule carefully and design the game modules to be **extensible**, allowing future requirements to be supported easily.

## Glossary
The owner of **Big-2** is quite familiar with **Domain-Driven Design (DDD)** concepts. He knows that establishing a common language across the development team greatly improves collaboration efficiency. To assist you, we’ve provided a glossary of terms, so you can quickly familiarize yourself with the corresponding concepts in the **Big-2 Poker** domain. You may refer back to the glossary while reading through the detailed game rules.

- **Big-2**: The **Big-2 Poker Game**, which is what we want you to develop.
- **Player**: A participant in the game, which can be either an AI player or a human player.
- **Card**: A combination of a rank (number) and a suit.
- **Rank**: The number on the card, which can be any of:
  `{3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A, 2}`.
- **Suit**: The suit of the card, which can be one of:
  `{Club (♣), Diamond (♦), Heart (♥), Spade (♠)}`.
- **Deck**: A deck of 52 different playing cards.
- **Deal**: The action where players take cards from the top of the deck.
- **Hand Cards**: The cards that a player has in hand, ranging from 0 to 13 cards.
- **Card Pattern**: A combination of cards.
- **Play**: The action of playing a legal card pattern from a player’s hand to the table.
- **Round**: A cycle in the game where players take turns to play cards, until three players consecutively pass.
- **Top Play**: The current highest card pattern on the table.
- **Top Player**: The player who played the top card pattern.

## Game Flow
The game proceeds as follows:

1. **Four Players**: After the game starts, each player will input their name. The players are indexed as {0, 1, 2, 3}.
2. **Deck Shuffling and Dealing**: The deck is shuffled, and the 52 cards are dealt to the four players until the deck is empty.
3. **First Round**:
   - The player with the **3 of Clubs** starts the first round. This player must play a valid card pattern that includes the **3 of Clubs**.
   - The played pattern becomes the **Top Play**.
   - The next player can:
     - **Pass**: Skip the turn and not play any cards.
     - **Play a Bigger Pattern**: Play a card pattern larger than the current **Top Play**. The new pattern becomes the new **Top Play**.
   - Players continue taking turns until three consecutive players **Pass**.
4. **Next Rounds**: After each round:
   - The **Top Play** is cleared, and the player who played the last **Top Play** becomes the **Top Player** for the next round.
   - The new round begins with the **Top Player**, and this player cannot pass.
5. **Game End**: The game continues until one player has played all their cards. That player is declared the **Winner**.

## Card Patterns and Ordering
In the initial version, there are four card patterns. Only the same type of card patterns can be compared:

1. **Single**: A single card.
   - **Comparison**: Compare the rank first, and if equal, compare the suit.
   - Rank: `3 < 4 < 5 < ... < 10 < J < Q < K < A < 2`
   - Suit: Club < Diamond < Heart < Spade

2. **Pair**: Two cards with the same rank, e.g., J-J, 3-3.
   - **Comparison**: Compare the larger card of the pair, e.g., A-A > 7-7.

3. **Straight**: Five consecutive cards, e.g., 3-4-5-6-7, 10-J-Q-K-A, J-Q-K-A-2, or K-A-2-3-4.
   - **Comparison**: Compare the largest card in the five-card sequence, e.g., 2-3-4-5-6 > 10-J-Q-K-A.

4. **Full House**: Five cards consisting of three cards of one rank and two cards of another rank, e.g., 3-3-3-2-2, A-A-A-7-7.
   - **Comparison**: Compare the rank of the three identical cards, e.g., A-A-A-3-3 > 5-5-5-2-2.

## Software Design Requirement: Open-Closed Principle (OCP)
Your **Big-2** program must adhere to the **Open-Closed Principle (OCP)** regarding the support for different card patterns:

> "When adding new card patterns (or removing existing ones), you should be able to extend the game without modifying the existing classes in the design."

This means that the system should allow new card patterns to be added or removed without altering the core classes that handle the game logic.
