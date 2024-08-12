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
}
