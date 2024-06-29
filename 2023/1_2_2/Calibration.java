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

        HashMap<String, Integer> wordsToNumSingle = new HashMap<>();
        wordsToNumSingle.put("one", 1);
        wordsToNumSingle.put("two", 2);
        wordsToNumSingle.put("three", 3);
        wordsToNumSingle.put("four", 4);
        wordsToNumSingle.put("five", 5);
        wordsToNumSingle.put("six", 6);
        wordsToNumSingle.put("seven", 7);
        wordsToNumSingle.put("eight", 8);
        wordsToNumSingle.put("nine", 9);

        HashMap<String, Integer> wordsToNumTeens = new HashMap<>();
        wordsToNumTeens.put("ten", 10);
        wordsToNumTeens.put("eleven", 11);
        wordsToNumTeens.put("twelve", 12);
        wordsToNumTeens.put("thirteen", 13);
        wordsToNumTeens.put("fourteen", 14);
        wordsToNumTeens.put("fifteen", 15);
        wordsToNumTeens.put("sixteen", 16);
        wordsToNumTeens.put("seventeen", 17);
        wordsToNumTeens.put("eighteen", 18);
        wordsToNumTeens.put("nineteen", 19);


        // for(String w : Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")){
        //     wordsFirstIndex.put(w, -1);
        //     wordsLastIndex.put(w, -1);
        // }

        int test = 0;

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    int firstInt = -1;
                    int lastInt = -1;

                    //System.out.println("line before: " + line);
                    
                    for(String k : wordsToNumTeens.keySet()){
                        line = line.replace(k, k.substring(0,1) + String.valueOf(wordsToNumTeens.get(k)) + k.substring(k.length()-1, k.length()));
                    }
                    for(String k : wordsToNumSingle.keySet()){
                        line = line.replace(k, k.substring(0,1) + String.valueOf(wordsToNumSingle.get(k)) + k.substring(k.length()-1, k.length()));
                    }

                    //System.out.println("line after: " + line);
                    

                    for(int ci = 0; ci < line.length(); ci++){
                        if (Character.isDigit(line.charAt(ci))) {
                            if (firstInt == -1) {
                                firstInt = ci;
                            }else{
                                lastInt = ci;
                            }
                        }
                    }

                    String fullNum = "" + line.charAt(firstInt);
                    if (lastInt != -1) {
                        fullNum += line.charAt(lastInt);
                    }else{
                        fullNum += fullNum;
                    }

                    //System.out.println("fullNum: " + fullNum);
                    //System.out.println("");
                
                    nums.add(Integer.valueOf(Integer.valueOf(fullNum)));

                    
                }


        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        int sum = 0;

        for(int num : nums){
            sum += num;
        }

        System.out.println("sum: " + sum);
        
    }
}