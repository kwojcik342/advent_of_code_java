package domain;

public class Rule {
    private char checkedValue;
    private char condition;
    private int comparedValue;
    private String processingResult;
    private String negativeResult;

    public Rule(String definition){
        this.checkedValue = definition.charAt(0);
        this.condition = definition.charAt(1);
        this.comparedValue = Integer.valueOf(definition.substring(2, definition.indexOf(':')));
        this.processingResult = definition.substring(definition.indexOf(':') + 1);
        this.negativeResult = "NEGATIVE";
    }

    public Rule(Rule r){
        this.checkedValue = r.checkedValue;
        this.condition = r.condition;
        this.comparedValue = r.comparedValue;
        this.processingResult = r.processingResult;
    }

    public String getProcessingResult(){
        return this.processingResult;
    }

    public String toString(){
        return String.valueOf(this.checkedValue) + String.valueOf(this.condition) + this.comparedValue + ":" + this.processingResult;
    }

    public String checkPart(Part p){
        String result = this.negativeResult;

        int testValue = -1;

        if (this.checkedValue == 'x') {
            testValue = p.getX();
        }

        if (this.checkedValue == 'm') {
            testValue = p.getM();
        }

        if (this.checkedValue == 'a') {
            testValue = p.getA();
        }

        if (this.checkedValue == 's') {
            testValue = p.getS();
        }

        if (this.condition == '<' && testValue < this.comparedValue) {
            result = this.processingResult;
        }

        if (this.condition == '>' && testValue > this.comparedValue) {
            result = this.processingResult;
        }

        return result;
    }

}
