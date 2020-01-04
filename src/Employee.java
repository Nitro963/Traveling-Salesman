public class Employee extends Watcher{
    public Employee(String name, int cnt) {
        super(name, null, cnt);
        super.type = "Employee";
    }
    public Employee(String name) {
        super(name, null, 20);
        super.type = "Employee";
    }
}
