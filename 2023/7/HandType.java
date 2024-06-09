import java.util.ArrayList;
import java.util.Arrays;

public enum HandType {
     FIVEOFAKIND(new ArrayList<>(Arrays.asList(5)))
    ,FOUROFAKIND(new ArrayList<>(Arrays.asList(1,4)))
    ,FULLHOUSE(new ArrayList<>(Arrays.asList(2,3)))
    ,THREEOFAKIND(new ArrayList<>(Arrays.asList(1,1,3)))
    ,TWOPAIR(new ArrayList<>(Arrays.asList(1,2,2)))
    ,ONEPAIR(new ArrayList<>(Arrays.asList(1,1,1,2)))
    ,HIGHCARD(new ArrayList<>(Arrays.asList(1,1,1,1,1)));

    private ArrayList<Integer> labelCounts;

    HandType(ArrayList<Integer> labelCounts){
        this.labelCounts = new ArrayList<>(labelCounts);
    }

    public ArrayList<Integer> getLabelCounts(){
        return this.labelCounts;
    }
}
