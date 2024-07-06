package domain;

public class Lens {
    private String label;
    private int focalLength;
    private char operType;

    private int calculateHashValue(String step){
        int result = 0;

        for(int c = 0; c < step.length(); c++){
            int asciiVal = step.charAt(c);
            result += asciiVal;
            result *= 17;
            result = result % 256;
        }

        return result;
    }

    public Lens(String step){
        if (step.contains("=")) {
            String[] lensData = step.split("=");
            this.label = lensData[0];
            this.focalLength = Integer.valueOf(lensData[1]);
            this.operType = '=';
        }

        if (step.contains("-")) {
            String[] lensData = step.split("-");
            this.label = lensData[0];
            this.focalLength = -1;
            this.operType = '-';
        }
    }
    
    public String getLabel(){
        return this.label;
    }

    public int getFocalLength(){
        return this.focalLength;
    }

    public char getOperType(){
        return this.operType;
    }

    public void setFocalLength(int focalLength){
        this.focalLength = focalLength;
    }

    public String toString(){
        return "[" + this.label + " " + this.focalLength + "]";
    }

    public int getHashValueFull(){
        if (this.operType == '-') {
            return this.calculateHashValue(this.label + "-");
        }else{
            return this.calculateHashValue(this.label + "=" + this.focalLength);
        }
    }

    public int getHashValueLabel(){
        return this.calculateHashValue(this.label);
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        Lens ol = (Lens) o;

        if (ol.label.equals(this.label)) {
            return true;
        }

        return false;
    }
}
