import java.nio.file.Paths;
import java.util.Scanner; 

public class Main {

    public static void main(String[] args) {

        DesertMaps maps = new DesertMaps();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            int lineIdx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.isBlank()) {
                    if (lineIdx == 1) {
                        lineIdx++;
                        maps.addInstructions(line);
                    }

                    if (lineIdx != 1 && line.contains("=")) {
                        maps.addNode(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("Steps count = " + maps.getStepsCount());
        System.out.println("Steps count multi = " + maps.getStepsCountMulti());
    }
}