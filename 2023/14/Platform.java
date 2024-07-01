import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Platform {
    private ArrayList<ArrayList<Integer>> platform;

    public Platform(String inputFile){
        this.platform = new ArrayList<>();
        this.loadFromFile(inputFile);
    }

    private void loadFromFile(String fileName){
        //storing platform as array of columns
        try (Scanner sc = new Scanner(Paths.get("input\\" + fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {
                    for(int i = 0; i < line.length(); i++){
                        if (this.platform.size() < i + 1) {
                            this.platform.add(new ArrayList<>());
                        }

                        if (line.charAt(i) == 'O') {
                            this.platform.get(i).add(0);
                        }

                        if (line.charAt(i) == '#') {
                            this.platform.get(i).add(4);
                        }

                        if (line.charAt(i) == '.') {
                            this.platform.get(i).add(1);
                        }

                        //for testing purposes
                        if (line.charAt(i) != 'O' && line.charAt(i) != '#' && line.charAt(i) != '.') {
                            this.platform.get(i).add((int)line.charAt(i));
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printPlatform(){
        for(int i = 0; i < this.platform.get(0).size(); i++){
            StringBuilder row = new StringBuilder();

            for(ArrayList<Integer> col : this.platform){
                row.append(col.get(i));
            }

            System.out.println(row.toString());
        }
    }

    private void tilt(){
        //tilt whole matrix sliding 0s north

        for(int i = 0; i < this.platform.size(); i++){
            ArrayList<Integer> newColumn = new ArrayList<>();
            //ArrayList<Integer> curColumn = new ArrayList<>(this.platform.get(i));

            while (true) {
                int squareIdx = this.platform.get(i).indexOf(4);
                
                if (squareIdx == -1) {
                    squareIdx = this.platform.get(i).size();
                }

                List<Integer> part = this.platform.get(i).subList(0, squareIdx);

                if (part.size() > 0) {
                    Collections.sort(part);
                    newColumn.addAll(part);
                }

                part.clear();

                if (this.platform.get(i).size() == 0) {
                    break;
                }

                newColumn.add(4);
                this.platform.get(i).remove(0);
                
            }

            this.platform.get(i).addAll(newColumn);
            
        }
    }

    private void rotate(){
        //rotate matrix counterclockwise
        //for part 2 we rotate the matix counterclockwise so we only need to tilt in one direction
        ArrayList<ArrayList<Integer>> newPlatform = new ArrayList<>();

        for(int i = this.platform.get(0).size() - 1; i >= 0; i--){
            ArrayList<Integer> newColumn = new ArrayList<>();

            for(ArrayList<Integer> c : this.platform){
                newColumn.add(c.get(i));
            }

            newPlatform.add(newColumn);
        }

        this.platform = newPlatform;
    }

    private void cycle(){
        //cycle is (tilt + rotation) * 4
        for(int i = 0; i < 4; i++){
            this.tilt();

            //System.out.println("after tilt");
            //this.printPlatform();
            //System.out.println();

            this.rotate();

            //System.out.println("after rotation");
            //this.printPlatform();
            //System.out.println();
        }
    }

    private int getLoad(){
        //returns load for current state of the platform
        int totalLoad = 0;

        for(ArrayList<Integer> col : this.platform){
            for(int i = 0; i < col.size(); i++){

                if (col.get(i) == 0) {
                    totalLoad += (col.size() - i);
                }
            }
        }

        return totalLoad;
    }

    private ArrayList<ArrayList<Integer>> getPlatformCopy(){
        ArrayList<ArrayList<Integer>> newPlaform = new ArrayList<>();

        for(int i = 0; i < this.platform.size(); i++){
            newPlaform.add(new ArrayList<>());

            for(int j = 0; j < this.platform.get(i).size(); j++){
                newPlaform.get(i).add(this.platform.get(i).get(j));
            }
        }

        return newPlaform;
    }

    public int getLoadCycles(){
        //result for part 2

        //general idea is that platform state repeats after certain amount of cycles
        //we cache platform states until we encounter the same state again
        //the we use modulo to calculate how many repeating cycles are there in 1000000000

        ArrayList<ArrayList<ArrayList<Integer>>> platformCycles = new ArrayList<>();
        int firstOccurence = -1;
        int secondOccurence = -1;

        for(int i = 0; i < 1000000000; i++){
            this.cycle();
            //System.out.println("after cycle " + i);
            //System.out.println(this.platform);

            firstOccurence = platformCycles.indexOf(this.platform);
            if (firstOccurence != -1) {
                secondOccurence = i;
                break;
            }
            platformCycles.add(this.getPlatformCopy());
            
        }

        System.out.println("firstOccurence = " + firstOccurence);
        System.out.println("secondOccurence = " + secondOccurence);

        int indxCycle = ((1000000000 - firstOccurence) % (secondOccurence - firstOccurence)) + firstOccurence - 1;
        this.platform = platformCycles.get(indxCycle);
        

        return this.getLoad();
    }

    public int getLoadTilt(){
        //result for part 1
        this.tilt();
        return this.getLoad();
    }
}
