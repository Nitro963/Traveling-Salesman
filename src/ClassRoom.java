import java.util.ArrayList;
import java.util.Objects;

public class ClassRoom implements Comparable<ClassRoom>, Cloneable {
    private String name;
    private int floor;
    private int cap;
    private ArrayList<String> watchersTypes;

    public ClassRoom(String name, int floor, int cap) {
        this.name = name;
        this.floor = floor;
        this.cap = cap;
        watchersTypes = new ArrayList<>();
        switch (cap) {
            case 30: {
                watchersTypes.add("Master");
                watchersTypes.add("Master");
                break;
            }
            case 50: {
                watchersTypes.add("Head");
                watchersTypes.add("Secretary");
                watchersTypes.add("Master");
                break;
            }
            case 70: {
                watchersTypes.add("Head");
                watchersTypes.add("secretary");
                watchersTypes.add("Master");
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public int getCap() {
        return cap;
    }

    public int getWatcherNeed() {
        return watchersTypes.size();
    }

    public ArrayList<String> getWatchersTypes() {
        return watchersTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return floor == classRoom.floor &&
                name.equals(classRoom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, floor);
    }

    @Override
    public int compareTo(ClassRoom o) {
        if (floor == o.floor)
            return cap - o.cap;
        return floor - o.floor;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "name='" + name + '\'' +
                ", floor=" + floor +
                ", cap=" + cap +
                "}\n";
    }

    @Override
    protected Object clone() {
        return new ClassRoom(name, floor, cap);
    }
}
