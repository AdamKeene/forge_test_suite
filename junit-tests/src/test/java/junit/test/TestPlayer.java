package junit.test;

import forge.game.Game;
import forge.game.player.Player;

public class TestPlayer extends Player {
    // Override only the methods you need for testing,
    // and provide minimal implementation to avoid static initializer issues.
    public TestPlayer(String name, Game game, int id) {
        super(name, game, id);
    }
    // Add other stubbed methods as needed.
}