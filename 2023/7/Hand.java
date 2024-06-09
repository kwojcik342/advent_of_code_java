import java.util.ArrayList;
import java.util.HashMap;

public class Hand implements Comparable<Hand>{
    private ArrayList<Integer> cards;
    private int bidAmount;
    private HandType type;

    public Hand(String cardLabels, int bidAmount){
        this.cards = new ArrayList<>();
        this.bidAmount = bidAmount;
        this.fillCards(cardLabels);
        //this.setHandType();//part one of the exercise
        this.setHandTypeJokers();//part two of the exercise
    }

    public void fillCards(String cardLabels){
        for(int i = 0; i < cardLabels.length(); i++){
            if (Character.isDigit(cardLabels.charAt(i))) {
                this.cards.add(Integer.valueOf(String.valueOf(cardLabels.charAt(i))));
            }else{
                if (cardLabels.charAt(i) == 'T') {
                    this.cards.add(10);
                }

                if (cardLabels.charAt(i) == 'J') {
                    //this.cards.add(11);//value 11 for part one of the exercise
                    this.cards.add(1);//value 1 for part two of the exercise
                }

                if (cardLabels.charAt(i) == 'Q') {
                    this.cards.add(12);
                }

                if (cardLabels.charAt(i) == 'K') {
                    this.cards.add(13);
                }

                if (cardLabels.charAt(i) == 'A') {
                    this.cards.add(14);
                }
            }
        }
    }

    public void setHandType(){
        ArrayList<Integer> cardsSorted = new ArrayList<>(this.cards);
        cardsSorted.sort(null);
        HashMap<Integer, Integer> labelCounts = new HashMap<>();

        // System.out.println("");
        // System.out.println("setHandType");
        // System.out.println("cards = " + this.cards);
        // System.out.println("cards sorted = " + cardsSorted);

        int prevLabel = -1;
        int labelCount = 0;
        for(int label : cardsSorted){
            if (label != prevLabel && prevLabel != -1) {
                labelCounts.put(prevLabel, labelCount);
                labelCount = 0;
            }
            prevLabel = label;
            labelCount++;
        }
        labelCounts.put(prevLabel, labelCount);
        ArrayList<Integer> labelCountsSorted = new ArrayList<>(labelCounts.values());
        labelCountsSorted.sort(null);
        //System.out.println("label counts = " + labelCountsSorted);

        for(HandType ht : HandType.values()){
            if (labelCountsSorted.equals(ht.getLabelCounts())) {
                this.type = ht;
                break;
            }
        }
    }

    public void setHandTypeJokers(){
        ArrayList<Integer> cardsSorted = new ArrayList<>(this.cards);
        cardsSorted.sort(null);
        HashMap<Integer, Integer> labelCounts = new HashMap<>();

        // System.out.println("");
        // System.out.println("setHandType");
        // System.out.println("cards = " + this.cards);
        // System.out.println("cards sorted = " + cardsSorted);

        int prevLabel = -1;
        int labelCount = 0;
        for(int label : cardsSorted){
            if (label != prevLabel && prevLabel != -1) {
                labelCounts.put(prevLabel, labelCount);
                labelCount = 0;
            }
            prevLabel = label;
            labelCount++;
        }
        labelCounts.put(prevLabel, labelCount);

        if (labelCounts.containsKey(1) && labelCounts.keySet().size() > 1) {
            int keyWithHighestVal = -1;
            int keyVal = -1;
            //find card label with highest card count
            for(int k : labelCounts.keySet()){
                if((keyWithHighestVal == -1 && k != 1) || (k != 1 && labelCounts.get(k) > labelCounts.get(keyWithHighestVal))){
                    keyWithHighestVal = k;
                    keyVal = labelCounts.get(k);
                }
            }
            //replace jokers with best key to determine hand type
            keyVal += labelCounts.get(1);
            labelCounts.put(keyWithHighestVal, keyVal);
            labelCounts.remove(1);
        }

        ArrayList<Integer> labelCountsSorted = new ArrayList<>(labelCounts.values());
        labelCountsSorted.sort(null);
        //System.out.println("label counts = " + labelCountsSorted);

        for(HandType ht : HandType.values()){
            if (labelCountsSorted.equals(ht.getLabelCounts())) {
                this.type = ht;
                break;
            }
        }

        if (this.type == null) {
            System.out.println(this.cards);
        }
    }

    public HandType getHandType(){
        return this.type;
    }

    public int getBidAmount(){
        return this.bidAmount;
    }

    public String toString(){
        return this.cards + " " + this.type + " " + this.bidAmount;
    }

    @Override
    public int compareTo(Hand compared) {
        int compare = this.type.compareTo(compared.type);

        if (compare == 0) {
            for(int i = 0; i < this.cards.size(); i++){
                if(this.cards.get(i) > compared.cards.get(i)){
                    return 1;
                }

                if(this.cards.get(i) < compared.cards.get(i)){
                    return -1;
                }
            }
            return 0;
        }else{
            return -compare;
        }
    }
}
