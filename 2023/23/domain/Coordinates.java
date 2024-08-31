package domain;

public class Coordinates {
    private int x;
    private int y;
    private int pathLength;
    private int dx; // direction of travel from previus coordinate
    private int dy;

    public Coordinates(int x, int y, int pathLength, int dx, int dy){
        this.x = x;
        this.y = y;
        this.pathLength = pathLength;
        this.dx = dx;
        this.dy = dy;
    }

    public Coordinates(Coordinates c){
        this.x = c.x;
        this.y = c.y;
        this.pathLength = c.pathLength;
        this.dx = c.dx;
        this.dy = c.dy;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getPathLength(){
        return this.pathLength;
    }

    public int getDX(){
        return this.dx;
    }

    public int getDY(){
        return this.dy;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ") " + this.pathLength;
    }
}
