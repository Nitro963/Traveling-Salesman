public class MasterStudent extends Watcher {
    public MasterStudent(String name, Constrain constrain, int cnt) {
        super(name, constrain, cnt);
    }

    public MasterStudent(String name, Constrain constrain) {
        super(name, constrain, 6);
    }
}
