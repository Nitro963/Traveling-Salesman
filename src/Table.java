import java.util.*;

public class Table implements Cloneable {
    private ArrayList<Exam> list;//Exams Table
    private Exam currentExam;
    private HashSet<Watcher>[][] hashSet;//at Day i and time j is the watcher x Taken?
    private HashSet<ClassRoom>[][] classRoomHashSet;//at Day i and time j is the classroom x taken?
    private LinkedList<Subject> pendingSubjects;//pending subjects to add to the exam list
    private int[] watchesCount;
    private int g;//Traversing cost(Graph edge)

    private static ArrayList<Integer>[] masks = new ArrayList[3];

    static {
 /*       for(int i = 0; i < 3;i++) {
            masks[i] = new ArrayList<>();
            for(int j = 1; j < (1 << 25);j++){
                int cnt = 0;
                int msk = j;
                while(msk != 0) {
                    cnt++;
                    msk &= (msk - 1);
                }
                if(cnt == (i + 2))
                    masks[i].add(msk);
            }
        }
        for(int i = 0;i < 3;i++)
            System.out.println(masks[i].size());

  */
    }

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

    private ArrayList<Table> f(ArrayList<ClassRoom> classRooms) {
        ArrayList<Table> ret = new ArrayList<>();
        Subject s = pendingSubjects.peek();
        for (ClassRoom r : classRooms) {
            if (!classRoomHashSet[s.getDay()][s.getTime()].contains(r)) {
                Table t = (Table) clone();
                Subject subject = (Subject) s.clone();
                subject.setStudentsCnt(Math.min(subject.getStudentsCnt() - r.getCap(), 0));
                t.classRoomHashSet[s.getDay()][s.getTime()].add(r);
                Exam e = new Exam(r, subject);
                //TODO check classroom constrains (same floor, size , etc)
                t.currentExam = e;
                ret.add(t);
            }
        }
        return ret;
    }

    private ArrayList<Table> fun(ArrayList<Watcher> watchers) {
        ArrayList<Table> ret = new ArrayList<>();
        if (currentExam.getWatcherNeed() >= currentExam.getWatchers().size())
            return ret;
        for (Watcher watcher : watchers) {
            if (hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getDay()].contains(watcher))
                continue;
            if (watcher.getConstrain().isAvailableAtDay(currentExam.getSubject().getDay())) {
                //TODO check watcher constrains
                Table table = (Table) clone();
                table.currentExam.addWatcher(watcher);
                ret.add(table);
            }
        }
        return ret;
    }

    public ArrayList<Table> generateNext(ArrayList<ClassRoom> classRooms, ArrayList<Watcher> watchers) {
        //TODO check for fair distribution
        if (currentExam == null)
            return f(classRooms);
        //if you are sure that the added watchers is what the exam need -> only check for sizes
        if (currentExam.isValid()) {
            list.add(currentExam);
            currentExam = null;
            return f(classRooms);
        } else
            return fun(watchers);
    }

    public boolean isFinal() {

        if (!pendingSubjects.isEmpty()) {
            Subject s = pendingSubjects.peek();
            if (s.getStudentsCnt() == 0)
                pendingSubjects.poll();
            return pendingSubjects.isEmpty();
        }
        return true;
    }

    @Override
    protected Object clone() {
        Table t = new Table();
        t.list = (ArrayList<Exam>) list.clone();
        t.pendingSubjects = (LinkedList<Subject>) pendingSubjects.clone();
        t.classRoomHashSet = Arrays.copyOf(t.classRoomHashSet, 10);
        t.hashSet = Arrays.copyOf(t.hashSet, 10);
        t.g = g;
        t.currentExam = (Exam) currentExam.clone();
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

            ArrayList<Table> list = t.generateNext(Main.ClassRooms, Main.Watchers);

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
