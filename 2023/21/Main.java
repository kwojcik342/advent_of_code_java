import logic.StepCounter;

public class Main {

    public static void main(String[] args) {
        StepCounter s = new StepCounter("input\\input.txt");
        s.calculateReachablePlots();

    }
}