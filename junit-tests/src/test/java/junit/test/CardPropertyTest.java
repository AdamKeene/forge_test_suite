package junit.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import forge.game.card.Card;
import forge.game.card.CardProperty;
import forge.game.player.Player;
import forge.game.spellability.SpellAbility;
import forge.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardPropertyTest {

    private Card card;
    private Player sourceController;
    private Card source;

    @BeforeEach
    public void setUp() {
        // Create mocks or dummy instances for required objects.
        card = mock(Card.class);
        sourceController = mock(Player.class);
        source = mock(Card.class);
        SpellAbility spellAbility = mock(SpellAbility.class);
        Game game = mock(Game.class);

        // Set up default behaviors for your mocks:
        when(card.getGame()).thenReturn(game);
        // Simulate that card is not phased out by default:
        when(card.isPhasedOut()).thenReturn(false);
        // Default for noName property: assume card has no name
        when(card.hasNoName()).thenReturn(true);
        // You might need to set up more interactions for a full test.
    }

    @Test
    public void testNoNamePropertyTrue() {
        // For property "noName", the card must report it has no name.
        when(card.hasNoName()).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "noName", sourceController, source, null);
        assertTrue(result, "Card should have the noName property");
    }

    @Test
    public void testNoNamePropertyFalseWhenHasName() {
        when(card.hasNoName()).thenReturn(false);
        boolean result = CardProperty.cardHasProperty(card, "noName", sourceController, source, null);
        assertFalse(result, "Card should not have the noName property when it has a name");
    }

    @Test
    public void testNamedPropertyTrue() {
        // For property "namedTestCard", the method calls card.sharesNameWith("TestCard").
        when(card.sharesNameWith("TestCard")).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "namedTestCard", sourceController, source, null);
        assertTrue(result, "Card should satisfy 'namedTestCard' property");
    }

    @Test
    public void testNotNamedPropertyFalse() {
        // For property "notnamedTestCard", if the card shares the name "TestCard" it should fail.
        when(card.sharesNameWith("TestCard")).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "notnamedTestCard", sourceController, source, null);
        assertFalse(result, "Card should not satisfy 'notnamedTestCard' property when names match");
    }

    @Test
    public void testPermanentPropertyTrue() {
        when(card.isPermanent()).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "Permanent", sourceController, source, null);
        assertTrue(result, "Card should have the Permanent property when isPermanent() returns true");
    }

    @Test
    public void testPermanentPropertyFalse() {
        when(card.isPermanent()).thenReturn(false);
        boolean result = CardProperty.cardHasProperty(card, "Permanent", sourceController, source, null);
        assertFalse(result, "Card should not have the Permanent property when isPermanent() returns false");
    }

    @Test
    public void testCMCEvenTrue() {
        // Property "cmcEven" checks that card.getCMC() % 2 == 0.
        when(card.getCMC()).thenReturn(4);
        boolean result = CardProperty.cardHasProperty(card, "cmcEven", sourceController, source, null);
        assertTrue(result, "Card with CMC 4 should satisfy cmcEven property");
    }

    @Test
    public void testCMCEvenFalse() {
        when(card.getCMC()).thenReturn(3);
        boolean result = CardProperty.cardHasProperty(card, "cmcEven", sourceController, source, null);
        assertFalse(result, "Card with CMC 3 should not satisfy cmcEven property");
    }

    @Test
    public void testTappedPropertyTrue() {
        when(card.isTapped()).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "tapped", sourceController, source, null);
        assertTrue(result, "Card that is tapped should satisfy tapped property");
    }

    @Test
    public void testTappedPropertyFalse() {
        when(card.isTapped()).thenReturn(false);
        boolean result = CardProperty.cardHasProperty(card, "tapped", sourceController, source, null);
        assertFalse(result, "Card that is not tapped should not satisfy tapped property");
    }

    @Test
    public void testPhasedOutPropertyTrue() {
        // When a card is phased out, only properties starting with "phasedOut" are allowed.
        when(card.isPhasedOut()).thenReturn(true);
        // For property "phasedOutnoName", the method strips the prefix and checks hasNoName().
        when(card.hasNoName()).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "phasedOutnoName", sourceController, source, null);
        assertTrue(result, "Phased out card should satisfy 'phasedOutnoName' property if hasNoName() is true");
    }

    @Test
    public void testPhasedOutPropertyFalseWithoutPrefix() {
        // A phased-out card should not satisfy a property that does not start with "phasedOut".
        when(card.isPhasedOut()).thenReturn(true);
        when(card.hasNoName()).thenReturn(true);
        boolean result = CardProperty.cardHasProperty(card, "noName", sourceController, source, null);
        assertFalse(result, "Phased out card should not satisfy 'noName' property without the phasedOut prefix");
    }

}

