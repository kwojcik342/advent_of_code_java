public class Tile {
    private char label;
    private int x;
    private int y;

    public Tile(int x, int y, char label){
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public Tile(Tile t){
        this.label = t.label;
        this.x = t.x;
        this.y = t.y;
    }

    public char getLabel(){
        return this.label;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setLabel(char label){
        this.label = label;
    }

    public String toString(){
        return String.valueOf(this.label) + ": (" + this.x + ", " + this.y + ")";
    }

    public boolean isAdjacent(Tile adjacent){
        if (this.x == adjacent.x - 1 || this.x == adjacent.x + 1){
            return true;
        }

        if (this.y == adjacent.y - 1 || this.y == adjacent.y + 1) {
            return true;
        }

        return false;
    }

    public int[] diffCoordinatesToPrev(Tile prevTile){
        int[] coordinatesDiff = new int[2];

        coordinatesDiff[0] = prevTile.x - this.x;
        coordinatesDiff[1] = prevTile.y - this.y;

        return coordinatesDiff;
    }
}
