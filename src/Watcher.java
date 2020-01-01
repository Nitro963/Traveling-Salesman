import java.util.Objects;

abstract public class Watcher {
    protected String name;
    protected Constrain constrain;
    protected int cntMax;

    public Watcher(String name, Constrain constrain, int cntMax) {
        this.name = name;
        this.constrain = constrain;
        this.cntMax = cntMax;
    }

    public String getName() {
        return name;
    }

    public Constrain getConstrain() {
        return constrain;
    }

    public int getCntMax() {
        return cntMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Watcher watcher = (Watcher) o;
        return name.equals(watcher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
