package domain.moduleTypes;

import java.util.HashMap;
import java.util.Queue;

import domain.queue.QPulse;

public class CModule extends BaseModule{
    private HashMap<String, Integer> srcToPulseMem; // pulse value for each source this is connected to

    public CModule(String initData){
        super(initData);
        this.srcToPulseMem = new HashMap<>();
    }

    public void addSource(String sourceName){
        this.srcToPulseMem.put(sourceName, 0);
    }

    public void printMem(){
        for(String k : this.srcToPulseMem.keySet()){
            System.out.println(k + ":" + this.srcToPulseMem.get(k));
        }
    }

    public String mem2String(){
        StringBuilder sb = new StringBuilder();
        for(String k : this.srcToPulseMem.keySet()){
            sb.append(k + ":" + this.srcToPulseMem.get(k) + ", ");
        }

        return sb.toString();
    }

    @Override
    public void handlePulse(int pulse, String sourceName, Queue<QPulse> q) {
        //System.out.println("handling pulse from CModule");

        this.srcToPulseMem.replace(sourceName, pulse);

        int propagatePulse = 0;

        if (this.srcToPulseMem.containsValue(0)) {
            // propagate 1 to destinations
            propagatePulse = 1;
        }

        for(String d : super.getDestinations()){
            q.add(new QPulse(super.getName(), d, propagatePulse));
        }
    }
}
