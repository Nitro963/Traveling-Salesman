import java.util.TreeSet;

public class ExamDay implements Cloneable {
    private TreeSet<Exam> examSet;
    private int day;

    private ExamDay() {
        examSet = new TreeSet<>();
    }

    public ExamDay(int day) {
        examSet = new TreeSet<>();
        this.day = day;
    }

    public void addExam(Exam e) {
        examSet.add(e);
    }

    public TreeSet<Exam> getExamSet() {
        return examSet;
    }

    public void setExamSet(TreeSet<Exam> examSet) {
        this.examSet = examSet;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ExamDay examDay = new ExamDay();
        examDay.examSet = (TreeSet<Exam>) examSet.clone();
        return examDay;
    }
}
