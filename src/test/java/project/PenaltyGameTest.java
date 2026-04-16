package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PenaltyGameTest {
    @Test
    void newGameStartsWithZeroShotsAndZeroGoals() {
        PenaltyGame game = new PenaltyGame("Julie");
        assertEquals("Julie", game.getPlayer().getName());
        assertEquals(0, game.getPlayer().getShotsTaken());
        assertEquals(0, game.getPlayer().getGoalsScored());
        assertFalse(game.isGameOver());
    }
    
    @Test
    void takeShotIncreasesShotsTaken() {
        PenaltyGame game = new PenaltyGame("Julie");
        game.takeShot(Direction.UP_LEFT);
        assertEquals(1, game.getPlayer().getShotsTaken());
    }

    @Test
    void gameIsOverAfterFiveShots() {
        PenaltyGame game = new PenaltyGame("Julie");
        for (int i = 0; i < 5; i++) {
            game.takeShot(Direction.UP_LEFT);
        }
        assertTrue(game.isGameOver());
        assertEquals(5, game.getPlayer().getShotsTaken());
    }

    @Test
    void resetGameSetsShotsAndGoalsToZero() {
        PenaltyGame game = new PenaltyGame("Julie");
        for (int i = 0; i < 5; i++) {
            game.takeShot(Direction.UP_LEFT);
        }
        game.resetGame();
        assertEquals(0, game.getPlayer().getShotsTaken());
        assertEquals(0, game.getPlayer().getGoalsScored());
        assertFalse(game.isGameOver());
    }
}
