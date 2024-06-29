import java.nio.file.Paths;
import java.util.LinkedList; 
import java.util.Scanner;

public class Scrachcards {
    public static void main(String[] args) {
        // Card c = new Card("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
        // System.out.println(c);

        LinkedList<Card> cards = new LinkedList<>();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //System.out.println("line = " + line);
                cards.add(new Card(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // for(Card c : cards){
        //     System.out.println(c);
        //     System.out.println("");
        // }

        int cardsValue = 0;
        for(Card c : cards){
            cardsValue += c.cardValue();
        }
        System.out.println("cardsValue = " + cardsValue);

        int cIdx = 0;
        int sumCards = 0;
        for(Card c : cards){
            sumCards += c.getNumberOfCopies();

            int curMatchingNumbers = c.getMatchingNumbersCount();
            for(int copy = 0; copy < c.getNumberOfCopies(); copy++){
                for(int num = 1; num <= curMatchingNumbers; num++){
                    if ((cIdx + num) > (cards.size() - 1)) {
                        break;
                    }

                    cards.get(cIdx + num).increaseCopies();
                }
            }

            cIdx++;
        }
        System.out.println("sumCards = " + sumCards);
    }
}