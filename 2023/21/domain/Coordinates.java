package domain;

public class Coordinates {
    private int x;
    private int y;
    private int stepsLeft;

    public Coordinates(int x, int y, int stepsLeft){
        this.x = x;
        this.y = y;
        this.stepsLeft = stepsLeft;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getStepsLeft(){
        return this.stepsLeft;
    }

    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (o.getClass() == this.getClass()) {
            Coordinates c = (Coordinates) o;

            if (c.x == this.x && c.y == this.y) {
                return true;
            }
        }

        return false;
    }
}
