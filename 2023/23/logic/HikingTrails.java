package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import domain.Coordinates;

public class HikingTrails {

    private ArrayList<String> inputData;
    private ArrayList<Integer> pathsNoSlopes;

    public HikingTrails(String fileName){

        this.inputData = new ArrayList<>();
        this.pathsNoSlopes = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {
                    inputData.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Coordinates> calculatePaths(){

        ArrayList<Coordinates> finalPaths = new ArrayList<>();

        // starting point is in first line, it should have only one dot
        Queue<Coordinates> travelPath = new LinkedList<>();
        travelPath.add(new Coordinates(this.inputData.get(0).indexOf('.'), 0, 0, 0, 1));

        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};

        while (!travelPath.isEmpty()) {
            Coordinates c = travelPath.remove();

            //System.out.println(); //LOG
            //System.out.println("traveling through: " + this.inputData.get(c.getY()).charAt(c.getX()) + " " + c); //LOG

            // get nodes adjacent to point we are traveling through
            for(int[] d : directions){

                if ((d[0] != 0 && d[0] == c.getDX()*-1)
                    || (d[1] != 0 && d[1] == c.getDY()*-1)) 
                {
                    // next point would be traveling backwards on the same path
                    continue;
                }

                int newX = c.getX() + d[0];

                if (newX < 0 || newX >= this.inputData.get(0).length()) {
                    // next point would be out of bounds
                    continue;
                }

                int newY = c.getY() + d[1];

                if (newY < 0 || newY >= this.inputData.size()) {
                    // next point would be out of bounds
                    continue;
                }

                //System.out.println("checking direction (" + d[0] + ", " + d[1] + ")"); // LOG

                if ((this.inputData.get(c.getY()).charAt(c.getX()) == '>' && d[0] != 1)
                    || (this.inputData.get(c.getY()).charAt(c.getX()) == '<' && d[0] != -1)
                    || (this.inputData.get(c.getY()).charAt(c.getX()) == '^' && d[1] != -1)
                    || (this.inputData.get(c.getY()).charAt(c.getX()) == 'v' && d[1] != 1)
                   ) 
                {
                    // current node is a slope, travel is possible only in one direction
                    continue;
                }

                if (this.inputData.get(newY).charAt(newX) == '.') {
                    // normal(not a slope) point that we can travel through
                    Coordinates nc = new Coordinates(newX, newY, c.getPathLength() + 1, d[0], d[1]);

                    if (newY == this.inputData.size() - 1) {
                        // reching last row means that path is finished
                        finalPaths.add(nc);
                        continue;
                    }

                    //System.out.println("travel possible to " + nc); // LOG

                    travelPath.add(nc);
                    continue;
                }

                if ((this.inputData.get(newY).charAt(newX) == '>' && d[0] != -1)
                    || (this.inputData.get(newY).charAt(newX) == '<' && d[0] != 1)
                    || (this.inputData.get(newY).charAt(newX) == '^' && d[1] != 1)
                    || (this.inputData.get(newY).charAt(newX) == 'v' && d[1] != -1)
                   ) 
                {
                    // if next node is a slope we can't travel against it
                    Coordinates nc = new Coordinates(newX, newY, c.getPathLength() + 1, d[0], d[1]);

                    //System.out.println("travel possible to " + nc); // LOG

                    travelPath.add(nc);
                    continue;
                }
            }
        }

        return finalPaths;
    }

    public void getLongestPath(){
        ArrayList<Coordinates> travelPaths = this.calculatePaths();
        int longestPath = 0;

        for(Coordinates c : travelPaths){
            if (c.getPathLength() > longestPath) {
                longestPath = c.getPathLength();
            }
        }

        System.out.println("Solution for part 1 = " + longestPath);
    }

    // private ArrayList<ArrayList<Integer>> getInitVisited(){
    //     // creates list of nodes possible to visit based on initial data
    //     // used for solution to part 2
    //     ArrayList<ArrayList<Integer>> visited = new ArrayList<>(this.inputData.size());

    //     for(String s : this.inputData){
    //         ArrayList<Integer> tmp = new ArrayList<>(s.length());

    //         for(int i = 0; i < s.length(); i++){
    //             if(s.charAt(i) == '#'){
    //                 tmp.add(1);
    //             }else {
    //                 tmp.add(0);
    //             }
                
    //         }

    //         visited.add(tmp);
    //     }

    //     return visited;
    // }

    private void followPath(Coordinates c, ArrayList<String> availablePaths){
        
        // really slow solution

        Coordinates cc = new Coordinates(c); // current point on path 
        Coordinates cn = null;               // next point on path
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};

        while (true) {

            // mark current point as visited
            StringBuilder row = new StringBuilder(availablePaths.get(cc.getY()));
            row.setCharAt(cc.getX(), '#');
            availablePaths.set(cc.getY(), row.toString());

            //System.out.println(availablePaths); // LOG

            for(int[] d : directions){

                int newX = cc.getX() + d[0];

                if (newX < 0 || newX >= this.inputData.get(0).length()) {
                    // next point would be out of bounds
                    continue;
                }

                int newY = cc.getY() + d[1];

                if (newY < 0 || newY >= this.inputData.size()) {
                    // next point would be out of bounds
                    continue;
                }

                if (availablePaths.get(newY).charAt(newX) != '#') {

                    // System.out.println("traveling through (" + newX + ", " + newY + ")"); // LOG

                    // point that we can travel through
                    if (newY == this.inputData.size() -1) {
                        // last row should have only 1 available point
                        // if we reached that it means we found complete path to travel through
                        this.pathsNoSlopes.add(cc.getPathLength() + 1);
                        continue;
                    }

                    if (cn == null) {
                        // 1st neighboring point that we can travel through
                        cn = new Coordinates(newX, newY, cc.getPathLength() + 1, 0, 0); // dx and dy from Coordinates doesn't matter in this approach
                    }else{
                        // we already have a point to travel through, start new recursion for another path
                        this.followPath(new Coordinates(newX, newY, cc.getPathLength() + 1, 0, 0), new ArrayList<>(availablePaths));
                    }
                }
            }

            if (cn == null) {
                // no more possible points to travel through
                break;
            }

            cc = cn;
            cn = null;
        }

    }

    public void getLongestPathNoSlopes(){
        int longestPath = 0;

        // ArrayList<ArrayList<Integer>> visited = this.getInitVisited();
        ArrayList<String> availablePaths = new ArrayList<>(inputData);

        this.followPath(new Coordinates(this.inputData.get(0).indexOf('.'), 0, 0, 0, 1), availablePaths);

        for(int p : this.pathsNoSlopes){
            if (p > longestPath) {
                longestPath = p;
            }
        }

        System.out.println("Solution for part 2 = " + longestPath);
    }
}
