package domain.moduleTypes;

import java.util.Queue;

import domain.queue.QPulse;

public class BModule extends BaseModule{

    public BModule(String initData){
        super(initData);
    }

    @Override
    public void handlePulse(int pulse, String sourceName, Queue<QPulse> q) {
        // propagate pulse to all destinations
        //System.out.println("handling pulse from BModule");
        for(String d : super.getDestinations()){
            q.add(new QPulse(super.getName(), d, pulse));
        }
    }
}