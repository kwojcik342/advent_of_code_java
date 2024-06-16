import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PipeMap pm = new PipeMap();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.isBlank()) {
                    pm.addLine(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(pm);
        //System.out.println("\n\n\n");


        // ArrayList<Tile> l = pm.getLoop();
        // for(int i = 0; i < l.size(); i ++){
        //     System.out.println(l.get(i) + "  from_start = " + i + " from_end = " + (l.size() - 1 - i));
        // }

        System.out.println("max distance = " + pm.getMaxDistanceOnLoop());
        System.out.println("\n");

        // for(Tile v : pm.getVertices()){
        //     System.out.println(v);
        // }

        // System.out.println("polygon area = " + pm.getPolygonArea());
        // System.out.println("\n");

        System.out.println("points inside polygon = " + pm.getTilesInsidePolygon());
        System.out.println("\n");
    }
}