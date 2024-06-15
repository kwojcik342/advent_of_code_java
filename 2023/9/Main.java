import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int extrapolatedSum = 0;

        try (Scanner sc = new Scanner(Paths.get("input.txt"))) {
            while (sc.hasNextLine()) {
                Reading r = new Reading(sc.nextLine());
                r.prepareHistory();
                extrapolatedSum += r.extrapolate(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("extrapolatedSum = " + extrapolatedSum);
    }
}