public class Employee extends Watcher{
    public Employee(String name, int id, int cnt) {
        super(name, id, null, cnt);
        super.type = "Employee";
    }

    public Employee(String name, int id) {
        super(name, id, null, 20);
        super.type = "Employee";
    }

}
