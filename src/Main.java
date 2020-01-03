import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static ArrayList<Watcher> Watchers = new ArrayList<>();
    public static ArrayList<ClassRoom> ClassRooms = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();


    public static Constrain buildConstrain(Scanner cin) {
        Constrain constrain = new Constrain();
        int k = cin.nextInt();
        while (k-- != 0) {
            char c = cin.next().charAt(0);
            switch (c) {
                case '@': {
                    String s = cin.next();
                    for (int j = 0; j < s.length(); j++)
                        if (s.charAt(j) == '1')
                            constrain.setAvailableAtDay(j + 1);
                        else
                            constrain.unSetAvailableAtDay(j + 1);
                    break;
                }
                case 'p': {
                    String s = cin.next();
                    for (int j = 0; j < s.length(); j++)
                        if (s.charAt(j) == '1')
                            constrain.setPreferDay(j + 1);
                        else
                            constrain.unSetPreferDay(j + 1);
                    break;
                }
            }
        }
        return constrain;
    }

    public static void main(String[] args) throws FileNotFoundException {
        State.solve();
    }
}
