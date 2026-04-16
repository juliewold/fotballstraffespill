package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameFileHandler {  // Håndterer filoperasjoner for spillet
    public void saveGame(PenaltyGame game, String filePath) throws IOException { // Lagrer spilldata til en fil
        if (game == null || filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Ugyldig input.");
        }

        // Lager en linje med spillerens navn, antall skudd og mål, separert med semikolon
        String line = game.getPlayer().getName() + ";" +
                game.getPlayer().getShotsTaken() + ";" +
                game.getPlayer().getGoalsScored();
        
        Path path = Path.of(filePath); // Oppretter en Path-objekt for filen
        List<String> lines = List.of(line); // Lager en liste med linjen som skal skrives til filen

        Files.write(path, lines,
            java.nio.file.StandardOpenOption.CREATE, // Oppretter filen hvis den ikke finnes
            java.nio.file.StandardOpenOption.APPEND // Legger til linjen i filen
        );
    }

    public void loadGame(PenaltyGame game, String filePath) throws IOException { // Laster inn spilldata fra filen og oppdaterer spillet
        if (game == null || filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Ugyldig input.");
        }

        List<String> lines = Files.readAllLines(Path.of(filePath)); // Leser alle
        String lastLine = lines.get(lines.size() - 1); // Henter den siste linjen i filen, som antas å være den siste lagrede spilldataen

        String[] parts = lastLine.split(";"); // Deler linjen i deler basert på semikolon, forventer formatet "navn;skudd;mål"
        
        String name = parts[0]; // Spillernes navn
        int shotsTaken = Integer.parseInt(parts[1]); // Antall skudd tatt
        int goalsScored = Integer.parseInt(parts[2]); // Antall mål scoret

        game.loadGame(name, shotsTaken, goalsScored); // Laster inn spilldataen i spillet ved å kalle loadGame-metoden i PenaltyGame-klassen
    }

    public int getBestScore(String filePath) throws IOException { // Leser alle linjene i filen og finner den høyeste målscoren
        List<String> lines = Files.readAllLines(Path.of(filePath)); // Leser alle linjene i filen
        int bestScore = 0; // Initialiserer den høyeste scoren

        for (String line : lines) { // Går gjennom hver linje i filen
            String[] parts = line.split(";"); // Deler linjen i deler 
            if (parts.length == 3) { // Sjekker at linjen har riktig format (navn;skudd;mål)
                int goalsScored = Integer.parseInt(parts[2]); // Henter ut antall mål scoret fra linjen
                if (goalsScored > bestScore) { // Oppdaterer bestScore hvis denne linjen har en høyere score enn den nåværende bestScore
                    bestScore = goalsScored;
                }
            }
        }
        return bestScore; // Returnerer den høyeste scoren funnet i filen
    }

    public List<String> getLeaderboardLines(String filePath) throws IOException { // Leser alle linjene i filen og formaterer dem for visning på leaderboardet
        List<String> lines = Files.readAllLines(Path.of(filePath)); // Leser alle linjene i filen
        List<String> result = new ArrayList<>(); // Lager en liste for å holde de formaterte linjene for leaderboardet

        for (String line : lines) { // Går gjennom hver linje i filen
            String[] parts = line.split(";"); // Deler linjen i deler

            if (parts.length == 3) { // Sjekker at linjen har riktig format (navn;skudd;mål)
                String name = parts[0]; 
                String shots = parts[1];
                String goals = parts[2];

                result.add(name + " - " + goals + "/" + shots); // Formaterer linjen for leaderboardet i formatet "navn - mål/skudd" og legger den til i resultatlisten
            }
        }
        return result; // Returnerer listen med formaterte linjer for leaderboardet
    }
}

