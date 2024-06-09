import java.nio.file.Paths;
import java.util.Scanner;


public class CamelGame {

    public static void main(String[] args) {

        CamelCards cc = new CamelCards();

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.isBlank()) {
                    String[] lineSplit = line.split(" ");
                    cc.addHand(lineSplit[0], Integer.valueOf(lineSplit[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cc.rankHands();
        //System.out.println(cc);
        System.out.println("total winnings = " + cc.getTotalWinnings());
    }
}