import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator; 
import java.util.LinkedList;

public class AlmanacMap {
    String mapName;
    private LinkedList<Compartment> alMap;

    public AlmanacMap(String mapName){
        this.mapName = mapName;
        this.alMap = new LinkedList<>();
    }

    public void addMapping(long source, long destination, long length){
        this.alMap.add(new Compartment(source, destination, length));
    }

    public String toString(){
        String ret = "mapping " + this.mapName;

        for(Compartment c : this.alMap){
            ret += ("\n" + c);
        }

        return ret;
    }

    public void sortCompartments(){
        Collections.sort(this.alMap);
    }

    public long getMappingForEmptyCompartment(long value){
        long mapping = 0;
        int idxBeforeEmpty = 0;

        for(Compartment c : this.alMap){
            if (value < c.getMinSrcValue()) {
                break;
            }
            idxBeforeEmpty++;
        }

        int idx = 0;
        for(Compartment c : this.alMap){
            if (idx > 0) {
                if (c.getMinSrcValue() > this.alMap.get(idx-1).getMaxSrcValue()+1) {
                    
                }
            }

            idx++;
        }

        return mapping;
    }

    public void fillEmptyCompartments(){
        int idx = 0;

        LinkedList<Compartment> alMapDstSort = new LinkedList<>(this.alMap);
        Comparator<Compartment> compDest = Comparator.comparing(Compartment::getMinDstValue);
        Collections.sort(alMapDstSort, compDest);

        // System.out.println(this.alMap);
        // System.out.println("");
        // System.out.println(alMapDstSort);

        long minDstValue = 0;
        int idxDst = 0;
        LinkedList<Compartment> alMapBlanks = new LinkedList<>();

        for(Compartment c : this.alMap){
            long prevMaxSrcValue = 0;

            if (idx > 0) {
                prevMaxSrcValue = this.alMap.get(idx-1).getMaxSrcValue()+1;
            }

            if (c.getMinSrcValue() > prevMaxSrcValue) {
                long minSrcValue = prevMaxSrcValue;
                long requiredSize = c.getMinSrcValue() - minSrcValue;

                for(int id = idxDst; id < alMapDstSort.size(); id++){

                    if (requiredSize <= 0) {
                        break;
                    }

                    long curMinDstValue = alMapDstSort.get(id).getMinDstValue();
                    if(minDstValue < curMinDstValue){
                        //mamy miejsce przed obecnym przedziałem
                        long availableSize = curMinDstValue - minDstValue;
                        if (availableSize > requiredSize) {
                            availableSize = requiredSize;
                        }

                        alMapBlanks.add(new Compartment(minSrcValue, minDstValue, availableSize));

                        minSrcValue += availableSize;
                        requiredSize -= availableSize;
                        minDstValue += availableSize;
                    }else{
                        //nie ma miejsca przed obecnym przedziałem
                        minDstValue = alMapDstSort.get(id).getMaxDstValue() + 1;
                    }
                    idxDst++;
                }
            }

            idx++;
        }

        this.alMap.addAll(alMapBlanks);
        this.sortCompartments();
    }

    public long getMapping(long value){
        long mapping = 0;
        boolean mappingFound = false;

        //System.out.println("mapping " + this.mapName);

        for(Compartment c : this.alMap){
            if (value >= c.getMinSrcValue() && value <= c.getMaxSrcValue()) {
                mapping = c.getDstForSrc(value);
                mappingFound = true;
                //System.out.println("mapping found: " + mapping);
                break;
            }
        }

        // if (!mappingFound) {
        //     mapping = getMappingForEmptyCompartment(value);
        // }
        
        return mapping;
    }
}
