import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ObservatoryImage oi = new ObservatoryImage();
        try (Scanner sc = new Scanner(Paths.get("input\\input.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if(!line.isBlank()){
                    oi.readImageLine(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("solution = " + oi.sumShortestPathsManhattan());
        
    }
}