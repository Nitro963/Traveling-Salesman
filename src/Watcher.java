import java.util.Objects;

abstract public class Watcher implements Cloneable {
    protected String name;
    protected Constrain constrain;
    protected int cntMax;
    protected String type;

    public Watcher(String name, Constrain constrain, int cntMax) {
        this.name = name;
        this.constrain = constrain;
        this.cntMax = cntMax;
        this.type = null;
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

    @Override
    protected Object clone() {
        try {
            Watcher w = (Watcher) super.clone();
            w.name = this.name;
            w.cntMax = cntMax;
            w.constrain = (Constrain) this.constrain.clone();
            w.type = type;
            return w;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getType() {
        return type;
    }

}