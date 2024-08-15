package domain;

public class Coordinate {
    private int x;
    private int y;
    private int z;

    public Coordinate(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate(String data){
        String[] dataSpl = data.split(",");
        this.x = Integer.valueOf(dataSpl[0]);
        this.y = Integer.valueOf(dataSpl[1]);
        this.z = Integer.valueOf(dataSpl[2]);
    }

    public Coordinate(Coordinate c){
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    public void setZ(int z){
        this.z = z;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
