package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import domain.Coordinates;

public class StepCounter {
    private int startX;
    private int startY;

    private ArrayList<String> garden;

    public StepCounter(String fileName){
        this.garden = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            int y = -1;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {
                    this.garden.add(line);
                    y++;

                    int x = line.indexOf("S");
                    if (x != -1) {
                        this.startX = x;
                        this.startY = y;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateReachablePlots(){
        // solution to part 1
        // which how many "." in garden are reachable in certain amount of steps from starting position
        int stepsLeft = 64;

        //System.out.println("starting pos = (" + this.startX + ", " + this.startY + ")");

        Queue<Coordinates> q = new LinkedList<>(); // queue for posiible next steps
        q.add(new Coordinates(startX, startY, stepsLeft));

        ArrayList<Coordinates> finalPositions = new ArrayList<>();

        while (true) {
            Coordinates c = q.remove();

            if (c.getStepsLeft() == 0) {
                // no more steps to do, add position to list 
                finalPositions.add(c);
            }else{
                // otherwise we make next move
                int[][] mov = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
                for(int[] i : mov){
                    int newX = c.getX() + i[0];
                    int newY = c.getY() + i[1];

                    if(newX < 0 || newX >= this.garden.get(0).length()-1 || newY < 0 || newY >= this.garden.size()){
                        // new position would be outside of bounds
                        continue;
                    }

                    if (this.garden.get(newY).charAt(newX) == '#') {
                        // new position would be a rock
                        continue;
                    }

                    // movement is possible, add to queue if it is not a duplicate
                    Coordinates nextMove = new Coordinates(newX, newY, c.getStepsLeft()-1);
                    if (!q.contains(nextMove)) {
                        q.add(new Coordinates(newX, newY, c.getStepsLeft()-1));
                    }
                    
                }
            }

            if (q.isEmpty()) {
                // no more moves to process
                break;
            }
        }

        System.out.println("solution for part 1 = " + finalPositions.size());
    }
}
