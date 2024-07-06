import logic.Contraption;

public class Main {

    public static void main(String[] args) {
        Contraption c = new Contraption("input\\input.txt");
        //c.printLayout();
        //System.out.println();
        c.countEnergizedTiles();
    }
}