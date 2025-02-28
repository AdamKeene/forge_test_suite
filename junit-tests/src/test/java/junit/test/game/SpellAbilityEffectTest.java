package junit.test.game;

import forge.GameCommand;
import forge.game.ability.SpellAbilityEffect;
import forge.game.ability.effects.TokenEffectBase;
import forge.game.card.Card;
import forge.game.spellability.SpellAbility;
import forge.game.trigger.Trigger;
import forge.game.trigger.TriggerHandler;
import org.junit.jupiter.api.Test;

import static forge.game.ability.SpellAbilityEffect.*;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito.*;

import forge.GuiDesktop;
import forge.ai.AIOption;
import forge.ai.LobbyPlayerAi;
import forge.deck.Deck;
import forge.game.Game;
import forge.game.GameRules;
import forge.game.GameStage;
import forge.game.GameType;
import forge.game.Match;
import forge.game.player.RegisteredPlayer;
import forge.game.player.Player;
import forge.gui.GuiBase;
import forge.localinstance.properties.ForgePreferences.FPref;
import forge.model.FModel;

class SpellAbilityEffectTest {
    private static boolean initialized = false;
    private Game game;
    private Player player;

    @BeforeAll
    static void initialize() {
        if (!initialized) {
            GuiBase.setInterface(new GuiDesktop());
            FModel.initialize(null, preferences -> {
                preferences.setPref(FPref.LOAD_CARD_SCRIPTS_LAZILY, false);
                preferences.setPref(FPref.UI_LANGUAGE, "en-US");
                return null;
            });
            initialized = true;
        }
    }

    @BeforeEach
    void setUp() {
        game = resetGame();
        player = game.getPlayers().get(0);
    }

    private Game resetGame() {
        List<RegisteredPlayer> players = new ArrayList<>();
        Deck deck = new Deck();

        // Create AI players
        Set<AIOption> options = new HashSet<>();
        options.add(AIOption.USE_SIMULATION);
        players.add(new RegisteredPlayer(deck).setPlayer(new LobbyPlayerAi("p1", options)));
        players.add(new RegisteredPlayer(deck).setPlayer(new LobbyPlayerAi("p2", null)));

        // Set up game rules and match
        GameRules rules = new GameRules(GameType.Constructed);
        Match match = new Match(rules, players, "Test");

        // Create the game
        Game game = new Game(players, rules, match);
        game.setAge(GameStage.Play);
        game.EXPERIMENTAL_RESTORE_SNAPSHOT = false;
        game.AI_TIMEOUT = FModel.getPreferences().getPrefInt(FPref.MATCH_AI_TIMEOUT);
        game.AI_CAN_USE_TIMEOUT = true;

        return game;
    }

    @Test
    void testSpellAbilityEffect() {
    }

    @Test
    void testaddPumpUntil() {
        SpellAbility sa = mock(SpellAbility.class);
        Card card = mock(Card.class);
        when(sa.hasParam("PumpDuration")).thenReturn(true);
//        when(sa.getParam("PumpDuration")).thenReturn("UntilEndOfTurn");
        when(sa.getHostCard()).thenReturn(card);
        when(card.getGame()).thenReturn(game);

        //Until is a GameCommand, addUntil adds once, isn't a while loop
        //addPumpUntil calls addUntil with untilEOT, on cleanup or endofturn depending on PumpDuration
        TokenEffectBase.addPumpUntil(sa, card, 123L);
        assertNotNull(game.getCleanup());
        assertSame(game., game.getCleanup());

        when(sa.hasParam("PumpDuration")).thenReturn(false);
        TokenEffectBase.addPumpUntil(sa, card, 123L);
        assertNotNull(game.getEndOfTurn());

    }
}