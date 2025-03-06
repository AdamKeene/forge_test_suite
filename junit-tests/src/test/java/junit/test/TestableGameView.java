package junit.test;

import forge.game.Game;
import forge.game.GameView;
import forge.trackable.TrackableProperty;

public class TestableGameView extends GameView {
    public TestableGameView(Game game) {
        super(game);
    }

    // Expose the protected set() method as public for testing purposes
    public <T> void publicSet(TrackableProperty prop, T value) {
        super.set(prop, value);
    }
}
