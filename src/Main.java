import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    public static ArrayList<Teacher> teachers = new ArrayList<>();
    public static ArrayList<TeacherAssistant> teacherAssistants = new ArrayList<>();
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<MasterStudent> students = new ArrayList<>();

    public static ArrayList<ClassRoom> ClassRooms = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        TSP_State.solve();
    }
}
