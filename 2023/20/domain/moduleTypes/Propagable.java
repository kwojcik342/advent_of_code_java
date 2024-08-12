package domain.moduleTypes;

import java.util.Queue;

import domain.queue.QPulse;

public interface Propagable {
    public void handlePulse(int pulse, String sourceName, Queue<QPulse> q);
}
