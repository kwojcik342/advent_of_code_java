import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Card {
    private int cardId;
    private int numberOfCopies;
    private ArrayList<Integer> winningNumbers;
    private ArrayList<Integer> cardNumbers;

    public Card(String data){
        String[] cardData = data.split(":");
        this.cardId = Integer.valueOf(cardData[0].replaceAll("( )+", " ").split(" ")[1]);
        //System.out.println("cardData: " + Arrays.toString(cardData));

        this.numberOfCopies = 1;

        String[] numbers = cardData[1].split("\\|");
        String[] strWinnigNumbers = numbers[0].trim().replace("  ", " ").split(" ");
        String[] strCardNumbers = numbers[1].trim().replace("  ", " ").split(" ");

        //System.out.println("numbers: " + Arrays.toString(numbers));
        //System.out.println("strCardNumbers: " + Arrays.toString(strCardNumbers));

        this.winningNumbers = Arrays.stream(strWinnigNumbers).map(s -> Integer.valueOf(s))
                                                             .collect(Collectors.toCollection(ArrayList::new));
        
        this.cardNumbers = Arrays.stream(strCardNumbers).map(s -> Integer.valueOf(s))
                                                        .collect(Collectors.toCollection(ArrayList::new));

        
    }

    public String toString(){
        return "Card " + this.cardId
                + "\nwinnig numbers: " + this.winningNumbers
                + "\ncard numbers: " + this.cardNumbers;
    }

    public int getMatchingNumbersCount(){
        int matchingNumbersCount = 0;

        for(int cn : this.cardNumbers){
            if (this.winningNumbers.contains(cn)) {
                matchingNumbersCount++;
            }
        }

        return matchingNumbersCount;
    }

    public int cardValue(){
        int value = 0;

        for(int i = 0; i < this.getMatchingNumbersCount(); i++){
            if(i == 0){
                value = 1;
            }else{
                value *= 2;
            }
        }

        return value;
    }

    public void increaseCopies(){
        this.numberOfCopies++;
    }

    public int getNumberOfCopies(){
        return this.numberOfCopies;
    }
}
