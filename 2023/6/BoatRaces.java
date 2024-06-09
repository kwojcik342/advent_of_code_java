import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class BoatRaces {

    public static void main(String[] args) {

        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();
        ArrayList<Race> races = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                line = line.replaceAll("(\\s)+", " ");

                String[] lineSplit = line.split(":");
                String[] lineValues = lineSplit[1].trim().split(" ");


                for(int i = 0; i < lineValues.length; i++){
                    int value = Integer.valueOf(lineValues[i]);

                    if (lineSplit[0].equals("Time")) {
                        times.add(value);
                    }

                    if (lineSplit[0].equals("Distance")) {
                        distances.add(value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println("times: " + times);
        // System.out.println("distances: " + distances);

        //Part one
        // for(int i = 0; i < times.size(); i++){
        //     races.add(new Race(times.get(i), distances.get(i)));
        // }

        // int solution = 0;
        // for(Race r : races){
        //     int possibilities = r.beatingRecordPossibilities();

        //     if(possibilities != 0){
        //         if (solution == 0) {
        //             solution = possibilities;
        //         }else{
        //             solution *= possibilities;
        //         }
        //     }
        // }
        // System.out.println("solution = " + solution);


        //Part two
        StringBuilder sb = new StringBuilder();
        for(int t : times){
            sb.append(t);
        }

        long time = Long.valueOf(sb.toString());
        sb = new StringBuilder();
        for(int d : distances){
            sb.append(d);
        }
        long distance = Long.valueOf(sb.toString());

        System.out.println("time = " + time);
        System.out.println("distance = " + distance);

        RaceL rl = new RaceL(time, distance);
        long result = rl.beatingRecordPossibilities();
        System.out.println("Beating record possibilities for long race = " + result);
    }
}