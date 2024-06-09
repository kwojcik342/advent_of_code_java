public class Compartment implements Comparable<Compartment>{
    private long minSrcValue;
    private long minDstValue;
    private long length;

    public Compartment(long minSrcValue, long minDstValue, long length){
        this.minSrcValue = minSrcValue;
        this.minDstValue = minDstValue;
        this.length = length;
    }

    public long getMinSrcValue(){
        return this.minSrcValue;
    }

    public long getMinDstValue(){
        return this.minDstValue;
    }

    public long getMaxSrcValue(){
        return this.minSrcValue + this.length - 1;
    }

    public long getMaxDstValue(){
        return this.minDstValue + this.length - 1;
    }

    public String toString(){
        return this.minSrcValue + "-" + this.getMaxSrcValue() + " -> " + this.minDstValue + "-" + this.getMaxDstValue();
    }

    public long getDstForSrc(long srcValue){
        return this.minDstValue + (srcValue - minSrcValue);
    }

    @Override
    public int compareTo(Compartment compared) {
        if (this.minSrcValue < compared.minSrcValue) {
            return -1;
        }

        return 1;
    }
}
