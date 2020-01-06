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
        classRooms = (ArrayList<ClassRoom>) map.get("rooms");
        classRooms.sort(ClassRoom::compareTo);
        subjects = (ArrayList<Subject>) map.get("subjects");
        employees = (ArrayList<Employee>) map.get("employees");
        teacherAssistants = (ArrayList<TeacherAssistant>) map.get("teacherAssistants");
        teachers = (ArrayList<Teacher>) map.get("teachers");
        students = (ArrayList<MasterStudent>) map.get("master");

        Table t = Table.AStar();
        System.out.println(t);

    }
}
