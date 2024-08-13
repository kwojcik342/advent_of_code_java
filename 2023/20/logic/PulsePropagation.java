package logic;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import domain.moduleTypes.BModule;
import domain.moduleTypes.BaseModule;
import domain.moduleTypes.CModule;
import domain.moduleTypes.FModule;
import domain.moduleTypes.Propagable;
import domain.queue.QPulse;

public class PulsePropagation {

    private HashMap<String, Propagable> mm; // map of all modules
    private Queue<QPulse> ppq; // pulse propagation queue

    public PulsePropagation(String fileName){

        mm = new HashMap<>();
        ppq = new LinkedList<>();

        ArrayList<String> cList = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (!line.isBlank()) {

                    if (line.charAt(0) == 'b') {
                        BModule bm = new BModule(line);
                        mm.put(bm.getName(), bm);
                    }

                    if (line.charAt(0) == '%') {
                        FModule fm = new FModule(line);
                        mm.put(fm.getName(), fm);
                    }

                    if (line.charAt(0) == '&') {
                        CModule cm = new CModule(line);
                        mm.put(cm.getName(), cm);
                        cList.add(cm.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setCmInputs(cList);
    }

    private void setCmInputs(ArrayList<String> cList){
        //initialize source map for each CModule
        if (cList.size() > 0) {

            for(String c : cList){
                CModule cm = (CModule) this.mm.get(c);

                for(Propagable p : this.mm.values()){
                    BaseModule m = (BaseModule) p;
                    if (m.getDestinations().contains(cm.getName())) {
                        cm.addSource(m.getName());
                    }
                }
            }
        }
    }

    public void printModules(){

        for(Propagable p : this.mm.values()){
            BaseModule m = (BaseModule) p;
            String mod = m.toString();

            if (m.getType() == '&'){
                CModule tmp = (CModule) m;
                mod += " mem = " + tmp.mem2String();
            }

            System.out.println(mod);
        }

        System.out.println();
    }

    public void propagate(){
        // solution to part 1 
        // multiply count of all low pulses sent by count of all high pulses sent
        // after pressing button 1000 times
        long lowPulseCount = 0;
        long highPulseCount = 0;

        // at the beggining we sent low pulse from button to broadcaster
        for(int i = 0; i < 1000; i++){
            this.ppq.add(new QPulse("btn", "broadcaster", 0));

            while (true) {

                QPulse p = ppq.remove();

                //System.out.println("Processing queue: " + p.toString()); // LOG

                if (p.getPulse() == 1) {
                    highPulseCount += 1;
                }else {
                    lowPulseCount += 1;
                }

                BaseModule bm = (BaseModule) this.mm.get(p.getDestName());

                if (bm != null) {
                    bm.handlePulse(p.getPulse(), p.getSrcName(), ppq);
                }

                if (ppq.isEmpty()) {
                    break;
                }
            }
        }

        System.out.println("lowPulseCount = " + lowPulseCount);
        System.out.println("highPulseCount = " + highPulseCount);
        System.out.println("solution for part 1 = " + (lowPulseCount*highPulseCount));
    }

    private String getRxInput(){
        // returns name of the module that sends pulse to rx
        String in = "";

        for(Propagable p : this.mm.values()){
            BaseModule bm = (BaseModule) p;
            if (bm.getDestinations().contains("rx")) {
                in = bm.getName();
                break;
            }
        }

        return in;
    }

    private long gcd(long a, long b){
        if (b == 0) {
            return a;
        }
        return this.gcd(b, a%b);
    }

    public void calcLowToRx(){
        // solution to part 2
        // how many button presses to get low pulse on module called rx?

        // rx gets pulse only from 1 module which is conjunction module, named "nc", (it will send low only if all inputs send high)
        // we have to calculate cycle for each input module of "nc" and then calculate least common multiple of those values

        String inRxName = this.getRxInput();
        //System.out.println("module sending input to rx: " + inRxName);
        CModule inRx = (CModule) this.mm.get(inRxName);
        //System.out.println("input names for module before rx: " + inRx.getSource());

        HashMap<String, Long> cycleStart = new HashMap<>();
        HashMap<String, Long> cycleEnd = new HashMap<>();

        long btnPessCnt = 0;

        while (true) {
            //constantly pressing button until we have solution
            btnPessCnt += 1;

            this.ppq.add(new QPulse("btn", "broadcaster", 0));

            while (true) {

                QPulse p = ppq.remove();

                if (p.getDestName().equals(inRxName) && p.getPulse() != 0) {
                    //if pulse is being sent to module before rx we are counting cycles
                    boolean isStart = false;

                    if (!cycleStart.containsKey(p.getSrcName())) {
                        cycleStart.put(p.getSrcName(), btnPessCnt);
                        isStart = true;
                    }

                    if (!isStart) {
                        if (!cycleEnd.containsKey(p.getSrcName())) {
                            cycleEnd.put(p.getSrcName(), btnPessCnt);
                        }
                    }
                }

                if (inRx.getSource().size() == cycleStart.size() && inRx.getSource().size() == cycleEnd.size()) {
                    // if we have start and end of all input cycles we can break
                    break;
                }

                //System.out.println("Processing queue: " + p.toString()); // LOG

                BaseModule bm = (BaseModule) this.mm.get(p.getDestName());

                if (bm != null) {
                    bm.handlePulse(p.getPulse(), p.getSrcName(), ppq);
                }

                if (ppq.isEmpty()) {
                    break;
                }
            }

            if (inRx.getSource().size() == cycleStart.size() && inRx.getSource().size() == cycleEnd.size()) {
                // if we have start and end of all input cycles we can break
                break;
            }
        }

        // System.out.println("cycle start: " + cycleStart);
        // System.out.println("cycle end: " + cycleEnd);

        //calculate cycles lengths
        ArrayList<Long> cycles = new ArrayList<>();

        for(String k : cycleStart.keySet()){
            cycles.add(cycleEnd.get(k) - cycleStart.get(k));
        }

        //System.out.println("cycles: " + cycles);

        //calculate least common multiple for cycles
        // lcm(a,b) = (a*b) / gcd(a,b)
        // gcd(a,b) -> recursively divide bigger number by smaller
        long lcm = cycles.get(0);
        for(int i = 1; i < cycles.size(); i++){
            long a = lcm;
            long b = cycles.get(i);
            lcm = (a*b) / (this.gcd(a, b));
        }

        System.out.println("solution for part 2 = " + lcm);
    }
}
