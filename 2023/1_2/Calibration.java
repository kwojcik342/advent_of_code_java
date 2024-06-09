import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Calibration{
    public static void main(String[] args) {

        ArrayList<Integer> nums = new ArrayList<>();

        HashMap<String, Integer> wordsFirstIndex;
        HashMap<String, Integer> wordsLastIndex;

        HashMap<String, Integer> wordsToNum = new HashMap<>();
        wordsToNum.put("one", 1);
        wordsToNum.put("two", 2);
        wordsToNum.put("three", 3);
        wordsToNum.put("four", 4);
        wordsToNum.put("five", 5);
        wordsToNum.put("six", 6);
        wordsToNum.put("seven", 7);
        wordsToNum.put("eight", 8);
        wordsToNum.put("nine", 9);

        // for(String w : Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")){
        //     wordsFirstIndex.put(w, -1);
        //     wordsLastIndex.put(w, -1);
        // }

        int test = 0;

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while(sc.hasNextLine()){

                String line = sc.nextLine();
                int firstInt = -1;
                int lastInt = -1;
                wordsFirstIndex = new HashMap<>();
                wordsLastIndex = new HashMap<>();
                String firstNumStr = "";
                String lastNumStr = "";

                test++;
                System.out.println("line: " + line);

                for(int ci = 0; ci < line.length(); ci++){
                    if (Character.isDigit(line.charAt(ci))) {
                        if (firstInt == -1) {
                            firstInt = ci;
                        }else{
                            lastInt = ci;
                        }
                    }
                }

                for(String w : Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")){
                    wordsFirstIndex.put(w, line.indexOf(w));
                    wordsLastIndex.put(w, line.lastIndexOf(w));
                }

                System.out.println("firstInt: " + firstInt);
                System.out.println("lastInt: " + lastInt);

                for(String k : wordsFirstIndex.keySet()){
                    if (firstInt == -1 || (firstInt != -1 && wordsFirstIndex.get(k) != -1 && firstInt > wordsFirstIndex.get(k))) {
                        firstInt = wordsFirstIndex.get(k);
                        firstNumStr = k;
                    }

                    if(lastInt == -1 || (lastInt != -1 && wordsLastIndex.get(k) != -1 && lastInt < wordsLastIndex.get(k))){
                        lastInt = wordsLastIndex.get(k);
                        lastNumStr = k;
                    }
                }

                System.out.println("after words firstInt: " + firstInt);
                System.out.println("after words lastInt: " + lastInt);

                String fullNum = "";
                
                if(firstNumStr.isBlank()){
                    fullNum += line.charAt(firstInt);
                }else{
                    fullNum += String.valueOf(wordsToNum.get(firstNumStr));
                }
                
                if (lastInt != -1) {

                    if (lastNumStr.isBlank()) {
                        fullNum += line.charAt(lastInt);
                    }else{
                        fullNum += String.valueOf(wordsToNum.get(lastNumStr));
                    }
                }else{
                    fullNum += fullNum;
                }

                System.out.println("fullNum: " + fullNum);
                
                nums.add(Integer.valueOf(Integer.valueOf(fullNum)));

                System.out.println("");
                System.out.println("");
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