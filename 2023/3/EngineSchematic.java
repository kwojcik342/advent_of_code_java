import java.nio.file.Paths; 
import java.util.LinkedList;
import java.util.Scanner;

public class EngineSchematic {
    public static void main(String[] args) {

        LinkedList<Line> lines = new LinkedList<>();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while(sc.hasNextLine()){
                lines.add(new Line(sc.nextLine()));
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        LinkedList<Integer> partNumbers = new LinkedList<>();

        for(int l = 0; l < lines.size(); l++){
            if(l == 0){
                partNumbers.addAll(lines.get(l).getPartNumbers(null, lines.get(l+1)));
            }else{
                if(l == lines.size() - 1){
                    partNumbers.addAll(lines.get(l).getPartNumbers(lines.get(l-1), null));
                }else{
                    partNumbers.addAll(lines.get(l).getPartNumbers(lines.get(l-1), lines.get(l+1)));
                }
            }
        }

        // int partNumbersSum = 0;
        // System.out.println("part numbers:");
        // for(int partNumber : partNumbers){
        //     partNumbersSum += partNumber;
        //     System.out.println(partNumber);
        // }

        // System.out.println("partNumbersSum = " + partNumbersSum);


        LinkedList<Integer> gearRatios = new LinkedList<>();

        for(int l = 0; l < lines.size(); l++){
            if(l == 0){
                gearRatios.addAll(lines.get(l).getGearRatios(null, lines.get(l+1)));
            }else{
                if(l == lines.size() - 1){
                    gearRatios.addAll(lines.get(l).getGearRatios(lines.get(l-1), null));
                }else{
                    gearRatios.addAll(lines.get(l).getGearRatios(lines.get(l-1), lines.get(l+1)));
                }
            }
        }

        int gearRatiosSum = 0;
        for(int gearRatio : gearRatios){
            gearRatiosSum += gearRatio;
        }

        System.out.println("gearRatiosSum = " + gearRatiosSum);

    }
}
