public class Teacher extends Watcher{

    public Teacher(String name, int id, Constrain constrain) {
        super(name, id, constrain, 7);
        this.type = "Teacher";
    }

    public Teacher(String name, int id, Constrain constrain, int cnt) {
        super(name, id, constrain, cnt);
        this.type = "Teacher";
    }
}
