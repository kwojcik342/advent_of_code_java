import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class Games{
    public static void main(String[] args) {

        final int maxRedCubes = 12;
        final int maxGreenCubes = 13;
        final int maxBlueCubes = 14;

        LinkedList<Game> games = new LinkedList<>();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] lineSplit = line.split(":");
                games.add(new Game(Integer.valueOf(lineSplit[0].split(" ")[1]), lineSplit[1].trim()));
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        int sumGameIds = 0;
        for(Game g : games){
            if (g.isPossible(maxRedCubes, maxGreenCubes, maxBlueCubes)) {
                sumGameIds += g.getGameId();
            }
        }

        System.out.println("sumGameIds = " + sumGameIds);

        int sumPowers = 0;
        for(Game g : games){
            sumPowers += g.powerOfMinCubes();
        }
        System.out.println("sumPowers = " + sumPowers);

    }
}