package project;

public class ShotResult {
    private String keeperDirection; // Retningen keeperen gikk i
    private boolean goal; // Om det ble mål eller ikke

    public ShotResult(String keeperDirection, boolean goal) {
        this.keeperDirection = keeperDirection;
        this.goal = goal;
    }

    public String getKeeperDirection() {
        return keeperDirection;
    }

    public boolean isGoal() {
        return goal;
    }
}
