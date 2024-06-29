import java.util.ArrayList;
import java.util.Collections; 

public class CamelCards {
    private ArrayList<Hand> hands;

    public CamelCards(){
        this.hands = new ArrayList<>();
    }

    public void addHand(String cardLabels, int bidAmount){
        this.hands.add(new Hand(cardLabels, bidAmount));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Hand h : this.hands){
            sb.append(h);
            sb.append("\n");
        }

        return sb.toString();
    }

    public void rankHands(){
        Collections.sort(this.hands);
    }

    public int getTotalWinnings(){
        int totalWinnings = 0;

        for(int i = 0; i < this.hands.size(); i++){
            totalWinnings += (this.hands.get(i).getBidAmount() * (i+1));
        }

        return totalWinnings;
    }
}
