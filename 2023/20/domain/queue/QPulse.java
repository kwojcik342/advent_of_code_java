package domain.queue;

public class QPulse {
    private String srcName;
    private String destName;
    private int pulse;

    public QPulse(String srcName, String destName, int pulse){
        this.srcName = srcName;
        this.destName = destName;
        this.pulse = pulse;
    }

    public String getSrcName(){
        return this.srcName;
    }

    public String getDestName(){
        return this.destName;
    }

    public int getPulse(){
        return this.pulse;
    }

    public String toString(){
        return this.pulse + ": " + this.srcName + " -> " + this.destName;
    }
}
