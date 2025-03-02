package junit.test.event;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import forge.game.card.Card;
import forge.game.player.Player;
import forge.game.event.GameEventAnteCardsSelected;
import forge.game.event.IGameEventVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameEventAnteCardsSelectedTest {

    private Multimap<Player, Card> cards;
    private Player player;
    private Card card;

    @BeforeEach
    public void setUp() {
        cards = ArrayListMultimap.create();
        player = mock(Player.class);
        card = mock(Card.class);
        cards.put(player, card);
    }

    @Test
    public void testConstructor() {
        GameEventAnteCardsSelected event = new GameEventAnteCardsSelected(cards);
        assertEquals(cards, event.cards);
    }

    @Test
    public void testVisit() {
        GameEventAnteCardsSelected event = new GameEventAnteCardsSelected(cards);
        IGameEventVisitor<String> visitor = mock(IGameEventVisitor.class);
        when(visitor.visit(event)).thenReturn("visited");

        String result = event.visit(visitor);
        assertEquals("visited", result);
        verify(visitor).visit(event);
    }
}