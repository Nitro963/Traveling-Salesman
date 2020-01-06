import java.io.Serializable;
import java.util.Objects;

public class Subject implements Comparable<Subject>, Cloneable, Serializable {
    private String name;
    private int studentsCnt;
    private int day, time;

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", studentsCnt=" + studentsCnt +
                ", day=" + day +
                ", time=" + time +
                '}';
    }

    public Subject(String name, long studentsCnt, long day, long time) {
        this.name = name;
        this.studentsCnt = (int) studentsCnt;
        this.day = (int) day;
        this.time = (int) time;
    }

    public String getName() {
        return name;
    }

    public int getStudentsCnt() {
        return studentsCnt;
    }

    public void reduceStd(int value) {
        if (this.studentsCnt <= value)
            this.studentsCnt = 0;
        else
            this.studentsCnt -= value;
    }
    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(Subject o) {
        if (o == null)
            return 0;
        if(o.day == this.day)
            return this.time - o.time;
        else
            return this.day - o.day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return studentsCnt == subject.studentsCnt &&
                day == subject.day &&
                time == subject.time &&
                name.equals(subject.name);
    }

    public void setStudentsCnt(int studentsCnt) {
        this.studentsCnt = studentsCnt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentsCnt, day, time);
    }

    @Override
    protected Object clone() {
        return new Subject(name, studentsCnt, day, time);
    }
}
