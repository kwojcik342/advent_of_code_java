import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Patterns {
    private ArrayList<ReflectionPattern> patterns;

    public Patterns(String inputFilenName){
        this.patterns = new ArrayList<>();
        this.readInput(inputFilenName);
    }

    private void readInput(String fileName){

        this.patterns.add(new ReflectionPattern());

        try (Scanner sc = new Scanner(Paths.get("input\\"+fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.isBlank()) {
                    this.patterns.add(new ReflectionPattern());
                }

                if (!line.isBlank()) {
                    this.patterns.get(this.patterns.size() - 1).addLine(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getResultPt1(){
        int result = 0;

        for(ReflectionPattern rp : this.patterns){
            result += rp.reflectionSummary(false);
        }

        return result;
    }

    public int getResultPt2(){
        int result = 0;

        for(ReflectionPattern rp : this.patterns){
            result += rp.reflectionSummary(true);
        }

        return result;
    }
}
