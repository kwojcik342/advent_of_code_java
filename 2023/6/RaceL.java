public class RaceL {
    private long raceDuration;
    private long recordDistance;

    public RaceL(long raceDuration, long recordDistance){
        this.raceDuration = raceDuration;
        this.recordDistance = recordDistance;
    }

    public String toString(){
        return "raceDuration = " + this.raceDuration + "\n" + "Record distance = " + this.recordDistance;
    }

    public boolean beatsRecordDistance(long chargeTime){
        long remainingTravelTime = this.raceDuration - chargeTime;
        
        if(remainingTravelTime * chargeTime > this.recordDistance){
            return true;
        }

        return false;
    }

    public long beatingRecordPossibilities(){
        long possibilities = 0;

        for(long possibleChargeTime = 0; possibleChargeTime <= this.raceDuration; possibleChargeTime++){
            if (this.beatsRecordDistance(possibleChargeTime)) {
                possibilities++;
            }
        }

        return possibilities;
    }
}
