import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;


public class Exam implements Comparable<Exam>, Cloneable {
    private Subject subject;
    private ClassRoom classRoom;
    private Teacher head;
    private Employee secretary;
    private HashSet<Watcher> watchers;
    private ArrayList<String> constrainBreak;
    private LinkedList<String> watchersTypes;

    private Exam() {
    }

    public Exam(ClassRoom classRoom) {
        this.classRoom = classRoom;
        watchers = new HashSet<>();
        constrainBreak = new ArrayList<>();
        watchersTypes = new LinkedList<>();
        switch (classRoom.getCap()) {
            case 30: {
                watchersTypes.add("Watcher");
                break;
            }
            case 50: {
                watchersTypes.add("Teacher");
                watchersTypes.add("Employee");
                watchersTypes.add("Watcher");
                watchersTypes.add("Watcher");
                break;
            }
            case 70: {
                watchersTypes.add("Teacher");
                watchersTypes.add("Employee");
                watchersTypes.add("Watcher");
                watchersTypes.add("Watcher");
                watchersTypes.add("Watcher");
                watchersTypes.add("Watcher");
                break;
            }
        }
    }

    public Exam(ClassRoom classRoom, Subject subject) {
        this.classRoom = classRoom;
        this.subject = subject;
        watchers = new HashSet<>();
        constrainBreak = new ArrayList<>();
    }

    public int getWatcherNeed() {
        return watchersTypes.size();
    }

    public String getNextWatcher() {
        if (watchersTypes.size() == watchers.size()) {
            System.out.println("full");
            return null;
        }
        return watchersTypes.get(watchers.size());
    }

    public LinkedList<String> getWatchersTypes() {
        return watchersTypes;
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

    public boolean isValid() {
        if (watchersTypes.size() != watchers.size())
            return false;
        LinkedList<String> list = new LinkedList<>();
        Collections.copy(list, watchersTypes);
        for (Watcher w : watchers) {
            if (list.contains(w.getType()))
                list.removeFirstOccurrence(w.getType());
            else if (w.getType().equals("Employee"))
                if (list.contains("Watcher"))
                    list.removeFirstOccurrence("Watcher");
                else
                    return false;
            else
                return false;
        }
        return true;
    }
}
