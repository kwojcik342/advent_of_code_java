import java.util.ArrayList;

public class Game {
    private int gameId;
    private ArrayList<Round> rounds;

    public Game(int gameId, String roundsStr){
        this.gameId = gameId;
        this.initGames(roundsStr);
    }

    public void initGames(String roundsStr){
        this.rounds = new ArrayList<>();
        String[] roundsStrSplit = roundsStr.split(";");

        for(int r = 0; r < roundsStrSplit.length; r ++){
            this.rounds.add(new Round(roundsStrSplit[r].trim()));
        }
    }

    public boolean isPossible(int maxRedCubes, int maxGreenCubes, int maxBlueCubes){
        for(Round r : this.rounds){
            if (!r.isPossible(maxRedCubes, maxGreenCubes, maxBlueCubes)) {
                return false;
            }
        }

        return true;
    }

    public int getGameId(){
        return this.gameId;
    }

    public String toString(){
        String ret = "Game " + this.gameId + ":";

        for(Round r : this.rounds){
            ret += r.toString() + ";";
        }

        return ret;
    }

    public int powerOfMinCubes(){
        int power = 1;
        int minRedCubes = 0;
        int minGreenCubes = 0;
        int minBlueCubes = 0;

        for(Round r : this.rounds){
            if(r.getRedCubes() > minRedCubes){
                minRedCubes = r.getRedCubes();
            }

            if(r.getGreenCubes() > minGreenCubes){
                minGreenCubes = r.getGreenCubes();
            }

            if(r.getBlueCubes() > minBlueCubes){
                minBlueCubes = r.getBlueCubes();
            }
        }

        if(minRedCubes > 0){
            power *= minRedCubes;
        }

        if(minGreenCubes > 0){
            power *= minGreenCubes;
        }

        if(minBlueCubes > 0){
            power *= minBlueCubes;
        }

        return power;
    }

}
