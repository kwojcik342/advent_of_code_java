package domain;

public class Route {
    private Coordinate currentPos;
    private Coordinate direction;

    public Route(Coordinate currentPos, Coordinate direction){
        this.currentPos = new Coordinate(currentPos);
        this.direction = new Coordinate(direction);
    }

    public Coordinate getCurrentPos(){
        return this.currentPos;
    }

    public Coordinate getDirection(){
        return this.direction;
    }

    public String toString(){
        return "position = " + this.currentPos + " direction = " + this.direction;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        Route or = (Route) o;

        if (this.currentPos.equals(or.currentPos) && this.direction.equals(or.direction)) {
            return true;
        }

        return false;
    }
}
