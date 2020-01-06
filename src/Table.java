import java.io.*;
import java.util.*;



public class Table implements Cloneable , Serializable{

    public static Object deepCopy(Object b){
        try {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutStream = new ObjectOutputStream(byteOutStream);
            objectOutStream.writeObject(b);
            objectOutStream.close();
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(byteOutStream.toByteArray()));
            Object cb = inputStream.readObject();
            inputStream.close();
            return cb;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Exam> list;//Exams Table
    private Exam currentExam;
    private HashSet<Integer>[][] hashSet;//at Day i and time j is the watcher x Taken?
    private HashSet<String>[][] classRoomHashSet;//at Day i and time j is the classroom x taken?
    private LinkedList<Subject> pendingSubjects;//pending subjects to add to the exam list
    //pending watchers to take a watch(fair distribution)
    private LinkedList<Teacher> pendingTeachers;
    private LinkedList<TeacherAssistant> pendingTeachersAssistant;
    private LinkedList<Employee> pendingEmployees;
    private LinkedList<MasterStudent> pendingStudents;
    private int[] watchesCount;
    private int g;//Traversing cost(Graph edge)

    public Exam getCurrentExam() {
        return currentExam;
    }

    public Table(ArrayList<Subject> subjects) {
        list = new ArrayList<>();
        hashSet = new HashSet[10][5];
        classRoomHashSet = new HashSet[10][5];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 5; j++) {
                hashSet[i][j] = new HashSet<>();
                classRoomHashSet[i][j] = new HashSet<>();
            }
        watchesCount = new int[25];
        pendingSubjects = new LinkedList<>(subjects);
        pendingTeachers = new LinkedList<>();
        pendingTeachersAssistant = new LinkedList<>();
        pendingStudents = new LinkedList<>();
        pendingEmployees = new LinkedList<>();
    }

    private Table(){

    }

    private int h1() {
        return pendingSubjects.size();
    }

    private int h2() {
        return 0;
    }

    private void addW(Watcher w) {
        currentExam.addWatcher(w);
        watchesCount[w.getId()]++;
        hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].add(w.getId());
    }

    private boolean checkConDay(Watcher w) {
        int cnt = 0;
        for (int j = 1; j <= 3; j++)
            if (hashSet[currentExam.getSubject().getDay()][j].contains(w))
                cnt++;
            else
                cnt = 0;
        return cnt > w.getConstrain().getConDay();
    }

    private ArrayList<Table> selectClassRoom(ArrayList<ClassRoom> classRooms) {
        ArrayList<Table> ret = new ArrayList<>();
        Subject s = pendingSubjects.peek();
        for (ClassRoom r : classRooms) {
            if (!classRoomHashSet[s.getDay()][s.getTime()].contains(r.getName())) {
                Table t = (Table) clone();
                Subject subject = (Subject) s.clone();
                t.classRoomHashSet[s.getDay()][s.getTime()].add(r.getName());
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
                if (!hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].contains(teacher.getId()))
                    if (teacher.getConstrain().isAvailableAtDay(currentExam.getSubject().getDay())) {
                        if (teacher.getConstrain().isAvailableAtTime(currentExam.getSubject().getTime())) {
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
                            if (!teacher.getConstrain().isPreferDay(currentExam.getSubject().getDay())) {
                                t.currentExam.addConstrainBreak(teacher.getName() + "dose not prefer day " + currentExam.getSubject().getDay());
                                t.g += 2;
                            }
                            t.g += t.checkWatchesCount(teacher);
                            ret.add(t);
                        }
                    }
        }
        return ret;
    }

    private int checkWatchesCount(Watcher w) {
        if (w instanceof Employee) {
            int cnt = 0;
            for (int j = 1; j <= 3; j++)
                if (hashSet[currentExam.getSubject().getDay()][j].contains(w))
                    cnt++;
            if (cnt > 1) {
                currentExam.addConstrainBreak("Employee " + w.getName() +
                        " has more than 1 watch at day" + currentExam.getSubject().getDay());
                return 1;
            }
            return 0;
        } else {
            int cnt = 0;
            for (int j = 1; j <= 3; j++)
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
            if(!hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].contains(emp.getId()))
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
            if (watchesCount[student.getId()] + 1 <= student.getCntMax()) {
                if (!hashSet[currentExam.getSubject().getDay()][currentExam.getSubject().getTime()].contains(student.getId())) {
                    if (student.getConstrain().isAvailableAtDay(currentExam.getSubject().getDay())) {
                        if (student.getConstrain().isAvailableAtTime(currentExam.getSubject().getTime())) {
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
                            if (!student.getConstrain().isPreferDay(currentExam.getSubject().getDay())) {
                                t.currentExam.addConstrainBreak(student.getName() + "dose not prefer day " + currentExam.getSubject().getDay());
                                t.g += 2;
                            }
                            t.g += t.checkWatchesCount(student);
                            ret.add(t);
                        }
                    }
                }
            }
        }
        return ret;
    }

    private ArrayList<Table> selectWatcher() {
        ArrayList<Table> ret = new ArrayList<>();
        if (currentExam.getWatcherNeed() <= currentExam.getWatchers().size()) {
            return ret;
        }
        switch (currentExam.getNextWatcher()) {
            case "Teacher": {
                if (pendingTeachers.isEmpty())
                    pendingTeachers.addAll(Main.teachers);
                if (pendingTeachersAssistant.isEmpty())
                    pendingTeachersAssistant.addAll(Main.teacherAssistants);
                ret.addAll(selectTeacher(pendingTeachers));

                //ret.addAll(selectTeacher(pendingTeachersAssistant));
                break;
            }
            case "Employee": {
                if (pendingEmployees.isEmpty())
                    pendingEmployees.addAll(Main.employees);
                ret.addAll(selectEmployee());
                break;
            }
            case "Watcher": {
                if (pendingStudents.isEmpty())
                    pendingStudents.addAll(Main.students);
                ArrayList<Table> tables = selectStudent();
                if (tables.isEmpty()) {
                    if (pendingEmployees.isEmpty())
                        pendingEmployees.addAll(Main.employees);
                    tables = selectEmployee();
                    for (Table t : tables) {
                        t.currentExam.addConstrainBreak("Add an employee rather than a student");
                        t.g += 2;
                        ret.add(t);
                    }
                } else
                    ret.addAll(tables);
                break;
            }

        }
        return ret;
    }

    private ArrayList<Table> generateNext(ArrayList<ClassRoom> classRooms) {
        if (currentExam == null)
            return selectClassRoom(classRooms);
        return selectWatcher();
    }

    public boolean isFinal() {
        if (!pendingSubjects.isEmpty()) {
            if (currentExam != null) {
                if (currentExam.isValid()) {
                    list.add(currentExam);
                    Subject s = pendingSubjects.peek();
                    s.setStudentsCnt(Math.max(s.getStudentsCnt() - currentExam.getClassRoom().getCap(), 0));
                    if (s.getStudentsCnt() == 0) {
                        pendingSubjects.poll();
                    }
                    currentExam = null;
                }
                return pendingSubjects.isEmpty();
            }
        }
        return pendingSubjects.isEmpty();
    }

    //(deep/shallow)cloning
    @Override
    protected Object clone() {
        Table t = new Table();
        t.list = (ArrayList<Exam>) list.clone();
        if(currentExam != null)
            t.currentExam = (Exam) currentExam.clone();

        t.watchesCount = (int[]) deepCopy(watchesCount);
        t.hashSet = (HashSet<Integer>[][]) deepCopy(hashSet);
        t.classRoomHashSet = (HashSet<String>[][]) deepCopy(classRoomHashSet);
        t.pendingSubjects = (LinkedList<Subject>) deepCopy(pendingSubjects);

        t.pendingStudents = (LinkedList<MasterStudent>) pendingStudents.clone();
        t.pendingEmployees = (LinkedList<Employee>) pendingEmployees.clone();
        t.pendingTeachers = (LinkedList<Teacher>) pendingTeachers.clone();
        t.pendingTeachersAssistant = (LinkedList<TeacherAssistant>) pendingTeachersAssistant.clone();
        t.g = g;
        return t;
    }

    @Override
    public String toString() {
        return "Table{" +
                "list=" + list +
                '}';
    }

    public ArrayList<Exam> getList() {
        return list;
    }

    public HashSet<Integer>[][] getHashSet() {
        return hashSet;
    }

    public HashSet<String>[][] getClassRoomHashSet() {
        return classRoomHashSet;
    }

    public LinkedList<Subject> getPendingSubjects() {
        return pendingSubjects;
    }

    public LinkedList<Teacher> getPendingTeachers() {
        return pendingTeachers;
    }

    public LinkedList<TeacherAssistant> getPendingTeachersAssistant() {
        return pendingTeachersAssistant;
    }

    public LinkedList<Employee> getPendingEmployees() {
        return pendingEmployees;
    }

    public LinkedList<MasterStudent> getPendingStudents() {
        return pendingStudents;
    }

    public int[] getWatchesCount() {
        return watchesCount;
    }

    public int getG() {
        return g;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return list.equals(table.list) &&
                Objects.equals(currentExam, table.currentExam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, currentExam);
    }

    //uniform search
    public static Table solve() {
        PriorityQueue<PqPair<Table>> pq = new PriorityQueue<>();
        ArrayList<Subject> mySubjects = new ArrayList<>();
        mySubjects.add(Main.subjects.get(0));
        //mySubjects.add(Main.subjects.get(1));
        Table table = new Table(mySubjects);
        pq.add(new PqPair<>(0, table));

        HashMap<Table, Integer> mp = new HashMap<>();
        mp.put(table, 0);

        while (!pq.isEmpty()) {

            PqPair<Table> p = pq.poll();
            Integer cost = p.first;
            Table t = p.second;

            if (mp.containsKey(t))
                if (cost > mp.get(t))
                    continue;

            if (t.isFinal()) {
                //TODO printing Table content and constrains break
                return t;
            }


            ArrayList<Table> list = t.generateNext(Main.classRooms);

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
        return null;
    }

    public static Table AStar() {
        PriorityQueue<PqPair<Table>> pq = new PriorityQueue<>();
        ArrayList<Subject> mySubjects = new ArrayList<>();
        mySubjects.add(Main.subjects.get(0));
        mySubjects.add(Main.subjects.get(1));

        Table table = new Table(mySubjects);
        pq.add(new PqPair<>(0, table));

        HashMap<Table, Integer> mp = new HashMap<>();
        mp.put(table, 0);
        HashMap<Table, Integer> gmp = new HashMap<>();
        gmp.put(table, 0);
        while (!pq.isEmpty()) {

            PqPair<Table> p = pq.poll();
            Table t = p.second;

            Integer currentF = mp.get(t);
            Integer currentG = gmp.get(t);

            if (mp.containsKey(t))
                if (currentF < p.first)
                    continue;

            if (t.isFinal()) {
                //TODO printing Table content and constrains break
                return t;
            }

            ArrayList<Table> list = t.generateNext(Main.classRooms);

            for (Table child : list) {
                if (mp.containsKey(child)) {
                    int oldCost = mp.get(child);
                    int g = child.g + currentG;
                    int f = g + child.h1();
                    if (oldCost > f) {
                        mp.put(child, f);
                        gmp.put(child, g);
                        pq.add(new PqPair<>(f, child));
                    }
                } else {
                    int g = currentG + child.g;
                    int f = g + child.h1();
                    mp.put(child, f);
                    gmp.put(child, g);
                    pq.add(new PqPair<>(f, child));
                }
            }
        }
        return null;
    }
}
