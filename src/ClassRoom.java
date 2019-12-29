import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ClassRoom {
    private String name;
    private int floor;
    private int cap;
    private int watcherNeed;

    public ClassRoom(String name, int floor, int cap, int watcherNeed) {
        this.name = name;
        this.floor = floor;
        this.cap = cap;
        this.watcherNeed = watcherNeed;

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
        return watcherNeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return floor == classRoom.floor &&
                cap == classRoom.cap &&
                watcherNeed == classRoom.watcherNeed &&
                name.equals(classRoom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, floor, cap, watcherNeed);
    }
}
