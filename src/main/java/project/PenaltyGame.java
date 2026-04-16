package project;

import java.util.Random;

public class PenaltyGame {
    private Player player;
    private Random random = new Random();

    public PenaltyGame(String name) {
        player = new Player(name);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayerName(String name) {
        player.setName(name);
    }

    public boolean isGameOver(){
        return player.getShotsTaken() >= 5; // Spillet er over etter 5 skudd
    }

    public ShotResult takeShot(String shotDirection) {
        String keeperDirection = randomDirection();

        boolean goal = !shotDirection.equals(keeperDirection); // Mål hvis skuddretningen er forskjellig fra keeperens retning
        player.registerShot(goal); // Registrerer skuddet for spilleren
        return new ShotResult(keeperDirection, goal);
    }

    private String randomDirection(){
        String[] dirs = {
                Direction.UP_LEFT,
                Direction.UP_RIGHT,
                Direction.DOWN_LEFT,
                Direction.DOWN_RIGHT    
        };
        return dirs[random.nextInt(dirs.length)]; // Returnerer en tilfeldig retning for keeperen
    }

    public void resetGame() {
        player.reset(); // Nullstiller spillerens statistikk for et nytt spill
    }

    public void loadGame(String name, int shotsTaken, int goalsScored) {
        player = new Player(name); // Oppretter en ny spiller med det angitte navnet
        player.loadStats(shotsTaken, goalsScored); // Laster inn statistikken 
    }
}
