import java.util.*;

public class Table implements Cloneable {
    ArrayList<Exam> list;//Exams Table
    HashSet<Watcher>[][] hashSet;//at Day i and time j is the watcher x Taken?
    HashSet<ClassRoom>[][] classRoomHashSet;//at Day i and time j is the classroom x taken?
    LinkedList<Subject> pendingSubjects;//pending subjects to add to the exam list
    int g;//Traversing cost(Graph edge)

    public Table() {
        list = new ArrayList<>();
        hashSet = new HashSet[10][5];
        classRoomHashSet = new HashSet[10][5];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 5; j++) {
                hashSet[i][j] = new HashSet<>();
                classRoomHashSet[i][j] = new HashSet<>();
            }
        pendingSubjects = new LinkedList<>();
    }

    ArrayList<Table> generateNext(ArrayList<ClassRoom> classRooms, ArrayList<Teacher> teachers, ArrayList<Employee> employees, ArrayList<MasterStudent> students) {
        ArrayList<Table> ret = new ArrayList<>();
        Subject s = pendingSubjects.peek();
        if (s.getStudentsCnt() == 0) {
            pendingSubjects.poll();
            s = pendingSubjects.peek();
        }
        for (ClassRoom r : classRooms) {
            if (!classRoomHashSet[s.getDay()][s.getTime()].contains(r)) {
                Table t = (Table) clone();
                t.classRoomHashSet[s.getDay()][s.getTime()].add(r);
                //create a new exam and check classroom constrains (same floor, size , etc)
                Exam e = new Exam(r, s);

                //start adding all possible watchers -> creating a new state

            }
        }
        return null;
    }

    public boolean isFinal() {
        return pendingSubjects.isEmpty();
    }

    @Override
    protected Object clone() {
        Table t = new Table();
        t.list = (ArrayList<Exam>) list.clone();
        t.pendingSubjects = (LinkedList<Subject>) pendingSubjects.clone();
        t.classRoomHashSet = Arrays.copyOf(t.classRoomHashSet, 10);
        t.hashSet = Arrays.copyOf(t.hashSet, 10);
        t.g = g;
        return t;
    }

    //uniform search
    public static void solve() {
        PriorityQueue<PqPair<Table>> pq = new PriorityQueue<>();
        Table table = new Table();
        pq.add(new PqPair<>(0, table));

        HashMap<Table, Integer> mp = new HashMap<>();
        while (!pq.isEmpty()) {

            PqPair<Table> p = pq.poll();
            Integer cost = p.first;
            Table t = p.second;

            if (t.isFinal()) {
                //TODO printing Table content and constrains break
                return;
            }

            if (mp.containsKey(t))
                if (cost > mp.get(t))
                    continue;

            ArrayList<Table> list = null;//TODO generating nextStates

            for (Table child : list) {
                if (mp.containsKey(child)) {
                    int oldCost = mp.get(child);
                    if (oldCost > cost + child.g) {
                        mp.put(child, cost + child.g);
                        pq.add(new PqPair<>(child.g, child));
                    }
                } else {
                    mp.put(child, child.g + cost);
                    pq.add(new PqPair<>(child.g + cost, child));
                }
            }
        }
    }
}
