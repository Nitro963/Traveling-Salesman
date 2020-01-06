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

    public static void main(String[] args) throws FileNotFoundException {
        //TSP_State.solve();
        read r = new read();
        HashMap<String, Object> map = r.ReadJson();
/*        Main.ClassRooms = (ArrayList<ClassRoom>) map.get("rooms");
        Main.subjects = (ArrayList<Subject>) map.get("subjects");
        Main.employees = (ArrayList<Employee>) map.get("employees");
        Main.teacherAssistants = (ArrayList<TeacherAssistant>) map.get("teacherAssistants");
        Main.teachers = (ArrayList<Teacher>) map.get("teachers");
        Main.students = (ArrayList<MasterStudent>) map.get("master");
*/

        employees.add(((ArrayList<Employee>) map.get("employees")).get(0));
        subjects.add(((ArrayList<Subject>) map.get("subjects")).get(0));
        teachers.add(((ArrayList<Teacher>) map.get("teachers")).get(0));
        students.add(((ArrayList<MasterStudent>) map.get("master")).get(0));
        ClassRooms.add(((ArrayList<ClassRoom>) map.get("rooms")).get(0));

        System.out.println(employees.get(0));
        System.out.println(teachers.get(0));
        System.out.println(students.get(0));
        System.out.println(subjects.get(0));
        System.out.println(ClassRooms.get(0));
        Table t = Table.solve();
        //System.out.println(t);


    }
}
