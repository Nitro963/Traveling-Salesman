import java.util.Objects;

public class ClassRoom implements Comparable<ClassRoom>, Cloneable {
    private String name;
    private int floor;
    private int cap;

    public ClassRoom(String name, long floor, long cap) {
        this.name = name;
        this.floor = (int) floor;
        this.cap = (int) cap;
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
