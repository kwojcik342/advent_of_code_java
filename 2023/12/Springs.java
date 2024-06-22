import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Springs {

    private String labels;
    private ArrayList<Integer> groups;

    public Springs(String line){
        this.labels = line.split(" ")[0];
        this.groups = Arrays.stream(line.split(" ")[1].split(",")).map(s -> Integer.valueOf(s)).collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isBracketOob(int startPos, int endPos){
        if (startPos < 0 || endPos > (this.labels.length() - 1)) {
            return true;
        }

        return false;
    }

    private boolean isBracketValidGroup(int startPos, int endPos){
        //bracket outside line
        if (startPos < 0 || endPos > (this.labels.length() - 1)) {
            return false;
        }

        //char before must separate group
        if (startPos > 0) {
            if(this.labels.charAt(startPos-1) == '#'){
                return false;
            }
        }

        //char after must separate group
        if (endPos < this.labels.length() - 1) {
            if (this.labels.charAt(endPos + 1) == '#') {
                return false;
            }
        }

        //valid group can contain # or ?
        for(int i = startPos; i <= endPos; i++){
            if (this.labels.charAt(i) == '.') {
                return false;
            }
        }

        return true;
    }

    private boolean validateAllBrackets(int[][] brackets){

        if (brackets.length == 1) {
            if (this.isBracketValidGroup(brackets[0][0], brackets[0][1])) {
                return true;
            }
        }

        if (brackets.length > 1) {
            for(int i = 0; i < brackets.length; i++){
                if (!this.isBracketValidGroup(brackets[i][0], brackets[i][1])) {
                    return false;
                }

                //brackets cannot be neighbors
                if (i < brackets.length - 1) {
                    if (brackets[i][1] == brackets[i+1][0] - 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isLineOutsideBracketsValid(int[][] brackets){

        HashSet<Integer> bracketIdxs = new HashSet<>();

        for(int i = 0; i < brackets.length; i++){
            for(int j = brackets[i][0]; j <= brackets[i][1]; j++){
                bracketIdxs.add(j);
            }
        }

        //System.out.println("bracketIdxs: " + bracketIdxs);

        //characters outside brackets cannot be hashes
        for(int i = 0; i < this.labels.length(); i ++){
            if (!bracketIdxs.contains(i)) {
                if (this.labels.charAt(i) == '#') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isRoomForSubsequentBrackets(int groupIdx, int groupEndPos){

        //System.out.println("isRoomForSubsequentBrackets " + groupIdx + ", " + groupEndPos);

        if(groupIdx < this.groups.size() - 1){
            int remainingGroupsLength = 0;

            for(int i = groupIdx + 1; i < this.groups.size(); i++){
                remainingGroupsLength += this.groups.get(i);
            }

            //System.out.println("\t remainingGroupsLength = " + remainingGroupsLength);

            if ((groupEndPos + remainingGroupsLength) < this.labels.length()) {
                //System.out.println("\t return true");
                return true;
            }
        }

        //System.out.println("\t return false");
        return false;
    }

    public int possibleArrangements(){
        int retVal = 0;

        //System.out.println(this.labels);
        //System.out.println(this.groups);

        //the idea is to split line into brackets
        //then move brackets to the right checking if characters in brackets are valid solution
        //for example "???.###" with groups 1,1,3 -> "[?][?][.##]#"

        int[][] brackets = new int[this.groups.size()][2];//[group index][bracket start, bracket finish]

        //modifiers used for moving brackets around
        int[] groupStartPosMod = new int[this.groups.size()];
        for(int i = 0; i < this.groups.size(); i ++){
            groupStartPosMod[i] = 0;
        }

        

        while (true) {

            int prevBracketEnd = -1;

            //boolean allBracketsValid = true;
            for(int i = 0; i < this.groups.size(); i++){

                brackets[i][0] = prevBracketEnd + 1 + groupStartPosMod[i];
                brackets[i][1] = brackets[i][0] + (this.groups.get(i) - 1);
                prevBracketEnd = brackets[i][1];

                // if (!this.isBracketValidGroup(brackets[i][0], brackets[i][1])) {
                //     allBracketsValid = false;
                // }
            }

            //log start
            // System.out.println("\n\nbrackets:");
            // for(int i = 0; i < brackets.length; i++){
            //     System.out.println(brackets[i][0] + " - " + brackets[i][1]);
            // }
            //log end

            //is last bracket outside of bounds
            if (this.isBracketOob(brackets[this.groups.size()-1][0], brackets[this.groups.size()-1][1])) {
                groupStartPosMod[this.groups.size() - 1] = 0;

                //move brackets before last one checking if that would leave room for subsequent brackets
                boolean prevBracketMovePossible = false;
                for(int i = this.groups.size() - 2; i >= 0; i--){
                    if(isRoomForSubsequentBrackets(i, (brackets[i][1] + 1))){
                        groupStartPosMod[i] += 1;
                        prevBracketMovePossible = true;
                        break;
                    }

                    groupStartPosMod[i] = 0;
                }

                if (!prevBracketMovePossible) {
                    break;
                }
            }
            groupStartPosMod[this.groups.size() - 1] += 1;

            // if (!allBracketsValid) {
            //     continue;
            // }
            if (!this.validateAllBrackets(brackets)) {
                continue;
            }

            if(this.isLineOutsideBracketsValid(brackets)){
                //System.out.println("correct bracket");
                retVal++;
            }

            //break;
        }

        return retVal;
    }

    private void unfold(){

        int unfoldingQuantity = 5;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < unfoldingQuantity; i++){
            sb.append(this.labels);

            if (i < unfoldingQuantity - 1) {
                sb.append("?");
            }
        }

        this.labels = sb.toString();
        System.out.println(this.labels);

        ArrayList<Integer> unfGroups = new ArrayList<>();
        for(int i = 0; i < unfoldingQuantity; i++){
            unfGroups.addAll(this.groups);
        }

        this.groups = unfGroups;
        System.out.println(this.groups);
    }

    public int possibleArrangementsUnfolded(){
        //solution for part 2
        this.unfold();

        return this.possibleArrangements();
    }
}

