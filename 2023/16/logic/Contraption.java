package logic;
import java.util.ArrayList;
import java.util.LinkedList;
import java.nio.file.Paths;
import java.util.Scanner;

import domain.Coordinate;
import domain.Route;

public class Contraption {
    private ArrayList<ArrayList<Character>> layout;

    public Contraption(String fileName){
        this.layout = new ArrayList<>();
        this.initLayout(fileName);
    }

    private void initLayout(String fileName){
        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {
                    this.layout.add(new ArrayList<>());

                    for(int i = 0; i < line.length(); i++){
                        this.layout.get(layout.size()-1).add(line.charAt(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printLayout(){
        for(ArrayList<Character> l : this.layout){
            StringBuilder line = new StringBuilder();
            for(char c : l){
                line.append(c);
            }
            System.out.println(line.toString());
        }
    }

    private ArrayList<Coordinate> getEnergizedTiles(int startX, int startY, int startDx, int startDy){
        //list containing tiles passed in specific direction
        ArrayList<Route> energizedTiles = new ArrayList<>();

        //queue, contains coordinates we are moving through
        //can contain multiple paths due to splitting
        LinkedList<Route> path = new LinkedList<>();

        //starting in top left corner, moving right
        //direction can be one of: (1, 0), (0, 1), (-1, 0), (0, -1)
        //adding direction coordinates to current position produces coordinates of next tile on travel path
        //example of moving right: currentPos(3, 0) + direction(1, 0) = nextTile(4, 0)
        path.add(new Route(new Coordinate(startX, startY), new Coordinate(startDx, startDy)));

        while (true) {
            //Coordinate nextPos = new Coordinate(currentPos.getX() + direction.getX(), currentPos.getY() + direction.getY());

            if (path.size() == 0) {
                break;
            }

            //Coordinate currentPos = path.get(0).getCurrentPos(); 
            //Coordinate direction = path.get(0).getDirection();
            Route currentR = new Route(path.get(0).getCurrentPos(), path.get(0).getDirection());
            path.remove(0);

            //LOG START
            // System.out.println("");
            // System.out.println("currentPos = " + currentR.getCurrentPos());
            // System.out.println("direction = " + currentR.getDirection());
            //LOG END

            if (currentR.getCurrentPos().getY() < 0 
                || currentR.getCurrentPos().getY() >= this.layout.size()
                || currentR.getCurrentPos().getX() < 0
                || currentR.getCurrentPos().getX() >= this.layout.get(0).size()
                ) 
            {
                // current beam is out of bounds
                //System.out.println("beam is oob"); // LOG
                continue;
            }
            
            char currentPosLabel = this.layout.get(currentR.getCurrentPos().getY()).get(currentR.getCurrentPos().getX());
            //System.out.println("currentPosLabel = " + currentPosLabel); // LOG

            if (currentPosLabel == '/') {
                // turn 90 degrees depending on direction of travel
                // (0, 1) -> (-1, 0)
                // (0, -1) -> (1, 0)
                // (1, 0) -> (0, -1)
                // (-1, 0) -> (0, 1)
                int newDx = currentR.getDirection().getY() * -1;
                int newDy = currentR.getDirection().getX() * -1;

                Route nextR = new Route(new Coordinate(currentR.getCurrentPos().getX() + newDx, currentR.getCurrentPos().getY() + newDy), new Coordinate(newDx, newDy));
                if (!energizedTiles.contains(currentR)) {
                    energizedTiles.add(currentR);
                    path.add(nextR);
                }
            }

            if (currentPosLabel == '\\') {
                // turn 90 degrees depending on direction of travel
                // (0, 1) -> (1, 0)
                // (0, -1) -> (-1, 0)
                // (1, 0) -> (0, 1)
                // (-1, 0) -> (0, -1)
                int newDx = currentR.getDirection().getY();
                int newDy = currentR.getDirection().getX();

                Route nextR = new Route(new Coordinate(currentR.getCurrentPos().getX() + newDx, currentR.getCurrentPos().getY() + newDy), new Coordinate(newDx, newDy));
                if (!energizedTiles.contains(currentR)) {
                    energizedTiles.add(currentR);
                    path.add(nextR);
                }
            }

            if (currentPosLabel == '|' && currentR.getDirection().getX() != 0) {
                // moving right-left or left-right causes split otherwise continue traveling
                if (!energizedTiles.contains(currentR)) {
                    //split upwards
                    Route nextR = new Route(new Coordinate(currentR.getCurrentPos().getX(), currentR.getCurrentPos().getY() - 1), new Coordinate(0, -1));
                    //split downwards
                    Route nextR2 = new Route(new Coordinate(currentR.getCurrentPos().getX(), currentR.getCurrentPos().getY() + 1), new Coordinate(0, 1));

                    energizedTiles.add(currentR);
                    path.add(nextR);
                    path.add(nextR2);

                    //System.out.println("split adding route = " + nextR); // LOG
                    //System.out.println("split adding route = " + nextR2); // LOG
                }
            }

            if (currentPosLabel == '-' && currentR.getDirection().getY() != 0) {
                // moving downward or upward causes split otherwise continue traveling
                if (!energizedTiles.contains(currentR)) {
                    //split to the left
                    Route nextR = new Route(new Coordinate(currentR.getCurrentPos().getX() - 1, currentR.getCurrentPos().getY()), new Coordinate(-1, 0));
                    //split to the right
                    Route nextR2 = new Route(new Coordinate(currentR.getCurrentPos().getX() + 1, currentR.getCurrentPos().getY()), new Coordinate(1, 0));

                    energizedTiles.add(currentR);
                    path.add(nextR);
                    path.add(nextR2);

                    //System.out.println("split adding route = " + nextR); // LOG
                    //System.out.println("split adding route = " + nextR2); // LOG
                }
            }

            if (currentPosLabel == '.' 
                || (currentPosLabel == '|' && currentR.getDirection().getX() == 0)
                || (currentPosLabel == '-' && currentR.getDirection().getY() == 0)
                ) 
            {
                //normal movement
                Route nextR = new Route(new Coordinate(currentR.getCurrentPos().getX() + currentR.getDirection().getX(), currentR.getCurrentPos().getY() + currentR.getDirection().getY())
                                                      ,new Coordinate(currentR.getDirection().getX(), currentR.getDirection().getY()));
                if (!energizedTiles.contains(currentR)) {
                    energizedTiles.add(currentR);
                    path.add(nextR);
                }
            }
        }

        // get list of tiles enerigized by beam
        // energizedTiles may contain the same tile visited multiple times from different directions
        ArrayList<Coordinate> retVal = new ArrayList<>();
        for(Route r : energizedTiles){
            //System.out.println(r); // LOG

            if (!retVal.contains(r.getCurrentPos())) {
                retVal.add(r.getCurrentPos());
            }
        }
        return retVal;
    }

    public void countEnergizedTiles(){
        ArrayList<Coordinate> energizedTiles = this.getEnergizedTiles(0, 0, 1, 0);

        // LOG START
        // for(int i = 0; i < this.layout.size(); i++){
        //     StringBuilder line = new StringBuilder();

        //     for(int j = 0; j < this.layout.get(i).size(); j++){
        //         if (energizedTiles.contains(new Coordinate(j, i))) {
        //             line.append('#');
        //         }else {
        //             line.append('.');
        //         }
        //     }
        //     System.out.println(line.toString());
        // }

        // System.out.println("");

        // for(Coordinate c : energizedTiles){
        //     System.out.println(c);
        // }

        // System.out.println("");
        // LOG END

        System.out.println("solution for part 1 = " + energizedTiles.size());
    }

    public void countEnergizedTilesMulti(){
        //solution for part 2 
        //we can start on any tile on edges
        int maxEnergizedTiles = -1;

        //rows
        for(int r = 0; r < this.layout.size(); r++){
            //beam heading to the right from the left edge
            ArrayList<Coordinate> energizedTiles = this.getEnergizedTiles(0, r, 1, 0);

            if (energizedTiles.size() > maxEnergizedTiles) {
                maxEnergizedTiles = energizedTiles.size();
            }

            //beam heading to the left from the right edge
            energizedTiles = this.getEnergizedTiles(this.layout.get(r).size()-1, r, -1, 0);

            if (energizedTiles.size() > maxEnergizedTiles) {
                maxEnergizedTiles = energizedTiles.size();
            }
        }

        //columns
        for(int c = 0; c < this.layout.get(0).size(); c++){
            //beam heading downwards from the top
            ArrayList<Coordinate> energizedTiles = this.getEnergizedTiles(c, 0, 0, 1);

            if (energizedTiles.size() > maxEnergizedTiles) {
                maxEnergizedTiles = energizedTiles.size();
            }

            //beam heading to the upwards from the bottom
            energizedTiles = this.getEnergizedTiles(this.layout.size()-1, c, 0, -1);

            if (energizedTiles.size() > maxEnergizedTiles) {
                maxEnergizedTiles = energizedTiles.size();
            }
        }

        System.out.println("solution for part 2 = " + maxEnergizedTiles);
    }
}
