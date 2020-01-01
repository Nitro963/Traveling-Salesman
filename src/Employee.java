public class Employee extends Watcher{
    public Employee(String name, int cnt) {
        super(name, null, cnt);
    }
    public Employee(String name) {
        super(name, null, 20);
    }
}
