//package forge.game;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.*;
//import java.util.List;
//import java.util.Arrays;
//import forge.game.Game;
//import forge.game.player.RegisteredPlayer;
//import forge.deck.Deck;
//import forge.deck.DeckSection;
//import forge.game.card.Card;
//
//
//class GameTest {
//    private RegisteredPlayer player;
//
//    // Setup method to initialize player and necessary dependencies
//    @BeforeEach
//    public void setUp() {
//        // Initialize a new deck
//        Deck deck = new Deck();
//
//        // Add a section to the deck
//        DeckSection mainDeckSection = DeckSection.Main;
//
//        // Populate the deck with some cards (for example)
//        mainDeckSection.addCard(new Card("Card1"));
//        mainDeckSection.addCard(new Card("Card2"));
//        mainDeckSection.addCard(new Card("Card3"));
//        mainDeckSection.addCard(new Card("Card4"));
//
//        // Create and initialize a player with this deck
//        player = new RegisteredPlayer(deck); // Assuming the constructor requires a deck
//
//        // Draw 3 cards initially for the test
//        player.drawCards(3);
//    }
//
//    @Test
//    public void testMulliganBehavior() {
//        // Ensure the player starts with 3 cards in hand
//        assertEquals(3, player.getCardsInHand().size(), "Player should start with 3 cards in hand");
//
//        // Perform a mulligan (the player returns 2 cards and draws 2 new cards)
//        player.mulligan(2); // Assuming mulligan() is a method in your RegisteredPlayer class
//
//        // Ensure the player's hand size remains the same after mulligan (3 cards)
//        assertEquals(3, player.getCardsInHand().size(), "Player's hand size should remain 3 after mulligan");
//
//        // Ensure the 2 returned cards are back in the deck
//        Deck deck = player.getCurrentDeck();
//        assertTrue(deck.containsCard("Card1"), "Card1 should be back in the deck after mulligan");
//        assertTrue(deck.containsCard("Card2"), "Card2 should be back in the deck after mulligan");
//
//        // Ensure the hand now contains different cards
//        assertTrue(player.getCardsInHand().contains(new Card("Card3")), "Player's hand should contain Card3 after mulligan");
//        assertTrue(player.getCardsInHand().contains(new Card("Card4")), "Player's hand should contain Card4 after mulligan");
//    }
//}
