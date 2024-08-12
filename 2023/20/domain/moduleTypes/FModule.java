package domain.moduleTypes;

import java.util.Queue;

import domain.queue.QPulse;

public class FModule extends BaseModule{
    private int state;

    public FModule(String initData){
        super(initData);
        this.state = 0;
    }

    @Override
    public void handlePulse(int pulse, String sourceName, Queue<QPulse> q) {
        //System.out.println("handling pulse from FModule");
        if (pulse == 0) {

            int propagatePulse = 0;

            if (this.state == 0) {
                this.state = 1;
                propagatePulse = 1;//propagate 1 to destinations
            }else {
                this.state = 0;
                //propagate 0 to destinations
            }

            for(String d : super.getDestinations()){
                q.add(new QPulse(super.getName(), d, propagatePulse));
            }
        }
    }
}
