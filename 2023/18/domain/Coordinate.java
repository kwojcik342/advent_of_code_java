package domain;

public class Coordinate {
    private long x;
    private long y;

    public Coordinate(long x, long y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c){
        this.x = c.x;
        this.y = c.y;
    }

    public long getX(){
        return this.x;
    }

    public long getY(){
        return this.y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        Coordinate oc = (Coordinate) o;

        if (this.x == oc.x && this.y == oc.y) {
            return true;
        }

        return false;
    }
}
