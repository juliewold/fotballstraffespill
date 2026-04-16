package project;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

public class ProjectController { // Kontrollerklassen for JavaFX-applikasjonen
    // FXML-komponenter som er koblet til i App.fxml
    @FXML private ImageView goalImage;
    @FXML private ImageView keeperImage;
    @FXML private ImageView ballImage;

    @FXML private Label statusLabel;
    @FXML private Label scoreLabel;
    @FXML private Label bestScoreLabel;

    @FXML private TextField nameField;
    @FXML private TextArea leaderboardArea; 

    // Instanser av spillet og filhåndtereren
    private PenaltyGame game; 
    private GameFileHandler fileHandler; 

    private static final String SAVE_FILE = "game.txt"; // Filnavn for lagring av spilldata

    @FXML
    public void initialize() { // Initialiserer spillet og filhåndtereren, og setter opp bilder og UI
        game = new PenaltyGame("Spiller");
        fileHandler = new GameFileHandler();

        goalImage.setImage(
            new Image(getClass().getResource("/project/images/goal.png").toString())
        );

        ballImage.setImage(
            new Image(getClass().getResource("/project/images/ball.png").toString())
        );

        setKeeperCenter();
        resetBall();
    }

    private void setKeeperCenter() { // Setter keeperens bilde til midtposisjon
        keeperImage.setImage(
            new Image(getClass().getResource("/project/images/keeper_center.png").toString())
        );
        // Setter keeperens posisjon til midten av målet
        keeperImage.setLayoutX(400);
        keeperImage.setLayoutY(300);
    }

    private void resetBall() { // Setter ballens posisjon tilbake til startposisjonen
        ballImage.setLayoutX(430); 
        ballImage.setLayoutY(510);
    }

    private void shoot(String dir) { // Håndterer skudd, oppdaterer keeperens bilde, statuslabel og ballens posisjon
        if (game.isGameOver()) { // Sjekker om spillet er over før skuddet tas
            statusLabel.setText("Spillet er ferdig!");
            return;
        }
        ShotResult result = game.takeShot(dir); // Tar skuddet og får resultatet
        moveKeeper(result.getKeeperDirection()); // Flytter keeperens bilde basert på keeperens retning

        if (result.isGoal()) { // Oppdaterer statuslabel basert på om det ble mål eller ikke
            statusLabel.setText("Mål!");
        } else {
            statusLabel.setText("Keeper reddet!");
        }

        moveBall(dir);
        updateUI();
    }

    private void updateUI() { // Oppdaterer scorelabel med antall mål og skudd
        scoreLabel.setText(
            game.getPlayer().getGoalsScored() + " / " +
            game.getPlayer().getShotsTaken()
        );
    }

    private void updateKeeperImage(String dir) { // Oppdaterer keeperens bilde basert på keeperens retning
        String path = ""; // Variabel for å holde stien til keeperens bilde
        
        switch (dir) { 
            case Direction.UP_LEFT:
                path = "/project/images/keeper_up_left.png";
                break;
            case Direction.UP_RIGHT:
                path = "/project/images/keeper_up_right.png";
                break;
            case Direction.DOWN_LEFT:
                path = "/project/images/keeper_down_left.png";
                break;
            case Direction.DOWN_RIGHT:
                path = "/project/images/keeper_down_right.png";
                break;
        }

        keeperImage.setImage(new Image(getClass().getResource(path).toString())); // Setter keeperens bilde basert på den valgte retningen
    }

    private void moveKeeper(String dir) { // Flytter keeperens bilde til en posisjon basert på keeperens retning
        switch (dir) {
            case Direction.UP_LEFT:
                keeperImage.setLayoutX(280);
                keeperImage.setLayoutY(300);
                break;
            case Direction.UP_RIGHT:
                keeperImage.setLayoutX(450);
                keeperImage.setLayoutY(300);
                break;
            case Direction.DOWN_LEFT:
                keeperImage.setLayoutX(280);
                keeperImage.setLayoutY(370);
                break;
            case Direction.DOWN_RIGHT:
                keeperImage.setLayoutX(450);
                keeperImage.setLayoutY(370);
                break;
        }

        updateKeeperImage(dir); 
    }

    private void moveBall(String dir) { // Flytter ballen til en posisjon basert på skuddretningen
        switch (dir) { 
            case Direction.UP_LEFT: 
                ballImage.setLayoutX(280);
                ballImage.setLayoutY(300);
                break;
            case Direction.UP_RIGHT:
                ballImage.setLayoutX(570);
                ballImage.setLayoutY(300);
                break;
            case Direction.DOWN_LEFT:
                ballImage.setLayoutX(280);
                ballImage.setLayoutY(400);
                break;
            case Direction.DOWN_RIGHT:
                ballImage.setLayoutX(570);
                ballImage.setLayoutY(400);
                break;
        }
    }

    // Håndterer knappetrykk for hver skuddretning og reset-knappen
    @FXML
    private void handleUL() {
        shoot(Direction.UP_LEFT);
    }

    @FXML
    private void handleUR() {
        shoot(Direction.UP_RIGHT);
    }

    @FXML
    private void handleDL() {
        shoot(Direction.DOWN_LEFT);
    }

    @FXML
    private void handleDR() {
        shoot(Direction.DOWN_RIGHT);
    }

    @FXML
    private void handleReset() { // Resetter spillet, oppdaterer statuslabel, setter keeper og ball til startposisjon, og oppdaterer UI
        game.resetGame();
        statusLabel.setText("Nytt spill!");
        setKeeperCenter();
        resetBall();
        updateUI();
    }

    private boolean updatePlayerNameFromField() { // Henter navnet fra nameField, validerer det og oppdaterer spillerens navn i spillet
        String name = nameField.getText(); // Henter navnet fra tekstfeltet

        if (name == null || name.isBlank()) { // Sjekker om navnet er null eller tomt
            statusLabel.setText("Skriv inn et spillernavn."); // Oppdaterer statuslabel hvis navnet er ugyldig
            return false; // Returnerer false for å indikere at navnet ikke ble oppdatert
        }

        try { 
            game.setPlayerName(name);
            return true; // Returnerer true for å indikere at navnet ble oppdatert
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Ugyldig navn.");
            return false; // Returnerer false for å indikere at navnet ikke ble oppdatert 
        }
        
    }

    // Håndterer lagring av spillet, oppdaterer statuslabel, best score og leaderboard
    @FXML
    private void handleSave() { 
        if (!updatePlayerNameFromField()) { // Oppdaterer spillerens navn fra tekstfeltet og sjekker om det var gyldig før lagring
            return;
        }
        try { 
            fileHandler.saveGame(game, SAVE_FILE); // Lagrer spillet ved å bruke GameFileHandler, og oppdaterer statuslabel, best score og leaderboard
            statusLabel.setText("Spillet ble lagret.");
            updateBestScore();
            updateLeaderboard();
        } catch (IOException e) { 
            statusLabel.setText("Kunne ikke lagre fil."); // Oppdaterer statuslabel hvis det oppsto en feil under lagring
        }
    }

    @FXML
    private void handleLoad() { 
        try {
            fileHandler.loadGame(game, SAVE_FILE);
            nameField.setText(game.getPlayer().getName()); 
            statusLabel.setText("Siste spill ble lastet inn."); // Oppdaterer statuslabel, setter keeper og ball til startposisjon, og oppdaterer UI etter innlastning av spillet
            setKeeperCenter();
            resetBall();
            updateUI();
            updateBestScore();
            updateLeaderboard();
        } catch (IOException | IllegalArgumentException e) { 
            statusLabel.setText("Kunne ikke laste fil."); // Oppdaterer statuslabel hvis det oppsto en feil under innlastning av spillet
        }
    }

    private void updateBestScore() {
        try {
            int best = fileHandler.getBestScore(SAVE_FILE);
            bestScoreLabel.setText("Beste score: " + best); // Oppdaterer bestScoreLabel med den beste scoren funnet i filen
        } catch (IOException e) {
            bestScoreLabel.setText("Beste score: -"); // Oppdaterer bestScoreLabel til en standardverdi hvis det oppsto en feil under lesing av filen
        }
    }

    private void updateLeaderboard() {
        try {
            List<String> lines = fileHandler.getLeaderboardLines(SAVE_FILE); // Henter formaterte linjer for leaderboardet fra filen og oppdaterer leaderboardArea
            
            if (lines.isEmpty()) { // Sjekker om det ikke finnes noen lagrede spill i filen
                leaderboardArea.setText("Ingen lagrede spill ennå."); 
            }

            StringBuilder text = new StringBuilder(); // Lager en StringBuilder for å bygge opp teksten som skal vises i leaderboardArea
            text.append("Leaderboard:\n"); // Legger til en overskrift for leaderboardet

            for (String line : lines) { // Går gjennom hver linje i leaderboardet og legger den til i teksten som skal vises
                text.append(line).append("\n");
            }

            leaderboardArea.setText(text.toString()); 
        } catch (IOException e) { 
            leaderboardArea.setText("Ingen lagrede spill ennå.");
        }
    }
}
