package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import domain.Part;
import domain.Rule;

public class PartSortingSystem {
    private HashMap<String, Workflow> workflows;
    private ArrayList<Part> parts;

    public PartSortingSystem(String fileName){
        this.workflows = new HashMap<>();
        this.parts = new ArrayList<>();
        this.loadData(fileName);
    }

    private void loadData(String fileName){
        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            boolean readWorkflows = true;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.isBlank()) {
                    readWorkflows = false;
                }

                if (!line.isBlank()) {
                    if (readWorkflows) {
                        Workflow w = new Workflow(line);
                        this.workflows.put(w.getName(), w);
                    }

                    if (!readWorkflows) {
                        this.parts.add(new Part(line));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAll(){
        System.out.println("Workflows: ");
        for(String k : this.workflows.keySet()){
            System.out.println(this.workflows.get(k));
        }

        System.out.println();

        System.out.println("Parts:");
        for(Part p : this.parts){
            System.out.println(p);
        }
    }

    private ArrayList<Part> getAcceptedParts(){
        ArrayList<Part> acceeptedParts = new ArrayList<>();

        for(Part p : this.parts){
            Workflow w = this.workflows.get("in");

            while (true) {
                String wResult = w.processPart(p);

                if (wResult.equals("R")) {
                    break;
                }

                if (wResult.equals("A")) {
                    acceeptedParts.add(new Part(p));
                    break;
                }

                w = this.workflows.get(wResult);
            }
        }

        return acceeptedParts;
    }

    public void calculateAcceptedParts(){
        // solution for part 1
        // get parts that pass through workflows 
        // sum all values from those parts
        ArrayList<Part> acceptedParts = this.getAcceptedParts();
        int sum = 0;

        for(Part p : acceptedParts){
            sum += p.getSumValues();
        }

        System.out.println("Solution for part 1 = " + sum);

    }

    private long calculateCombinations(String wflName, long xMin, long xMax, long mMin, long mMax, long aMin, long aMax, long sMin, long sMax){
        long res = 0;

        if (wflName.equals("R")) {
            return 0;
        }

        if (wflName.equals("A")) {
            return (xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1);
        }

        Workflow w = this.workflows.get(wflName);

        long nxMin = xMin;
        long nxMax = xMax;
        long nmMin = mMin;
        long nmMax = mMax;
        long naMin = aMin;
        long naMax = aMax;
        long nsMin = sMin;
        long nsMax = sMax;
        
        boolean rangeBreak = false;

        for(Rule r : w.getRules()){
            long keyMax = 0;
            long keyMin = 0;
            long tMax = 0;
            long tMin = 0;
            long fMax = 0;
            long fMin = 0;

            // LOG START
            // System.out.println("");
            // System.out.println("Workflow: " + w.getName() + " rule: " + String.valueOf(r.getCheckedValue()) + String.valueOf(r.getCondition()) + String.valueOf(r.getCoparedValue()));
            // LOG END

            if (r.getCheckedValue() == 'x') {
                keyMax = xMax;
                keyMin = xMin;
            }

            if (r.getCheckedValue() == 'm') {
                keyMax = mMax;
                keyMin = mMin;
            }

            if (r.getCheckedValue() == 'a') {
                keyMax = aMax;
                keyMin = aMin;
            }

            if (r.getCheckedValue() == 's') {
                keyMax = sMax;
                keyMin = sMin;
            }

            //get new ranges for rule's condition
            if (r.getCondition() == '<') {

                tMin = keyMin;
                if ((r.getComparedValue() - 1) < keyMax) {
                    tMax = r.getComparedValue() - 1;
                }else{
                    tMax = keyMax;
                }
                
                if (r.getComparedValue() > keyMin) {
                    fMin = r.getComparedValue();
                }else{
                    fMin = keyMin;
                }
                fMax = keyMax;
            }else {
                if ((r.getComparedValue() + 1) > keyMin) {
                    tMin = r.getComparedValue() + 1;
                }else {
                    tMin = keyMin;
                }
                tMax = keyMax;

                fMin = keyMin;
                if (r.getComparedValue() < keyMax) {
                    fMax = r.getComparedValue();
                }else {
                    fMax = keyMax;
                }
            }

            if (tMin <= tMax) {
                // reassing values to ranges based on value we checked
                long txMin = nxMin;
                long txMax = nxMax;
                long tmMin = nmMin;
                long tmMax = nmMax;
                long taMin = naMin;
                long taMax = naMax;
                long tsMin = nsMin;
                long tsMax = nsMax;

                if (r.getCheckedValue() == 'x') {
                    txMax = tMax;
                    txMin = tMin;
                }
    
                if (r.getCheckedValue() == 'm') {
                    tmMax = tMax;
                    tmMin = tMin;
                }
    
                if (r.getCheckedValue() == 'a') {
                    taMax = tMax;
                    taMin = tMin;
                }
    
                if (r.getCheckedValue() == 's') {
                    tsMax = tMax;
                    tsMin = tMin;
                }

                //System.out.println("Next workflow for T: " + r.getProcessingResult() + " with values x: " + txMin + "-" + txMax + " m: " + tmMin + "-" + tmMax + " a: " + taMin + "-" + taMax + " s: " + tsMin + "-" + tsMax); // LOG
                res += this.calculateCombinations(r.getProcessingResult(), txMin, txMax, tmMin, tmMax, taMin, taMax, tsMin, tsMax);
            }

            if (fMin <= fMax) {
                if (r.getCheckedValue() == 'x') {
                    nxMax = fMax;
                    nxMin = fMin;
                }
    
                if (r.getCheckedValue() == 'm') {
                    nmMax = fMax;
                    nmMin = fMin;
                }
    
                if (r.getCheckedValue() == 'a') {
                    naMax = fMax;
                    naMin = fMin;
                }
    
                if (r.getCheckedValue() == 's') {
                    nsMax = fMax;
                    nsMin = fMin;
                }
            }else {
                rangeBreak = true;
                break;
            }
        }

        if (!rangeBreak) {
            res += this.calculateCombinations(w.getDefaultResult(), nxMin, nxMax, nmMin, nmMax, naMin, naMax, nsMin, nsMax);
        }

        return res;
    }

    public void getCombinations(){
        // solution for part 2
        // calculate possible combinations assuming each value can be in range 1-4000

        long possibilities = this.calculateCombinations("in", 1, 4000, 1, 4000, 1, 4000, 1, 4000);

        System.out.println("Solution for part 2 = " + possibilities);
    }

}
