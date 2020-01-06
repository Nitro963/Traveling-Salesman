import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Main {
    public static ArrayList<Teacher> teachers = new ArrayList<>();
    public static ArrayList<TeacherAssistant> teacherAssistants = new ArrayList<>();
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<MasterStudent> students = new ArrayList<>();

    public static ArrayList<ClassRoom> classRooms = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        read r = new read();
        HashMap<String, Object> map = r.ReadJson();
        Main.classRooms = (ArrayList<ClassRoom>) map.get("rooms");
        Main.subjects = (ArrayList<Subject>) map.get("subjects");
        Main.employees = (ArrayList<Employee>) map.get("employees");
        Main.teacherAssistants = (ArrayList<TeacherAssistant>) map.get("teacherAssistants");
        Main.teachers = (ArrayList<Teacher>) map.get("teachers");
        Main.students = (ArrayList<MasterStudent>) map.get("master");


        Table t = Table.AStar();
        System.out.println(t);

    }
}
