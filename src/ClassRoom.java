import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public class ClassRoom implements Comparable<ClassRoom>, Cloneable {
    private String name;
    private int floor;
    private int cap;
    private int watcherNeed;

    public ClassRoom(String name, int floor, int cap, int watcherNeed) {
        this.name = name;
        this.floor = floor;
        this.cap = cap;
        this.watcherNeed = watcherNeed;

    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public int getCap() {
        return cap;
    }

    public int getWatcherNeed() {
        return watcherNeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return floor == classRoom.floor &&
                cap == classRoom.cap &&
                watcherNeed == classRoom.watcherNeed &&
                name.equals(classRoom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, floor, cap, watcherNeed);
    }

    @Override
    public int compareTo(ClassRoom o) {
        if (floor == o.floor)
            return cap - o.cap;
        return floor - o.floor;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "name='" + name + '\'' +
                ", floor=" + floor +
                ", cap=" + cap +
                ", watcherNeed=" + watcherNeed +
                "}\n";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new ClassRoom(name, floor, cap, watcherNeed);
    }

    public static ArrayList<Pair<Exam, Integer>> generateExams(ArrayList<ClassRoom> classRooms, ArrayList<Subject> subjects) throws Exception {
        ArrayList<Pair<Exam, Integer>> ret = new ArrayList<>();
        PriorityQueue<ClassRoom> qu = new PriorityQueue<>();
        for (Subject s : subjects) {
            int cnt = s.getStudentsCnt();
            int floor = qu.peek().floor;
            int cost = 0;
            ArrayList<Exam> arrayList = new ArrayList<>();
            while (cnt != 0) {
                if (qu.isEmpty())
                    throw new Exception("Students overflow");
                ClassRoom r = qu.poll();
                if (r.floor != floor)
                    cost += 1;
                cnt -= Math.min(r.cap, cnt);
                arrayList.add(new Exam(r, s));
            }
            for (Exam e : arrayList)
                ret.add(new Pair<>(e, cost));
        }
        return null;
    }
}
