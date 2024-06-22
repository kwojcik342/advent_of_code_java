import java.util.HashMap;
import java.util.HashSet;

public class ObservatoryImage {
    private int galaxiesCount;
    private int imageMaxX;
    private int imageMaxY;
    private HashMap<Integer, Integer[]> galaxies;
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
            sb.append(this.galaxies.get(g)[0]);
            sb.append(", ");
            sb.append(this.galaxies.get(g)[1]);
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

            this.galaxies.put(this.galaxiesCount, new Integer[]{galaxyIdx, this.imageMaxY});

            prevIdx = galaxyIdx + 1;
        }
    }

    public void expandSpace(){
        //each row or column that had no galaxy in it should be counted twice
        //this method adjusts coordinates accordingly

        for(int g : this.galaxies.keySet()){
            Integer[] newCoordinates = new Integer[2];
            newCoordinates[0] = this.galaxies.get(g)[0];
            newCoordinates[1] = this.galaxies.get(g)[1];

            //System.out.println("key = " + g +": (" + newCoordinates[0] + ", " + newCoordinates[1] + ")");

            for(int x : this.colsWithoutGalaxy){
                if (x < this.galaxies.get(g)[0]) {
                    newCoordinates[0]++;
                } else {
                    break;
                }
            }

            for(int y : this.rowsWithoutGalaxy){
                if (y < this.galaxies.get(g)[1]) {
                    newCoordinates[1]++;
                } else {
                    break;
                }
            }

            this.galaxies.put(g, newCoordinates);
        }

        this.imageMaxX += this.colsWithoutGalaxy.size();
        this.imageMaxY += this.rowsWithoutGalaxy.size();
    }

    public long sumShortestPathsManhattan(){
        //solution to part 1
        long sum = 0;

        for(int g1 : this.galaxies.keySet()){
            for(int g2 : this.galaxies.keySet()){
                if (g1 < g2) {
                    Integer[] g1Coordinates = this.galaxies.get(g1);
                    Integer[] g2Coordinates = this.galaxies.get(g2);

                    int distance = (Math.abs(g1Coordinates[0] - g2Coordinates[0]) + Math.abs(g1Coordinates[1] - g2Coordinates[1]));

                    //System.out.println(g1 + " -> " + g2 + " = " + distance);

                    sum += distance;
                }
            }
        }

        return sum;
    }
}
