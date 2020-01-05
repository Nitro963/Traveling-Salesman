import java.util.*;

public class Table implements Cloneable {
    private ArrayList<Exam> list;//Exams Table
    private Exam currentExam;
    private HashSet<Watcher>[][] hashSet;//at Day i and time j is the watcher x Taken?
    private HashSet<ClassRoom>[][] classRoomHashSet;//at Day i and time j is the classroom x taken?
    private LinkedList<Subject> pendingSubjects;//pending subjects to add to the exam list
    private LinkedList<Teacher> pendingTeachers;
    private LinkedList<TeacherAssistant> pendingTeachersAssistant;
    private LinkedList<Employee> pendingEmployees;
    private LinkedList<MasterStudent> pendingStudents;

    private int[] watchesCount;
    private int g;//Traversing cost(Graph edge)

    public Table() {
        list = new ArrayList<>();
        hashSet = new HashSet[10][5];
        classRoomHashSet = new HashSet[10][5];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 5; j++) {
                hashSet[i][j] = new HashSet<>();
                classRoomHashSet[i][j] = new HashSet<>();
            }
        watchesCount = new int[25];
        pendingSubjects = new LinkedList<>();
        pendingTeachers = new LinkedList<>();
        pendingTeachersAssistant = new LinkedList<>();
        pendingStudents = new LinkedList<>();
        pendingEmployees = new LinkedList<>();
    }

    private void addW(Watcher w) {
        currentExam.addWatcher(w);
        watchesCount[w.getId()]++;
        hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].add(w);
    }

    private boolean checkConDay(Watcher w) {
        if (w.getConstrain().getConDay() == -1)
            return true;
        int cnt = 0;
        for (int j = 0; j < 3; j++)
            if (hashSet[currentExam.getSubject().getDay()][j].contains(w))
                cnt++;
            else
                cnt = 0;
        return cnt <= w.getConstrain().getConDay();
    }

    private ArrayList<Table> selectClassRoom(ArrayList<ClassRoom> classRooms) {
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

    private ArrayList<Table> selectTeacher(LinkedList<? extends Teacher> teachers) {
        ArrayList<Table> ret = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            if (this.checkConDay(teacher))
                continue;
            if (watchesCount[teacher.id] + 1 <= teacher.getCntMax())
                if (!hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].contains(teacher))
                    if (teacher.getConstrain().isAvailableAtDay(currentExam.getSubject().getDay())) {
                        teachers.remove(teacher);
                        Table t = (Table) clone();
                        if (teacher instanceof TeacherAssistant) {
                            LinkedList<TeacherAssistant> linkedList = (LinkedList<TeacherAssistant>) teachers;
                            linkedList.add((TeacherAssistant) teacher);
                        } else {
                            LinkedList<Teacher> linkedList = (LinkedList<Teacher>) teachers;
                            linkedList.add(teacher);
                        }
                        t.addW(teacher);
                        if (teacher.getConstrain().isPreferTime(currentExam.getSubject().getTime())) {
                            t.g = 1;
                        } else {
                            currentExam.addConstrainBreak(teacher.getName() + " dose not prefer time " + currentExam.getSubject().getTime());
                            t.g = 2;
                        }
                        t.g += t.checkWatchesCount(teacher);
                        ret.add(t);
                    }
        }
        return ret;
    }

    private int checkWatchesCount(Watcher w) {
        if (w instanceof Employee) {
            int cnt = 0;
            for (int j = 0; j < 3; j++)
                if (hashSet[currentExam.getSubject().getDay()][j].contains(w))
                    cnt++;
            if (cnt >= 1) {
                currentExam.addConstrainBreak("Employee " + w.getName() +
                        " has more than 1 watch at day" + currentExam.getSubject().getDay());
                return 1;
            }
            return 0;
        } else {
            int cnt = 0;
            for (int j = 0; j < 3; j++)
                if (hashSet[currentExam.getSubject().getDay()][j].contains(w))
                    cnt++;
            if (cnt >= 2) {
                if (w.getConstrain().getCntDay() < cnt) {
                    currentExam.addConstrainBreak("watcher " + w.getName() +
                            " has more than 2 watch at day" + currentExam.getSubject().getDay());
                    return 1;
                }
            }
            return 0;
        }
    }

    private ArrayList<Table> selectEmployee() {
        ArrayList<Table> ret = new ArrayList<>();
        for (int i = 0; i < pendingEmployees.size(); i++) {
            Employee emp = pendingEmployees.get(i);
            if (watchesCount[emp.id] + 1 <= emp.getCntMax()) {
                pendingEmployees.remove(i);
                Table t = (Table) clone();
                pendingEmployees.add(i, emp);
                t.addW(emp);
                t.g = 1;
                t.g += t.checkWatchesCount(emp);

                ret.add(t);
            }
        }
        return ret;
    }

    private ArrayList<Table> selectStudent() {
        ArrayList<Table> ret = new ArrayList<>();
        for (int i = 0; i < pendingStudents.size(); i++) {
            MasterStudent student = pendingStudents.get(i);
            if (this.checkConDay(student))
                continue;

            if (watchesCount[student.getId()] <= student.getCntMax()) {
                if (!hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].contains(student)) {
                    if (student.getConstrain().isAvailableAtDay(currentExam.getSubject().getDay())) {
                        pendingStudents.remove(i);
                        Table t = (Table) clone();
                        pendingStudents.add(i, student);

                        t.addW(student);
                        if (student.getConstrain().isPreferTime(currentExam.getSubject().getTime())) {
                            t.g = 1;
                        } else {
                            currentExam.addConstrainBreak(student.getName() + " dose not prefer time " + currentExam.getSubject().getTime());
                            t.g = 2;
                        }
                        t.g += t.checkWatchesCount(student);
                        ret.add(t);
                    }
                }
            }
        }
        return ret;
    }

    private ArrayList<Table> selectWatcher() {
        ArrayList<Table> ret = new ArrayList<>();
        if (currentExam.getWatcherNeed() >= currentExam.getWatchers().size())
            return ret;
        switch (currentExam.getNextWatcher()) {
            case "Teacher": {
                if (pendingTeachers.isEmpty())
                    pendingTeachers.addAll(Main.teachers);
                if (pendingTeachersAssistant.isEmpty())
                    pendingTeachersAssistant.addAll(Main.teacherAssistants);
                ret.addAll(selectTeacher(pendingTeachers));
                ret.addAll(selectTeacher(pendingTeachersAssistant));
            }
            case "Employee": {
                if (pendingEmployees.isEmpty())
                    pendingEmployees.addAll(Main.employees);
                ret.addAll(selectEmployee());
            }
            case "Watcher": {
                if (pendingStudents.isEmpty())
                    pendingStudents.addAll(Main.students);
                ArrayList<Table> tables = selectStudent();
                if (tables.isEmpty()) {
                    tables = selectEmployee();
                    for (Table t : tables) {
                        t.g = 2;
                        ret.add(t);
                    }
                } else
                    ret.addAll(tables);
            }
        }
        return ret;
    }

    private ArrayList<Table> generateNext(ArrayList<ClassRoom> classRooms) {
        if (currentExam == null)
            return selectClassRoom(classRooms);
        //if you are sure that the added watchers is what the exam need -> only check for sizes
        if (currentExam.isValid()) {
            list.add(currentExam);
            currentExam = null;
            return selectClassRoom(classRooms);
        } else
            return selectWatcher();
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

    //(deep/shallow) cloning
    @Override
    protected Object clone() {
        Table t = new Table();
        Collections.copy(t.list, list);
        Collections.copy(t.pendingSubjects, pendingSubjects);
        Collections.copy(t.pendingStudents, pendingStudents);
        Collections.copy(t.pendingEmployees, pendingEmployees);
        Collections.copy(t.pendingTeachersAssistant, pendingTeachersAssistant);
        Collections.copy(t.pendingTeachers, pendingTeachers);
        /*
        t.pendingTeachers = (LinkedList<Teacher>) pendingTeachers.clone();
        t.pendingEmployees = (LinkedList<Employee>) pendingEmployees.clone();
        t.pendingStudents = (LinkedList<MasterStudent>) pendingStudents.clone();
        t.pendingTeachersAssistant = (LinkedList<TeacherAssistant>) pendingTeachersAssistant.clone();
        */
        for (int i = 0; i < t.classRoomHashSet.length; i++)
            for (int j = 0; j < t.classRoomHashSet[i].length; j++)
                t.classRoomHashSet[i][j] = (HashSet<ClassRoom>) t.classRoomHashSet[i][j].clone();

        for (int i = 0; i < t.hashSet.length; i++)
            for (int j = 0; j < t.hashSet[i].length; j++)
                t.hashSet[i][j] = (HashSet<Watcher>) t.hashSet[i][j].clone();

        t.g = g;
        t.currentExam = (Exam) currentExam.clone();
        return t;
    }

    //uniform search
    public static void solve() {
        PriorityQueue<PqPair<Table>> pq = new PriorityQueue<>();
        Table table = new Table();
        table.pendingSubjects.addAll(Main.subjects);
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

            ArrayList<Table> list = t.generateNext(Main.ClassRooms);

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
