import java.util.LinkedList;

public class Line {
    private LinkedList<Num> nums;
    private LinkedList<Integer> specChars;
    private LinkedList<Integer> stars;
    private String line;

    public Line(String line){
        this.line = line;
        this.nums = new LinkedList<>();
        this.specChars = new LinkedList<>();
        this.stars = new LinkedList<>();

        String n = "";

        for(int i =0; i < line.length(); i++){
            if(Character.isDigit((line.charAt(i)))){
                if(n.isBlank()){
                    nums.add(new Num());
                }
                n += line.charAt(i);
                this.nums.get(nums.size()-1).addPosition(i);
            }else{
                if(!n.isBlank()){
                    this.nums.get(nums.size()-1).setNum(Integer.valueOf(n));
                    n = "";
                }

                if(line.charAt(i) != '.'){
                    specChars.add(i);
                    if (line.charAt(i) == '*') {
                        this.stars.add(i);
                    }
                }
            }
        }

        if(!n.isBlank()){
            this.nums.get(nums.size()-1).setNum(Integer.valueOf(n));
        }
    }

    public String getLine(){
        return this.line;
    }

    public String toString(){
        String ret = this.line + "\n" + "Numbers:\n";

        for(Num n : this.nums){
            ret += (n + "\n");
        }

        ret += "Special Characters: ";
        for(int sc : this.specChars){
            ret += (sc + ",");
        }

        ret += "\n";

        return ret;
    }

    public LinkedList<Integer> getPartNumbers(Line prevLine, Line nextLine){
        LinkedList<Integer> partNumbers = new LinkedList<>();

        for(Num n : this.nums){
            Boolean isPartNum = false;
            if (prevLine != null) {
                for(int sc : prevLine.specChars){
                    if(n.isAdjacent(sc)){
                        isPartNum = true;
                        break;
                    }
                }
            }

            if(!isPartNum){
                for(int sc : this.specChars){
                    if(n.isAdjacent(sc)){
                        isPartNum = true;
                        break;
                    }
                }
            }

            if(!isPartNum && nextLine != null){
                for(int sc : nextLine.specChars){
                    if(n.isAdjacent(sc)){
                        isPartNum = true;
                        break;
                    }
                }
            }

            if(isPartNum){
                partNumbers.add(n.getNum());
            }
        }

        return partNumbers;
    }

    public LinkedList<Integer> getGearRatios(Line prevLine, Line nextLine){
        LinkedList<Integer> gearRatios = new LinkedList<>();
        int adjCnt = 0;
        int gearRatio = 0;

        for(int s : this.stars){
            adjCnt = 0;
            gearRatio = 0;

            if (prevLine != null) {
                for(Num n : prevLine.nums){
                    if(n.isAdjacent(s)){
                        adjCnt++;
                        if (adjCnt > 2) {
                            break;
                        }else{
                            if (gearRatio == 0) {
                                gearRatio += n.getNum();
                            }else{
                                gearRatio *= n.getNum();
                            }
                        }
                    }
                }
            }

            if (adjCnt < 3) {
                for(Num n : this.nums){
                    if(n.isAdjacent(s)){
                        adjCnt++;
                        if (adjCnt > 2) {
                            break;
                        }else{
                            if (gearRatio == 0) {
                                gearRatio += n.getNum();
                            }else{
                                gearRatio *= n.getNum();
                            }
                        }
                    }
                }
            }

            if (adjCnt < 3 && nextLine != null) {
                for(Num n : nextLine.nums){
                    if(n.isAdjacent(s)){
                        adjCnt++;
                        if (adjCnt > 2) {
                            break;
                        }else{
                            if (gearRatio == 0) {
                                gearRatio += n.getNum();
                            }else{
                                gearRatio *= n.getNum();
                            }
                        }
                    }
                }
            }

            if (adjCnt == 2) {
                gearRatios.add(gearRatio);
            }
        }

        return gearRatios;
    }
}
