package domain.moduleTypes;

import java.util.ArrayList;

public abstract class BaseModule implements Propagable{
    private String name;
    public char type;
    private ArrayList<String> destinations;

    public BaseModule(String initData){
        this.destinations = new ArrayList<>();

        String[] initSplit = initData.split("->");
        this.name = initSplit[0].trim();
        this.type = this.name.charAt(0);

        if (this.name.charAt(0) == '%' || this.name.charAt(0) == '&') {
            this.name = this.name.substring(1);
        }

        String[] destSplit = initSplit[1].trim().split(",");

        for(String d : destSplit){
            this.destinations.add(d.trim());
        }
    }

    public String getName(){
        return this.name;
    }

    public char getType(){
        return this.type;
    }

    public ArrayList<String> getDestinations(){
        return this.destinations;
    }

    public String toString(){
        return Character.toString(this.type) + ":" + this.name + " -> " + this.destinations;
    }
}