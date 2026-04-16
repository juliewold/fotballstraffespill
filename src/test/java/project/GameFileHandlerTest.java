package project;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameFileHandlerTest {
    @Test
    void saveAndLoadGameWorks() throws IOException {
        Path tempFile = Files.createTempFile("penaltygame", ".txt");

        PenaltyGame game = new PenaltyGame("Julie");
        game.loadGame("Julie", 5, 4);

        GameFileHandler fileHandler = new GameFileHandler();
        fileHandler.saveGame(game, tempFile.toString());

        PenaltyGame loadedGame = new PenaltyGame("Test");
        fileHandler.loadGame(loadedGame, tempFile.toString());

        assertEquals("Julie", loadedGame.getPlayer().getName());
        assertEquals(5, loadedGame.getPlayer().getShotsTaken());
        assertEquals(4, loadedGame.getPlayer().getGoalsScored());
    }

    @Test
    void getBestScoreReturnsHighestGoalsScored() throws IOException {
        Path tempFile = Files.createTempFile("penaltygame", ".txt");

        Files.write(tempFile, List.of(
                "Julie;5;4",
                "Tom;5;3",
                "Anna;5;5"
        ));

        GameFileHandler fileHandler = new GameFileHandler();
        int bestScore = fileHandler.getBestScore(tempFile.toString());
        assertEquals(5, bestScore);
    }
}
