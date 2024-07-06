package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import domain.Lens;

public class InitSequence {

    private HashMap<Integer, ArrayList<Lens>> boxes;

    public InitSequence(){
        this.boxes = new HashMap<>();
    }

    public void printBoxes(){
        for(int boxNumber : this.boxes.keySet()){
            StringBuilder sb = new StringBuilder();
            sb.append("Box ");
            sb.append(boxNumber);
            sb.append(": ");

            for(Lens l : this.boxes.get(boxNumber)){
                sb.append(l);
                sb.append(" ");
            }

            System.out.println(sb.toString());
        }
        System.out.println("");
    }

    private int calculateFocusingPower(){
        int focusingPower = 0;

        for(int bi : this.boxes.keySet()){
            for(int li = 0; li < this.boxes.get(bi).size(); li++){
                focusingPower += ((bi+1) * (li+1) * this.boxes.get(bi).get(li).getFocalLength());
            }
        }

        return focusingPower;
    }

    public void processSequence(String fileName){
        int sumHashValues = 0; // result for part 1

        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String[] steps = sc.nextLine().split(",");

                for(int i = 0; i < steps.length; i++){
                    Lens l = new Lens(steps[i]);

                    //calculate result for part 1
                    sumHashValues += l.getHashValueFull(); 

                    //part 2
                    //l.getHashValueLabel() - calculates key representing box number
                    if (l.getOperType() == '-') {
                        //remove lens from box
                        if (this.boxes.containsKey(l.getHashValueLabel())) {
                            this.boxes.get(l.getHashValueLabel()).remove(l);

                            //remove empty box
                            if (this.boxes.get(l.getHashValueLabel()).size() == 0) {
                                this.boxes.remove(l.getHashValueLabel());
                            }
                        }
                    }

                    if (l.getOperType() == '=') {
                        //add lens to box
                        if (!this.boxes.containsKey(l.getHashValueLabel())) {
                            //init array for box if not exists
                            this.boxes.put(l.getHashValueLabel(), new ArrayList<>());
                        }

                        int lensIdx = this.boxes.get(l.getHashValueLabel()).indexOf(l);
                        if (lensIdx == -1) {
                            this.boxes.get(l.getHashValueLabel()).add(l);
                        }

                        if (lensIdx != -1) {
                            this.boxes.get(l.getHashValueLabel()).get(lensIdx).setFocalLength(l.getFocalLength());
                        }
                        
                    }

                    // LOG START
                    //System.out.println("After " + steps[i]);
                    //this.printBoxes();
                    // LOG END
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("result for part 1 = " + sumHashValues);
        System.out.println("result for part 2 = " + this.calculateFocusingPower());
    }
}
