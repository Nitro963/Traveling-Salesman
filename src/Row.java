import java.util.ArrayList;
import java.util.TreeSet;

public class Row implements Cloneable {
    private TreeSet<Exam> examSet;
    int day;

    private Row() {
    }

    public Row(int day) {
        examSet = new TreeSet<>();
        this.day = day;
    }

    private void addExam(Exam e) throws Exception {
        if (e.getSubject().getDay() != day)
            throw new Exception("Day did't match");
        examSet.add(e);
    }

    public ArrayList<Row> generateNextStates(ArrayList<Watcher> watchers, ArrayList<ClassRoom> classRooms, ArrayList<Subject> subjects) {
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Row row = new Row();
        row.examSet = (TreeSet<Exam>) examSet.clone();
        return row;
    }
}
