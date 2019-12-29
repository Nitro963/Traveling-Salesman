import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Exam implements Comparable<Exam>{
    private ClassRoom classRoom;
    private Subject subject;
    private Teacher head;
    private Employee secretary;
    private Set<Watcher> watchers;

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
        return subject.compareTo(o.subject);
    }

}
