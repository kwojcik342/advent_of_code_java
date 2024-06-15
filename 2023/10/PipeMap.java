import java.util.ArrayList;
import java.util.HashMap;

public class PipeMap {
    private HashMap<Integer, String> pipeMap;
    private int startLine;
    private int startCol;
    private int maxLine;
    private int maxCol;
    private TileSet ts;

    public PipeMap(){
        this.pipeMap = new HashMap<>();
        this.startLine = -1;
        this.startCol = -1;
        this.maxLine = -1;
        this.maxCol = -1;
        this.ts = new TileSet();
    }

    public void addLine(String line){
        this.maxLine++;
        this.pipeMap.put(this.maxLine, line);

        if (this.maxCol == -1) {
            this.maxCol = line.length() - 1;
        }

        if (line.indexOf("S") != -1) {
            this.setStartPosition(this.maxLine, line.indexOf("S"));
        }
    }

    public void setStartPosition(int startLine, int startCol){
        this.startLine = startLine;
        this.startCol = startCol;
    }

    public int getStartLine(){
        return this.startLine;
    }

    public int getStartCol(){
        return this.startCol;
    }

    public char getTile(int line, int col){
        return this.pipeMap.get(line).charAt(col);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i : this.pipeMap.keySet()){
            sb.append(i + ": ");
            sb.append(this.pipeMap.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    private ArrayList<Tile> getPossibleRoutes(){
        //get tiles next to starting point
        ArrayList<Tile> possibleRoutes = new ArrayList<>();

        //move north
        if (this.startLine > 0) {
            possibleRoutes.add(new Tile(this.startCol, this.startLine - 1, this.getTile(this.startLine - 1, this.startCol)));
        }

        //move east
        if (this.startCol < this.maxCol) {
            possibleRoutes.add(new Tile(this.startCol + 1, this.startLine, this.getTile(this.startLine, this.startCol + 1)));
        }

        //move south
        if (this.startLine < this.maxLine) {
            possibleRoutes.add(new Tile(this.startCol, this.startLine + 1, this.getTile(this.startLine + 1, this.startCol)));
        }

        //move west
        if (this.startCol > 0) {
            possibleRoutes.add(new Tile(this.startCol - 1, this.startLine, this.getTile(this.startLine, this.startCol - 1)));
        }

        return possibleRoutes;
    }

    private boolean isOutsideOfBounds(Tile t){

        if (t.getX() < 0 || t.getX() > this.maxCol) {
            return true;
        }

        if(t.getY() < 0 || t.getY() > this.maxLine){
            return true;
        }

        return false;
    }

    public ArrayList<Tile> getLoop(){
        //create list of tiles that connect back to starting position
        ArrayList<Tile> retLoop = null;
        Tile startTile = new Tile(this.startCol, this.startLine, 'S');
        
        for(Tile t : this.getPossibleRoutes()){
            Tile prevTile = startTile;
            Tile curTile = t;
            boolean loopFound = false;

            retLoop = new ArrayList<>();
            retLoop.add(new Tile(prevTile));
            retLoop.add(new Tile(curTile));

            //System.out.println("trying path:");
            //System.out.println(prevTile);
            //System.out.println(curTile);

            while (true) {
                if (curTile.getLabel() == 'S') {
                    loopFound = true;
                    break;
                }

                Tile nextTile = this.ts.getNextTile(prevTile, curTile);

                if (nextTile == null) {
                    break;
                }

                if (this.isOutsideOfBounds(nextTile)) {
                    break;
                }

                nextTile.setLabel(this.getTile(nextTile.getY(), nextTile.getX()));
                retLoop.add(new Tile(nextTile));
                prevTile = curTile;
                curTile = nextTile;

                //System.out.println(nextTile);
            }

            //System.out.println("\n\n");

            if (loopFound) {
                break;
            }
        }

        return retLoop;
    }

    public int getMaxDistanceOnLoop(){
        //solution for part 1
        int ret = 0;

        ret = (this.getLoop().size() - 1) / 2;

        return ret;
    }
}
