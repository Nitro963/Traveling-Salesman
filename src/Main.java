import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    public static ArrayList<Teacher> teachers = new ArrayList<>();
    public static ArrayList<TeacherAssistant> teacherAssistants = new ArrayList<>();
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<MasterStudent> students = new ArrayList<>();

    public static ArrayList<ClassRoom> ClassRooms = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();

    /* public static Constrain buildConstrain(Scanner cin) {
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
 */
    public static void main(String[] args) throws FileNotFoundException {
        //TSP_State.solve();
        read r = new read();
        HashMap<String, Object> map = r.ReadJson();
        Main.ClassRooms = (ArrayList<ClassRoom>) map.get("rooms");
        Main.subjects = (ArrayList<Subject>) map.get("subjects");
        Main.employees = (ArrayList<Employee>) map.get("employees");
        Main.teacherAssistants = (ArrayList<TeacherAssistant>) map.get("teacherAssistants");
        Main.teachers = (ArrayList<Teacher>) map.get("teachers");
        Main.students = (ArrayList<MasterStudent>) map.get("master");


        System.out.println(Main.teachers);
        System.out.println(Main.students);


    }
}
