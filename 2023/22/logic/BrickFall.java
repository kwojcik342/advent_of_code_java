package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import domain.Brick;

public class BrickFall {
    private ArrayList<Brick> bricks;

    public BrickFall(String fileName){
        this.bricks = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {
                    this.bricks.add(new Brick(line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBricks(){
        for(Brick b : this.bricks){
            System.out.println(b);
        }
        System.out.println();
    }

    private void sortBricks(){
        Collections.sort(this.bricks);
    }

    private void finishFall(){
        ArrayList<Brick> bricksAfter = new ArrayList<>();

        for(int i = 0; i<this.bricks.size(); i++){
            Brick b = this.bricks.get(i);

            if (b.getMinZ() == 1) {
                // at the time of snapshot brick was on the ground
                bricksAfter.add(new Brick(b));
                continue;
            }

            int maxZ = 1; // maximum level current brick can fall to

            //System.out.println("falling brick = " + b); // LOG

            // if brick was above the groud
            // check if there is another brick below it
            for(Brick ba : bricksAfter){
                if (ba.isOverlapping(b)) {

                    //System.out.println("brick under falling = " + ba); // LOG

                    if (ba.getMaxZ() > maxZ) {
                        // there can be multiple bricks under current one and all of them can have different height
                        maxZ = ba.getMaxZ();
                    }
                }
            }

            //System.out.println("highest under = " + maxZ); // LOG
            //System.out.println(); // LOG

            //adjust level(Z coordinate) of currently processed brick
            bricksAfter.add(new Brick(b));
            bricksAfter.get(bricksAfter.size()-1).setZ(maxZ + 1);
        }

        this.bricks = bricksAfter;
    }

    public void countDisintegration(){
        // solution to part 1

        // input consits of bricks that havent reached the groud yet
        // first we have to calculate where they will end up

        // sorting bricks by z axis so we can process each layer
        this.sortBricks();

        // calculate where bricks, that were above ground at the time of snapshot, would fall 
        this.finishFall();

        // how many bricks can we disintegrate?
        int bricksCount = 0;

        // brick can be disintegrated if it doesnt's support other bricks
        // or bricks that are supported by it are also supported by other brick
        for(int i = 0; i < this.bricks.size(); i++){
            Brick b = this.bricks.get(i);

            boolean bAboveNoSup = false;
            // check bricks level above
            for(int j = 0; j < this.bricks.size(); j++){
                Brick ba = this.bricks.get(j);

                if (ba.getMinZ() <= b.getMaxZ()) {
                    //same level or below
                    continue;
                }

                if (ba.getMinZ() >= b.getMaxZ() + 2) {
                    //brick more than 1 level above must be supported by other bricks
                    continue;
                }

                if (b.isOverlapping(ba)) {
                    // bricks are touching, check if that brick is supported by other bricks
                    boolean hasOtherSupp = false;

                    for(int k = 0; k < this.bricks.size(); k++){

                        if (k != i && k != j) {
                            // don't check bricks we are currently analyzing
                            Brick bu = this.bricks.get(k);

                            if (bu.getMaxZ() == ba.getMinZ() - 1) {
                                // brick on the level we want to check
                                if (bu.isOverlapping(ba)) {
                                    // we found other support
                                    hasOtherSupp = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!hasOtherSupp) {
                        bAboveNoSup = true;
                    }
                }

                if (bAboveNoSup) {
                    // found at lest one brick with no other support
                    break;
                }
            }

            if (!bAboveNoSup) {
                bricksCount++;
            }
        }

        System.out.println("Solution for part 1 = " + bricksCount);
    }
}
