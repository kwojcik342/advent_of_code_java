public class Round {
    private int redCubes;
    private int greenCubes;
    private int blueCubes;

    public Round(int redCubes, int greenCubes, int blueCubes){
        this.redCubes = redCubes;
        this.greenCubes = greenCubes;
        this.blueCubes = blueCubes;
    }

    public Round(String roundStr){
        String[] roundStrSplit = roundStr.split(",");
        int redCubes = 0;
        int greenCubes = 0;
        int blueCubes = 0;

        for(int c = 0; c < roundStrSplit.length; c++){
            String[] cubeStr = roundStrSplit[c].trim().split(" ");
            if (cubeStr[1].equals("red")) {
                redCubes = Integer.valueOf(cubeStr[0]);
            }
            if (cubeStr[1].equals("green")) {
                greenCubes = Integer.valueOf(cubeStr[0]);
            }
            if (cubeStr[1].equals("blue")) {
                blueCubes = Integer.valueOf(cubeStr[0]);
            }
        }

        this.redCubes = redCubes;
        this.greenCubes = greenCubes;
        this.blueCubes = blueCubes;
    }

    public boolean isPossible(int maxRedCubes, int maxGreenCubes, int maxBlueCubes){
        if(this.redCubes > maxRedCubes || this.greenCubes > maxGreenCubes || this.blueCubes > maxBlueCubes){
            return false;
        }

        return true;
    }

    public String toString(){
        return this.redCubes + " red, " + this.greenCubes + " green, " + this.blueCubes + " blue";
    }

    public int getRedCubes(){
        return this.redCubes;
    }

    public int getGreenCubes(){
        return this.greenCubes;
    }

    public int getBlueCubes(){
        return this.blueCubes;
    }
}
