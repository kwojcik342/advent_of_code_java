package domain;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c){
        this.x = c.x;
        this.y = c.y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
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
