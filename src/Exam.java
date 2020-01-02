import java.util.HashSet;
import java.util.Set;


public class Exam implements Comparable<Exam>, Cloneable {
    private Subject subject;
    private ClassRoom classRoom;
    private Teacher head;
    private Employee secretary;
    private Set<Watcher> watchers;

    private Exam() {

    }

    public Exam(ClassRoom classRoom) {
        this.classRoom = classRoom;
        watchers = new HashSet<>();
    }

    public Exam(ClassRoom classRoom, Subject subject) {
        this.classRoom = classRoom;
        this.subject = subject;
        watchers = new HashSet<>();
    }

    public void addWatcher(Watcher w){
        watchers.add(w);
    }

    public void setClassRoomPresident(Teacher t){
        watchers.add(t);
        head = t;
    }

    public void setClassRoomEmp(Employee emp){
        watchers.add(emp);
        this.secretary = emp;
    }

    public Teacher getHead() {
        return head;
    }

    public Employee getSecretary() {
        return secretary;
    }

    public Set<Watcher> getWatchers() {
        return watchers;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public int compareTo(Exam o) {
        if (subject == null)
            return 0;
        return subject.compareTo(o.subject);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Exam exam = new Exam();
        exam.classRoom = (ClassRoom) classRoom.clone();
        if (subject != null)
            exam.subject = (Subject) subject.clone();
        exam.head = head;
        exam.watchers = watchers;
        exam.secretary = secretary;
        return exam;
    }
}
