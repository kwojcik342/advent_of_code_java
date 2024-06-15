import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Reading {
    private ArrayList<Integer> initialValues;
    private ArrayList<ArrayList<Integer>> sequences;

    public Reading(String readingHistory){
        String[] histSplit = readingHistory.split(" ");
        this.initialValues = Arrays.stream(histSplit).map(s -> Integer.valueOf(s))
                                                     .collect(Collectors.toCollection(ArrayList::new));
        
        this.sequences = new ArrayList<>();
    }

    public void prepareHistory(){
        this.sequences.add(new ArrayList<>(this.initialValues));
        int sequencesIdx = 0;

        //System.out.println("initial values: " + this.sequences.get(sequencesIdx));

        //System.out.println("");
        //System.out.println("prep rows:");
        while (true) {
            this.sequences.add(new ArrayList<>());
            sequencesIdx += 1;
            boolean isRowZero = true;

            for(int i = 1; i < this.sequences.get(sequencesIdx - 1).size(); i++){
                int valDiff = this.sequences.get(sequencesIdx - 1).get(i) - this.sequences.get(sequencesIdx - 1).get(i - 1);
                this.sequences.get(sequencesIdx).add(valDiff);
                if (valDiff != 0) {
                    isRowZero = false;
                }
            }

            //System.out.println(this.sequences.get(sequencesIdx));

            if (isRowZero) {
                break;
            }

        }
    }

    public int extrapolate(boolean isReverse){
        //isReverse = flase for part 1
        //isReverse = true for part 2
        int retVal = -1;

        for(int j = this.sequences.size() - 2; j >= 0; j--){
            int prevRowIdx = 0;
            int curRowIdx = 0;
            int curRowVal = 0;

            if (!isReverse) {
                prevRowIdx = this.sequences.get(j+1).size() - 1;
                curRowIdx = this.sequences.get(j).size() - 1;
                curRowVal = this.sequences.get(j).get(curRowIdx) + this.sequences.get(j+1).get(prevRowIdx);
                this.sequences.get(j).add(curRowVal);
            }else{
                curRowVal = this.sequences.get(j).get(curRowIdx) - this.sequences.get(j+1).get(prevRowIdx);
                this.sequences.get(j).add(0, curRowVal);;
            }
            
            //System.out.println(this.sequences.get(j));

            if (j == 0) {
                retVal = curRowVal;
            }
        }

        return retVal;
    }
}
