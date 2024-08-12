import logic.PulsePropagation;

public class Main {

    public static void main(String[] args) {
        PulsePropagation pp = new PulsePropagation("input\\input.txt");
        pp.printModules();
        pp.propagate();
    }
}