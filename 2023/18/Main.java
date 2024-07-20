import logic.DigPlan;

public class Main {

    public static void main(String[] args) {
        DigPlan dp = new DigPlan("input\\input.txt");
        dp.getArea(false); // part 1
        dp.getArea(true); // part 2
    }
}