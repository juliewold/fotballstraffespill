package project;

public class Player implements Comparable<Player> { // Implementerer Comparable for å kunne sortere spillere etter mål
    private String name;
    private int shotsTaken;
    private int goalsScored;

    public Player(String name) {
        setName(name);  // Bruker setName for å validere navnet
        this.shotsTaken = 0;
        this.goalsScored = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {  // Validerer at navnet ikke er null eller tomt
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Navn kan ikke være tomt");
        }
        this.name = name.trim(); // Fjerner mellomrom før og etter navnet
    }

    public int getShotsTaken() {
        return shotsTaken;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void registerShot(boolean goal) { // Registrerer et skudd
        shotsTaken++;
        if (goal) {
            goalsScored++;
        }
    }

    public void reset() { // Nullstiller statistikken for spilleren
        shotsTaken = 0;
        goalsScored = 0;
    }

    public void loadStats(int shotsTaken, int goalsScored) {    // Laster inn statistikk for spilleren, med validering
        if (shotsTaken < 0 || goalsScored < 0 || goalsScored > shotsTaken || shotsTaken > 5) { // Sjekker at statistikken er gyldig
            throw new IllegalArgumentException("Ugyldig statistikk.");
        }
        // Setter statistikken for spilleren
        this.shotsTaken = shotsTaken;
        this.goalsScored = goalsScored;
    }

    // Implementerer compareTo for å sortere spillere etter mål
    @Override
    public int compareTo(Player other) {
        return Integer.compare(other.goalsScored, this.goalsScored);
    }
}
