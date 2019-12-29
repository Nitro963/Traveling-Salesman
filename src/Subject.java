import java.util.Objects;

public class Subject implements Comparable<Subject>{
    private String name;
    private int StudentsCnt;
    private int day, time;

    public Subject(String name, int studentsCnt, int day, int time) {
        this.name = name;
        StudentsCnt = studentsCnt;
        this.day = day;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getStudentsCnt() {
        return StudentsCnt;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(Subject o) {
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
        return StudentsCnt == subject.StudentsCnt &&
                day == subject.day &&
                time == subject.time &&
                name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, StudentsCnt, day, time);
    }
}
