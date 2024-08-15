import logic.BrickFall;

public class Main {

    public static void main(String[] args) {
        BrickFall bf = new BrickFall("input\\input.txt");
        //bf.printBricks();
        bf.countDisintegration(); // part 1
        //bf.printBricks();
        bf.countChainReaction(); // part 2
    }
}