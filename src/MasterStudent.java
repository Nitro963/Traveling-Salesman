public class MasterStudent extends Watcher {
    public MasterStudent(String name, int id, Constrain constrain, int cnt) {
        super(name, id, constrain, cnt);
        this.type = "Watcher";
    }

    public MasterStudent(String name, int id, Constrain constrain) {
        super(name, id, constrain, 6);
        this.type = "Watcher";
    }
}
