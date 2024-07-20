package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Coordinate;
import domain.DigInstruction;

public class DigPlan {
    private ArrayList<DigInstruction> instructions;

    public DigPlan(String fileName){
        this.instructions = new ArrayList<>();
        this.loadInstructions(fileName);
    }

    private void loadInstructions(String fileName){
        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {
                    String[] lineSplit = line.split(" ");
                    this.instructions.add(new DigInstruction(lineSplit[0].charAt(0), Integer.valueOf(lineSplit[1]), lineSplit[2]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alternateInstructions(){
        // for part2 instructions are encoded in hex code from colour field
        ArrayList<DigInstruction> altInstructions = new ArrayList<>();

        for(DigInstruction di : this.instructions){
            String hexCode = di.getColour();

            int distance = Integer.parseInt(hexCode.substring(2, 7), 16);

            char direction = 'U';
            if (hexCode.charAt(7) == '0') {
                direction = 'R';
            }
            if (hexCode.charAt(7) == '1') {
                direction = 'D';
            }
            if (hexCode.charAt(7) == '2') {
                direction = 'L';
            }

            altInstructions.add(new DigInstruction(direction, distance, hexCode));
        }

        this.instructions = altInstructions;

        // for(DigInstruction di : this.instructions){
        //     System.out.println(di);
        // }
    }

    private Coordinate getNextVertice(Coordinate c, DigInstruction di){
        long newX = c.getX();
        long newY = c.getY();

        if (di.getDirection() == 'U') {
            newY += di.getDistance();
        }

        if (di.getDirection() == 'D') {
            newY -= di.getDistance();
        }

        if (di.getDirection() == 'L') {
            newX -= di.getDistance();
        }

        if (di.getDirection() == 'R') {
            newX += di.getDistance();
        }

        return new Coordinate(newX, newY);
    }

    private long calculateArea(ArrayList<Coordinate> vertices){
        // calculate area using shoelace formula
        long area = 0;

        long sumA = 0;
        long sumB = 0;

        for(int i = vertices.size() - 1; i >= 0; i--){
            if (i == 0) {
                sumA += (vertices.get(i).getX() * vertices.get(vertices.size() - 1).getY());
                sumB += (vertices.get(i).getY()) * vertices.get(vertices.size() - 1).getX();
            }else{
                sumA += (vertices.get(i).getX() * vertices.get(i-1).getY());
                sumB += (vertices.get(i).getY()) * vertices.get(i-1).getX();
            }
        }

        //area = Math.abs(sumA - sumB) / 2;

        area = (sumA - sumB) / 2;
        if (area < 0) {
            area = area * (-1);
        }

        return area;
    }

    private long calculatePointsInside(long boundaryPoints, long area){
        // calculate points inside polygon using pick's theorem
        return (area - (boundaryPoints/2) + 1);
    }

    public void getArea(boolean alternateInstructions){
        // calculate area of polygon created by following instructions from input
        // using shoelace formula and pick's theorem

        // alternate instructions for part 2
        if (alternateInstructions) {
            this.alternateInstructions();
        }

        long boundaryPoints = 0;

        ArrayList<Coordinate> vertices = new ArrayList<>();
        Coordinate c = new Coordinate(0, 0);

        for(DigInstruction di : this.instructions){
            c = getNextVertice(c, di);
            vertices.add(c);

            // calculate boundary points for pick's theorem
            boundaryPoints += di.getDistance();
        }

        // for(Coordinate v : vertices){
        //     System.out.println(v);
        // }

        long pointsInsidePolygon = this.calculatePointsInside(boundaryPoints, this.calculateArea(vertices));

        if (alternateInstructions) {
            System.out.println("Solution for part 2 = " + (boundaryPoints +  pointsInsidePolygon));
        }else {
            System.out.println("Solution for part 1 = " + (boundaryPoints +  pointsInsidePolygon));
        }
    }
}
