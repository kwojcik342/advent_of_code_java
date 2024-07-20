package domain;

public class DigInstruction {
    private char direction;
    private long distance;
    private String colour;

    public DigInstruction(char direction, long distance, String colour){
        this.direction = direction;
        this.distance = distance;
        this.colour = colour;
    }

    public char getDirection(){
        return this.direction;
    }

    public long getDistance(){
        return this.distance;
    }

    public String getColour(){
        return this.colour;
    }

    public String toString(){
        return this.direction + " " + this.distance;
    }
}
