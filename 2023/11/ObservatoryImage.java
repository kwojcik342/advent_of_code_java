import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ObservatoryImage {
    private int galaxiesCount;
    private int imageMaxX;
    private int imageMaxY;
    private HashMap<Integer, ArrayList<Integer>> galaxies;
    private HashSet<Integer> colsWithoutGalaxy;
    private HashSet<Integer> rowsWithoutGalaxy;

    public ObservatoryImage(){
        this.galaxiesCount = 0;
        this.imageMaxX = -1;
        this.imageMaxY = -1;
        this.galaxies = new HashMap<>();
        this.rowsWithoutGalaxy = new HashSet<>();
        this.colsWithoutGalaxy = new HashSet<>();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("galaxies:\n");

        for(int g : this.galaxies.keySet()){
            sb.append(g);
            sb.append(": (");
            sb.append(this.galaxies.get(g).get(0));
            sb.append(", ");
            sb.append(this.galaxies.get(g).get(1));
            sb.append(")\n");
        }

        sb.append("imageMaxX = ");
        sb.append(this.imageMaxX);
        sb.append("\n");

        sb.append("imageMaxY = ");
        sb.append(this.imageMaxY);
        sb.append("\n");

        sb.append("rowsWithoutGalaxy: ");
        sb.append(this.rowsWithoutGalaxy);
        sb.append("\n");

        sb.append("colsWithoutGalaxy: ");
        sb.append(this.colsWithoutGalaxy);
        sb.append("\n");

        return sb.toString();
    }

    public void readImageLine(String line){
        this.imageMaxY++;

        if (this.imageMaxX == -1) {
            this.imageMaxX = line.length() - 1;
            for(int i = 0; i <= this.imageMaxX; i++){
                //create set with all possible X coordinates
                //values will be removed when galaxy appears on X coordinate coresponding to them
                this.colsWithoutGalaxy.add(i);
            }
        }

        int prevIdx = 0;
        while(true){
            int galaxyIdx = line.indexOf('#', prevIdx);

            if (galaxyIdx == -1) {
                if (prevIdx == 0) {
                    this.rowsWithoutGalaxy.add(this.imageMaxY);
                }
                break;
            }

            this.galaxiesCount++;
            this.colsWithoutGalaxy.remove(galaxyIdx);

            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(galaxyIdx);
            coordinates.add(this.imageMaxY);

            this.galaxies.put(this.galaxiesCount, new ArrayList<>(coordinates));

            prevIdx = galaxyIdx + 1;
        }
    }

    public long sumShortestPathsManhattan(){
        //solution
        long sum = 0;

        for(int g1 : this.galaxies.keySet()){
            for(int g2 : this.galaxies.keySet()){
                if (g1 < g2) {
                    int g1x = this.galaxies.get(g1).get(0);
                    int g1y = this.galaxies.get(g1).get(1);

                    int g2x = this.galaxies.get(g2).get(0);
                    int g2y = this.galaxies.get(g2).get(1);

                    int distance = (Math.abs(g1x - g2x) + Math.abs(g1y - g2y));

                    //empty spaces
                    //int additionalSpace = 1; // for part 1
                    int additionalSpace = 999999; // for part 2
                    for(int c : this.colsWithoutGalaxy){
                        if (c > Math.min(g1x, g2x) && c < Math.max(g1x, g2x)) {
                            distance += additionalSpace;
                        }
                    }

                    for(int r : this.rowsWithoutGalaxy){
                        if (r > Math.min(g1y, g2y) && r < Math.max(g1y, g2y)) {
                            distance += additionalSpace;
                        }
                    }

                    //System.out.println(g1 + " -> " + g2 + " = " + distance);

                    sum += distance;
                }
            }
        }

        return sum;
    }
}
