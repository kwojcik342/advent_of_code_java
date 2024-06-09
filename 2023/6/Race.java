public class Race {
    private int raceDuration;
    private int recordDistance;

    public Race(int raceDuration, int recordDistance){
        this.raceDuration = raceDuration;
        this.recordDistance = recordDistance;
    }

    public String toString(){
        return "raceDuration = " + this.raceDuration + "\n" + "Record distance = " + this.recordDistance;
    }

    public boolean beatsRecordDistance(int chargeTime){
        int remainingTravelTime = this.raceDuration - chargeTime;
        
        if(remainingTravelTime * chargeTime > this.recordDistance){
            return true;
        }

        return false;
    }

    public int beatingRecordPossibilities(){
        int possibilities = 0;

        for(int possibleChargeTime = 0; possibleChargeTime <= this.raceDuration; possibleChargeTime++){
            if (this.beatsRecordDistance(possibleChargeTime)) {
                possibilities++;
            }
        }

        return possibilities;
    }
}
