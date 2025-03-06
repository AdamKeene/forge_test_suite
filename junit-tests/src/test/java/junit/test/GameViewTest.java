package junit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import forge.game.Game;
import forge.game.GameRules;
import forge.game.GameType;
import forge.game.Match;
import forge.trackable.TrackableProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class GameViewTest {

    private TestableGameView gameView;

    @BeforeAll
    public static void setUpOnce() throws Exception {
        // Create a dummy ResourceBundle that simply returns the key as value.
        ResourceBundle dummyBundle = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return key; // or any dummy value you'd prefer
            }
            @Override
            public Enumeration<String> getKeys() {
                return Collections.enumeration(Collections.emptyList());
            }
        };

        // Use reflection to access the resourceBundle field in Localizer
        Class<?> localizerClass = Class.forName("forge.util.Localizer");


        Field rbField = localizerClass.getDeclaredField("resourceBundle");
        rbField.setAccessible(true);

        // Check if the field is static.
        if (Modifier.isStatic(rbField.getModifiers())) {
            // Field is static, so set it using null for the instance.
            rbField.set(null, dummyBundle);
        } else {
            // Field is not static. Obtain an instance from Localizer.
            // For example, if Localizer provides a getInstance() method:
            Method getInstanceMethod = localizerClass.getMethod("getInstance");
            Object localizerInstance = getInstanceMethod.invoke(null);
            rbField.set(localizerInstance, dummyBundle);
        }

        // Set the englishBundle field
        Field englishBundleField = localizerClass.getDeclaredField("englishBundle");
        englishBundleField.setAccessible(true);
        if (Modifier.isStatic(englishBundleField.getModifiers())) {
            englishBundleField.set(null, dummyBundle);
        } else {
            // If not static, obtain an instance (if available)
            Method getInstanceMethod = localizerClass.getMethod("getInstance");
            Object localizerInstance = getInstanceMethod.invoke(null);
            englishBundleField.set(localizerInstance, dummyBundle);
        }
    }

    @BeforeEach
    public void setUp() {
        // Create mocks for Game and Match
        Game gameMock = mock(Game.class);
        GameRules rulesMock = mock(GameRules.class);
        Match matchMock = mock(Match.class);

        // Setup mock behaviors
        when(rulesMock.hasCommander()).thenReturn(true);
        when(rulesMock.getGameType()).thenReturn(GameType.Constructed);
        when(rulesMock.getPoisonCountersToLose()).thenReturn(10);
        when(rulesMock.getGamesPerMatch()).thenReturn(1);

        // Stub behavior for game and match mocks
        when(gameMock.getMatch()).thenReturn(matchMock);
        when(gameMock.getRules()).thenReturn(rulesMock);
        when(matchMock.getTitle()).thenReturn("Test Match Title");
//        when(gameMock.getGameLog()).thenReturn(new GameLog());

        // Create a real instance of GameView using the mocked Game.
        gameView = new TestableGameView(gameMock);

        // Simulate the property that holds the title
        gameView.publicSet(TrackableProperty.Title, matchMock.getTitle());
    }

    @Test
    public void testGetTitle() {
        // Verify that getTitle() returns the title set during construction.
        assertEquals("Test Match Title", gameView.getTitle());
    }

    @Test
    public void testGetGameType() {
        // Verifies that getGameType() returns the value set up from GameRules.
        assertEquals(GameType.Constructed, gameView.getGameType());
    }

    @Test
    public void testGetNumGamesInMatch() {
        // Verifies that getNumGamesInMatch() returns the correct number.
        assertEquals(1, gameView.getNumGamesInMatch());
    }
}
