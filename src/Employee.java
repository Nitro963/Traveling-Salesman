public class Employee extends Watcher{
    public Employee(String name, int cnt) {
        super(name, new Constrain(), cnt);
    }
    public Employee(String name) {
        super(name, new Constrain(), 20);
    }
}
