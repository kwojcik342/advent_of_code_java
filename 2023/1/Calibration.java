import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Calibration{
    public static void main(String[] args) {

        ArrayList<Integer> nums = new ArrayList<>();

        int test = 0;

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while(sc.hasNextLine()){

                String line = sc.nextLine();
                int firstInt = -1;
                int lastInt = -1;

                //test++;
                //System.out.println("line: " + line);

                for(int ci = 0; ci < line.length(); ci++){
                    if (Character.isDigit(line.charAt(ci))) {
                        if (firstInt == -1) {
                            firstInt = ci;
                        }else{
                            lastInt = ci;
                        }
                    }
                }

                //System.out.println("firstInt: " + firstInt);
                //System.out.println("lastInt: " + lastInt);

                String fullNum = "" + line.charAt(firstInt);
                if (lastInt != -1) {
                    fullNum += line.charAt(lastInt);
                }else{
                    fullNum += line.charAt(firstInt);
                }

                //System.out.println("fullNum: " + fullNum);
                
                nums.add(Integer.valueOf(Integer.valueOf(fullNum)));

                //System.out.println("");
                //System.out.println("");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int sum = 0;

        for(int num : nums){
            sum += num;
        }

        System.out.println("sum: " + sum);
        
    }
}