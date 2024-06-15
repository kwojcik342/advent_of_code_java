import java.util.Arrays;
import java.util.HashMap;

public class TileSet {
    private HashMap<Character, int[][]> tileSet;
    //private char startLabel;

    public TileSet(){
        //this.startLabel = 'S';
        this.tileSet = new HashMap<>();
        //definition of tiles from exercise
        //label associated with possible coordinates change
        //for example label | connects 1 tile before {0, -1} or after {0, 1} on y axis
        this.tileSet.put('|', new int[][]{{0, -1}, {0, 1}});
        this.tileSet.put('-', new int[][]{{-1, 0}, {1, 0}});
        this.tileSet.put('L', new int[][]{{0, -1}, {1, 0}});
        this.tileSet.put('J', new int[][]{{0, -1}, {-1, 0}});
        this.tileSet.put('7', new int[][]{{0, 1}, {-1, 0}});
        this.tileSet.put('F', new int[][]{{0, 1}, {1, 0}});
    }

    public Tile getNextTile(Tile prevTile, Tile curTile){
        //returns next tile coordinates if movement is possible
        Tile nextTile = null;

        //System.out.println("trying to move " + curTile.getLabel());

        if(prevTile.isAdjacent(curTile)){
            if(this.tileSet.containsKey(curTile.getLabel())){
                int[] coordinatesDiff = curTile.diffCoordinatesToPrev(prevTile);
                int[][] labelCoorChange = this.tileSet.get(curTile.getLabel());

                //System.out.println("coordinatesDiff = (" + coordinatesDiff[0] + ", " + coordinatesDiff[1] + ")");
                //System.out.println("labelCoorChange[0] = (" + labelCoorChange[0][0] + ", " + labelCoorChange[0][1] + ")");
                //System.out.println("labelCoorChange[1] = (" + labelCoorChange[1][0] + ", " + labelCoorChange[1][1] + ")");

                    if(Arrays.equals(labelCoorChange[0], coordinatesDiff)){
                        int newX = curTile.getX() + labelCoorChange[1][0];
                        int newY = curTile.getY() + labelCoorChange[1][1];
                        nextTile = new Tile(newX, newY, 'x');
                    }

                    if(Arrays.equals(labelCoorChange[1], coordinatesDiff)){
                        int newX = curTile.getX() + labelCoorChange[0][0];
                        int newY = curTile.getY() + labelCoorChange[0][1];
                        nextTile = new Tile(newX, newY, 'x');
                    }
                //in other cases movement is not possible from prevTile to curTile 
            }
        }

        return nextTile;
    }
}
