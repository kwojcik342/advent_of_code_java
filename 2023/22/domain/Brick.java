package domain;

public class Brick implements Comparable<Brick>{
    private int uniqueNumber;
    private Coordinate start;
    private Coordinate end;

    public Brick(String data){
        String[] dataSpl = data.split("~");
        this.start = new Coordinate(dataSpl[0]);
        this.end = new Coordinate(dataSpl[1]);
        this.uniqueNumber = -1;
    }

    public Brick(Brick b){
        this.uniqueNumber = b.uniqueNumber;
        this.start = new Coordinate(b.start);
        this.end = new Coordinate(b.end);
    }

    public String toString(){
        return this.start.toString() + " -> " + this.end.toString();
    }

    @Override
    public int compareTo(Brick b) {
        // assuming start is always lower on z axis than end
        if (this.start.getZ() > b.start.getZ()) {
            return 1;
        }

        if (this.start.getZ() < b.start.getZ()) {
            return -1;
        }else{
            return 0;
        }
    }

    public int getMinZ(){
        if (this.start.getZ() <= this.end.getZ()) {
            return this.start.getZ();
        }else{
            return this.end.getZ();
        }
    }

    public int getMaxZ(){
        if (this.start.getZ() >= this.end.getZ()) {
            return this.start.getZ();
        }else{
            return this.end.getZ();
        }
    }

    public int getUniqueNumber(){
        return this.uniqueNumber;
    }

    public boolean isOverlapping(Brick b){
        if ((Math.max(this.start.getX(), b.start.getX()) <= Math.min(this.end.getX(), b.end.getX()))
            && (Math.max(this.start.getY(), b.start.getY()) <= Math.min(this.end.getY(), b.end.getY()))
           ) 
        {
            return true;
        }

        return false;
    }

    public void setZ(int z){
        int zDiff = this.end.getZ() - this.start.getZ();
        this.start.setZ(z);
        this.end.setZ(z + zDiff);
    }

    public void setUniqueNumber(int uniqueNumber){
        this.uniqueNumber = uniqueNumber;
    }
}
