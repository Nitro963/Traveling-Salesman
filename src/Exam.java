import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class Exam implements Comparable<Exam>, Cloneable {
    private Subject subject;
    private ClassRoom classRoom;
    private Teacher head;
    private Employee secretary;
    private HashSet<Watcher> watchers;
    private ArrayList<String> constrainBreak;

    private Exam() {

    }

    public Exam(ClassRoom classRoom) {
        this.classRoom = classRoom;
        watchers = new HashSet<>();
        constrainBreak = new ArrayList<>();
    }

    public Exam(ClassRoom classRoom, Subject subject) {
        this.classRoom = classRoom;
        this.subject = subject;
        watchers = new HashSet<>();
        constrainBreak = new ArrayList<>();
    }

    public void addConstrainBreak(String s) {
        constrainBreak.add(s);
    }

    public void addWatcher(Watcher w){
        if (w instanceof Teacher)
            if (head == null)
                head = (Teacher) w;
        if (w instanceof Employee)
            if (secretary == null)
                secretary = (Employee) w;
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

    public HashSet<Watcher> getWatchers() {
        return watchers;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public Subject getSubject() {
        return subject;
    }

    private boolean check(int s) {
        if (head == null || secretary == null)
            return false;
        return watchers.size() == s;
    }

    public boolean isValid() {
        int cap = classRoom.getCap();
        if (cap == 50) {
            if (!check(3))
                return false;
        }
        if (cap == 30) {
            if (watchers.size() != 3)
                return false;
        }
        if (cap == 70) {
            return check(4);
        }
        return true;
    }

    @Override
    public int compareTo(Exam o) {
        return subject.compareTo(o.subject);
    }

    @Override
    protected Object clone() {
        Exam exam = new Exam();
        exam.classRoom = (ClassRoom) classRoom.clone();
        exam.subject = (Subject) subject.clone();
        Collections.copy(exam.constrainBreak, constrainBreak);
        exam.head = (Teacher) head.clone();
        exam.secretary = (Employee) secretary.clone();
        exam.watchers = (HashSet<Watcher>) watchers.clone();
        return exam;
    }
}
