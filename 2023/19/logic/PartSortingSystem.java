package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import domain.Part;

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

}
