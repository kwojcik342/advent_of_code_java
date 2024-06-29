import java.util.ArrayList;

public class ReflectionPattern {
    private ArrayList<ArrayList<Integer>> rows;
    private ArrayList<ArrayList<Integer>> columns;

    public ReflectionPattern(){
        this.rows = new ArrayList<>();
        this.columns = new ArrayList<>();
    }

    public void addLine(String line){
        //add line from input file to object
        this.rows.add(new ArrayList<>());

        for(int i = 0; i < line.length(); i++){
            if (this.columns.size() < (i + 1)) {
                this.columns.add(new ArrayList<>());
            }

            if (line.charAt(i) == '#') {
                this.rows.get(this.rows.size()-1).add(1);
                this.columns.get(i).add(1);
            }
            if (line.charAt(i) == '.') {
                this.rows.get(this.rows.size()-1).add(0);
                this.columns.get(i).add(0);
            }
        }
    }

    public void print(){
        System.out.println("rows: " + this.rows.size());
        System.out.println("columns: " + this.columns.size());

        System.out.println("based on rows:");
        for(ArrayList<Integer> r : this.rows){
            StringBuilder sbR = new StringBuilder();
            for(int i : r){
                sbR.append(i);
            }
            System.out.println(sbR.toString());
        }

        System.out.println();
        System.out.println("based on columns:");
        for(int i = 0; i < this.rows.size(); i++){
            StringBuilder sbC = new StringBuilder();
            for(ArrayList<Integer> c : this.columns){
                sbC.append(c.get(i));
            }
            System.out.println(sbC.toString());
        }
        System.out.println();
    }

    public boolean compareWithSmudge(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
        //compare 2 arrays of equal size
        //allows for 1 difference

        boolean smudgeFound = false;

        for(int i = 0; i < arr1.size(); i++){
            if (arr1.get(i) != arr2.get(i)) {

                if (smudgeFound) {
                    return false;
                }

                if (!smudgeFound) {
                    smudgeFound = true;
                }
            }
        }
        return true;
    }

    private int getReflectionHorizontal(boolean checkSmudge){
        //check if this pattern has horizontal reflection line

        //checkSmudge = true for part 2
        //in part 2 each patter has exactly 1 smudge
        //exactly one . or # should be the opposite

        int rowIdx = -1; // not found

        for(int line = 1; line < this.rows.size(); line++){   //potential reflection line
            boolean reflectionFound = true;
            boolean smudgeFixed = false;

            //iterate forwards and backwards from potential reflection line
            for(int row = line, comparedRow = line-1; row < this.rows.size() && comparedRow >= 0; row++, comparedRow--){

                if (!this.rows.get(row).equals(this.rows.get(comparedRow))) {

                    if (!checkSmudge) {
                        reflectionFound = false;
                        break;
                    }

                    if (checkSmudge) {
                        
                        if (smudgeFixed) { 
                            //smudge has already been fixed this relection line is not valid
                            reflectionFound = false;
                            break;
                        }

                        if (!smudgeFixed) {
                            if (this.compareWithSmudge(this.rows.get(row), this.rows.get(comparedRow))) {
                                smudgeFixed = true;
                            }else {
                                reflectionFound = false;
                                break;
                            }
                        }
                    }
                }
            }

            if (checkSmudge && !smudgeFixed) {
                reflectionFound = false;
            }

            if (reflectionFound) {
                return line;
            }
        }

        return rowIdx;
    }

    private int getReflectionVertical(boolean checkSmudge){
        //check if this pattern has vertical reflection line 

        //checkSmudge = true for part 2
        //in part 2 each patter has exactly 1 smudge
        //exactly one . or # should be the opposite

        int colIdx = -1; // not found

        for(int line = 1; line < this.columns.size(); line++){   //potential reflection line
            boolean reflectionFound = true;
            boolean smudgeFixed = false;

            //iterate forwards and backwards from potential reflection line
            for(int col = line, comparedCol = line-1; col < this.columns.size() && comparedCol >= 0; col++, comparedCol--){
                if (!this.columns.get(col).equals(this.columns.get(comparedCol))) {
                    if (!checkSmudge) {
                        reflectionFound = false;
                        break;
                    }

                    if (checkSmudge) {
                        
                        if (smudgeFixed) { 
                            //smudge has already been fixed this relection line is not valid
                            reflectionFound = false;
                            break;
                        }

                        if (!smudgeFixed) {
                            if (this.compareWithSmudge(this.columns.get(col), this.columns.get(comparedCol))) {
                                smudgeFixed = true;
                            }else {
                                reflectionFound = false;
                                break;
                            }
                        }
                    }
                }
            }

            if (checkSmudge && !smudgeFixed) {
                reflectionFound = false;
            }

            if (reflectionFound) {
                return line;
            }
        }

        return colIdx;
    }

    public int reflectionSummary(boolean checkSmudge){
        //return result for this pattern (Part1)
        int summary = this.getReflectionHorizontal(checkSmudge) * 100;

        if (summary == -100) {
            summary = this.getReflectionVertical(checkSmudge);
        }

        if (summary < 0) {
            System.out.println("no reflection line found");
        }

        return summary;
    }

    


}
