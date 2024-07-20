package domain;

public class Part {
    private int x;
    private int m;
    private int a;
    private int s;

    public Part(int x, int m, int a, int s){
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }

    public Part(Part p){
        this.x = p.x;
        this.m = p.m;
        this.a = p.a;
        this.s = p.s;
    }

    public Part(String definition){
        String[] values = definition.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("=", "").replaceAll("[xmas]", "").split(",");
        this.x = Integer.valueOf(values[0]);
        this.m = Integer.valueOf(values[1]);
        this.a = Integer.valueOf(values[2]);
        this.s = Integer.valueOf(values[3]);
    }

    public int getX(){
        return this.x;
    }

    public int getM(){
        return this.m;
    }

    public int getA(){
        return this.a;
    }

    public int getS(){
        return this.s;
    }

    public String toString(){
        return "x=" + this.x + ",m=" + this.m + ",a=" + this.a + ",s=" + this.s; 
    }

    public int getSumValues(){
        return this.x + this.m + this.a + this.s;
    }
}
