public class Teacher extends Watcher{

    public Teacher(String name, Constrain constrain) {
        super(name, constrain, 7);
        this.type = "Teacher";
    }

    public Teacher(String name, Constrain constrain, int cnt) {
        super(name, constrain, cnt);
        this.type = "Teacher";
    }
}
