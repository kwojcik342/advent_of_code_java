import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int result = 0;

        try (Scanner sc = new Scanner(Paths.get("input\\input.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.isBlank()) {
                    Springs sp = new Springs(line);
                    result += sp.possibleArrangements();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Springs sp = new Springs("???.### 1,1,3");
        //Springs sp = new Springs(".??..??...?##. 1,1,3");
        //Springs sp = new Springs("?#?#?#?#?#?#?#? 1,3,1,6");
        //Springs sp = new Springs("????.#...#... 4,1,1");
        //Springs sp = new Springs("????.######..#####. 1,6,5");
        //Springs sp = new Springs("?###???????? 3,2,1");
        //System.out.println(sp.possibleArrangements());
        //System.out.println(sp.possibleArrangementsUnfolded());

        System.out.println("part 1 solution = " + result);
    }
}