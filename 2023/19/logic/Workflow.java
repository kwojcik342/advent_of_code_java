package logic;

import java.util.ArrayList;

import domain.Part;
import domain.Rule;

public class Workflow {
    private String name;
    private ArrayList<Rule> rules;
    private String defaultResult;

    public Workflow(String definition){
        this.rules = new ArrayList<>();

        this.name = definition.substring(0, definition.indexOf('{'));
        String[] ruleSet = definition.substring(definition.indexOf('{') + 1, definition.indexOf('}')).split(",");

        for(int i = 0; i < ruleSet.length - 1; i++){
            this.rules.add(new Rule(ruleSet[i]));
        }

        this.defaultResult = ruleSet[ruleSet.length - 1];
    }

    public String toString(){
        String s = this.name + "{";

        for(Rule r : this.rules){
            s += r.toString() + ",";
        }

        s += (this.defaultResult + "}");

        return s;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Rule> getRules(){
        return this.rules;
    }

    public String getDefaultResult(){
        return this.defaultResult;
    }

    public String processPart(Part p){
        String result = this.defaultResult;

        for(Rule r : this.rules){
            String rResult = r.checkPart(p);

            if (!rResult.equals("NEGATIVE")) {
                result = rResult;
                break;
            }
        }

        return result;
    }

}
