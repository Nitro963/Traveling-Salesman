import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
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
        Scanner cin = new Scanner(new BufferedInputStream(new FileInputStream("input.txt")));

        int n = cin.nextInt();
        for (int i = 0; i < n; i++)
            ClassRooms.add(new ClassRoom(cin.next(), cin.nextInt(), cin.nextInt(), cin.nextInt()));
        System.out.println(ClassRooms);

        int m = cin.nextInt();
        for (int i = 0; i < m; i++) {
            int t = cin.nextInt();
            Watcher w = null;
            switch (t) {
                case 1: {
                    w = new Teacher(cin.next(), buildConstrain(cin));
                    break;
                }
                case 2: {
                    w = new TeacherAssist(cin.next(), buildConstrain(cin));
                    break;
                }
            }
            Watchers.add(w);
        }

        UFS();

    }


    public static Table UFS() {
        PriorityQueue<Table> pq = new PriorityQueue<>();
        pq.addAll(Table.init(Watchers, ClassRooms));

        return null;
    }

}
