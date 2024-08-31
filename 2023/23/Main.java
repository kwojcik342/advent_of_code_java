import logic.HikingTrails;

public class Main {

    public static void main(String[] args) {
        HikingTrails t = new HikingTrails("input\\input.txt");
        t.getLongestPath();
        t.getLongestPathNoSlopes();
    }
}