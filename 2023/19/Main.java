import logic.PartSortingSystem;

public class Main {

    public static void main(String[] args) {
        PartSortingSystem pss = new PartSortingSystem("input\\input.txt");
        //pss.printAll();
        pss.calculateAcceptedParts();
        pss.getCombinations();
    }
}