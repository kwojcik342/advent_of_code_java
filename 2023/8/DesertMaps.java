import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DesertMaps {
    ArrayList<Character> navInstructions;
    HashMap<String, ArrayList<String>> nodes;

    public DesertMaps(){
        this.navInstructions = new ArrayList<>();
        this.nodes = new HashMap<>();
    }

    public String toString(){
        return this.navInstructions.toString() + "\n" + this.nodes.toString();
    }

    public void addInstructions(String instructions){
        String[] nav = instructions.split("|");
        this.navInstructions = Arrays.stream(nav).map(s -> s.charAt(0))
                                                 .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addNode(String nodeStr){
        String[] nodeData = nodeStr.split("=");
        String node = nodeData[0].trim();
        String[] nodeDest = nodeData[1].split(",");
        ArrayList<String> dest = Arrays.stream(nodeDest).map(s -> s.trim().replace("(", "").replace(")", ""))
                                                        .collect(Collectors.toCollection(ArrayList::new));
        this.nodes.put(node, dest);
    }

    public int getStepsCount(){
        //solution for part 1
        int stepsCount = 0;
        boolean finishNodeFound = false;
        String finishNode = "ZZZ";
        String currentNode = "AAA";
        
        for(int i = 0; i < 300; i++){
            //if we dont get to ZZZ after going through all L/R instructions we have to loop again
            // for loop to avoid infinite loop in case of potential errors
            for(char direction : this.navInstructions){
                stepsCount++;

                if(direction == 'L'){
                    currentNode = this.nodes.get(currentNode).get(0);
                }else{
                    currentNode = this.nodes.get(currentNode).get(1);
                }

                if (currentNode.equals(finishNode)) {
                    finishNodeFound = true;
                    break;
                }
            }

            if (finishNodeFound) {
                System.out.println("finishNodeFound");
                break;
            }
        }

        return stepsCount;
    }

    public int getStepsCount(String startNode){
        //part of solution for part 2
        int stepsCount = 0;
        boolean finishNodeFound = false;
        //String finishNode = "ZZZ";
        String currentNode = startNode;
        
        while(true){
            //if we dont get to XXZ after going through all L/R instructions we have to loop again
            for(char direction : this.navInstructions){
                stepsCount++;

                if(direction == 'L'){
                    currentNode = this.nodes.get(currentNode).get(0);
                }else{
                    currentNode = this.nodes.get(currentNode).get(1);
                }

                if (currentNode.charAt(2) == 'Z') {
                    finishNodeFound = true;
                    break;
                }
            }

            if (finishNodeFound) {
                //System.out.println("finishNodeFound");
                break;
            }
        }

        return stepsCount;
    }

    private long greatestCommonDivisor(long num1, long num2){
        //part of solution for part 2
        if (num2 == 0)
            return num1;
        return greatestCommonDivisor(num2, num1 % num2);
    }
 
    private long leastCommonMultipleArray(ArrayList<Long> a){
        //part of solution for part 2
        long lcm = a.get(0);
        for (int i = 1; i < a.size(); i++) {
            long num1 = lcm;
            long num2 = a.get(i);
            long gcd_val = this.greatestCommonDivisor(num1, num2);
            lcm = (lcm * a.get(i)) / gcd_val;
        }
        return lcm;
    }

    public long getStepsCountMulti(){
        //int stepsCount = 0;
        //boolean finishNodeFound = false;
        //ArrayList<String> startingNodes = new ArrayList<>();
        ArrayList<Long> stepsCounts = new ArrayList<>();


        for(String node : this.nodes.keySet()){
            if (node.charAt(2) == 'A') {
                //startingNodes.add(node);
                //System.out.println("starting node = " + node);
                //System.out.println("steps count = " + this.getStepsCount(node));
                //System.out.println("");
                stepsCounts.add(Long.valueOf(this.getStepsCount(node)));
            }
        }

        //bruteforce takes too long
        // while (true) {
        //     for(char dir : this.navInstructions){
        //         stepsCount++;
        //         ArrayList<String> currentNodes = new ArrayList<>();

        //         int dir2int = 0;
        //         if (dir == 'R') {
        //             dir2int = 1;
        //         }

        //         //get all next nodes
        //         for(String node : startingNodes){
        //             currentNodes.add(this.nodes.get(node).get(dir2int));
        //         }

        //         //check if all next nodes are finish nodes
        //         int finishNodes = 0;
        //         for(String dstNode : currentNodes){
        //             if (dstNode.charAt(2) == 'Z') {
        //                 finishNodes++;
        //             }
        //         }
        //         if (finishNodes == currentNodes.size()) {
        //             finishNodeFound = true;
        //             break;
        //         }

        //         startingNodes = new ArrayList<>(currentNodes);

        //     }
            
        //     if (finishNodeFound) {
        //         System.out.println("finishNodeFound");
        //         break;
        //     }
        // }

        return leastCommonMultipleArray(stepsCounts);
    }
}
