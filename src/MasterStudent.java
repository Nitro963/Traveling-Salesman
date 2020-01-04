public class MasterStudent extends Watcher {
    public MasterStudent(String name, Constrain constrain, int cnt) {
        super(name, constrain, cnt);
        this.type = "Watcher";
    }

    public MasterStudent(String name, Constrain constrain) {
        super(name, constrain, 6);
        this.type = "Watcher";
    }
}
