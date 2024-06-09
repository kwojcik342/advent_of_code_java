import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Almanac {

    public static void main(String[] args) {

        ArrayList<Long> seeds = new ArrayList<>();
        String curMap = "";
        HashMap<String, AlmanacMap> maps = new HashMap<>();
        ArrayList<String> mappingOrder = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while(sc.hasNextLine()){
                String line = sc.nextLine();

                if(line.contains("seeds:")){
                    String[] strSeeds = line.split(":")[1].trim().split(" ");
                    //System.out.println(Arrays.toString(strSeeds));
                    seeds = Arrays.stream(strSeeds).map(s -> Long.valueOf(s))
                                                   .collect(Collectors.toCollection(ArrayList::new));
                }

                if(line.contains("map")){
                    curMap = line.split(" ")[0];
                    mappingOrder.add(curMap);
                    //System.out.println(curMap);
                    maps.put(curMap, new AlmanacMap(curMap));
                }

                if(!line.isBlank() && !line.contains(":")){
                    String[] mapping = line.split(" ");
                    maps.get(curMap).addMapping(Long.valueOf(mapping[1]), Long.valueOf(mapping[0]), Long.valueOf(mapping[2]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(seeds);

        

        // maps.get("soil-to-fertilizer").sortCompartments();
        // System.out.println(maps.get("soil-to-fertilizer"));

        // maps.get("soil-to-fertilizer").fillEmptyCompartments();

        // System.out.println("");
        // System.out.println("");
        // System.out.println("After filling empty compartments");
        // System.out.println(maps.get("soil-to-fertilizer"));

        for(AlmanacMap am : maps.values()){
            am.sortCompartments();
            am.fillEmptyCompartments();
            // System.out.println(am);
            // System.out.println("");
            // System.out.println("");
        }

        // long seed = seeds.get(0);
        // long valueToMap = seed;
        // for(String mo : mappingOrder){
        //     valueToMap = maps.get(mo).getMapping(valueToMap);
        // }
        
        //Part one
        // long minLocation = -1;
        // for(Long seed : seeds){
        //     long valueToMap = seed;
        //     for(String mo : mappingOrder){
        //         valueToMap = maps.get(mo).getMapping(valueToMap);
        //     }

        //     if (valueToMap < minLocation || minLocation == -1) {
        //         minLocation = valueToMap;
        //     }
        // }
        // System.out.println("minLocation = " + minLocation);


        //Part two
        long minLocation = -1;
        for(int i = 0; i < seeds.size(); i += 2){
            long seed = seeds.get(i);
            for(long s = 0; s < seeds.get(i + 1); s++){
                    long valueToMap = seed + s;
                    for(String mo : mappingOrder){
                        valueToMap = maps.get(mo).getMapping(valueToMap);
                    }

                    if (valueToMap < minLocation || minLocation == -1) {
                        minLocation = valueToMap;
                    }
            }
        }
        System.out.println("minLocation = " + minLocation);
    }
}