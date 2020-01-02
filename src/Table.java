import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Table implements Comparable<Table> {
    private ArrayList<ExamDay> examDays;
    private int g;
    private int f;
    private ArrayList<Pair<Watcher, String>> ConstrainBreak;

    public Table() {
        examDays = new ArrayList<>();
        g = f = 0;
    }

    public Table(ArrayList<ExamDay> examDays) {
        this.examDays = examDays;
        g = f = 0;
    }

    public Table(ArrayList<ExamDay> examDays, int cost) {
        this.examDays = examDays;
        this.g = cost;
    }

    public static ArrayList<Table> init(ArrayList<Watcher> watchers, ArrayList<ClassRoom> classRooms) {
        ArrayList<ExamDay> examDays = new ArrayList<>();
        ArrayList<Table> ret = new ArrayList<>();
        for (ClassRoom room : classRooms) {
            ExamDay examDay = new ExamDay(1);
            examDay.addExam(new Exam(room));
            examDays.add(examDay);
        }

        Queue<Teacher> teacherQueue = new LinkedList<>();
        Queue<TeacherAssist> teacherAssistQueue = new LinkedList<>();
        Queue<MasterStudent> masterStudentQueue = new LinkedList<>();
        Queue<Employee> employeeQueue = new LinkedList<>();
        for (Watcher w : watchers) {
            if (w instanceof Teacher)
                teacherQueue.add((Teacher) w);
            else if (w instanceof TeacherAssist)
                teacherAssistQueue.add((TeacherAssist) w);
            else if (w instanceof MasterStudent)
                masterStudentQueue.add((MasterStudent) w);
            else
                employeeQueue.add((Employee) w);
        }

        return ret;
    }

    public ArrayList<Pair<Watcher, String>> getConstrainBreak() {
        return ConstrainBreak;
    }

    public boolean isFinal() {
        return false;
    }

    public ArrayList<Table> generateNextTables() {
        ArrayList<Table> ret = new ArrayList<>();

        return ret;
    }

    public ArrayList<ExamDay> getExamDays() {
        return examDays;
    }

    public int getG() {
        return g;
    }

    public int getF() {
        return f;
    }

    @Override
    public int compareTo(Table o) {
        return g - o.g;
    }
}
