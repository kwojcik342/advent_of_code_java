public class Main {

    public static void main(String[] args) {
        Platform p = new Platform("input.txt");
        //p.printPlatform();
        //System.out.println();
        System.out.println("result for part 1 = " + p.getLoadTilt());
        System.out.println("result for part 2 = " + p.getLoadCycles());
        
    }
}