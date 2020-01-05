import java.util.Objects;

public class Constrain implements Cloneable {
    private int mainDaysMask;
    private int secondaryDaysMask;
    private int mainTimesMask;
    private int secondaryTimesMask;
    private int conDay;//main
    private int cntDay;//secondary

    public Constrain() {
        mainDaysMask = (1 << 5) - 1;
        mainTimesMask = (1 << 3) - 1;
        secondaryTimesMask = mainTimesMask;
        secondaryDaysMask = mainDaysMask;
        conDay = 3;
        cntDay = 3;
    }

    private Constrain(int mainDaysMask, int secondaryDaysMask, int mainTimesMask, int secondaryTimesMask, int conDay, int cntDay) {
        this.mainDaysMask = mainDaysMask;
        this.secondaryDaysMask = secondaryDaysMask;
        this.mainTimesMask = mainTimesMask;
        this.secondaryTimesMask = secondaryTimesMask;
        this.conDay = conDay;
        this.cntDay = cntDay;
    }

    public boolean isAvailableAtDay(int i) {
        return (mainDaysMask & (1 << (i - 1))) != 0;
    }

    public boolean isPreferDay(int i) {
        return (secondaryDaysMask & (1 << (i - 1))) != 0;
    }

    public boolean isAvailableAtTime(int i) {
        return (mainTimesMask & (1 << (i - 1))) != 0;
    }

    public boolean isPreferTime(int i) {
        return (secondaryTimesMask & (1 << (i - 1))) != 0;
    }

    public void setAvailableAtDay(int i){
        mainDaysMask |= (1 << (i - 1));
    }

    public void setPreferDay(int i){
        secondaryDaysMask |= (1 << (i - 1));
    }

    public void setAvailableAtTime(int i){
        mainTimesMask |= (1 << (i - 1));
    }

    public void setPreferTime(int i){
        secondaryTimesMask |= (1 << (i - 1));
    }

    public void unSetAvailableAtDay(int i) {
        mainDaysMask &= ~(1 << (i - 1));
        secondaryDaysMask &= ~(1 << (i - 1));
    }

    public void unSetPreferDay(int i) {
        secondaryDaysMask &= ~(1 << (i - 1));
    }

    public void unSetAvailableAtTime(int i) {
        mainTimesMask &= ~(1 << (i - 1));
        secondaryTimesMask &= ~(1 << (i - 1));
    }

    public void unSetPreferTime(int i) {
        secondaryTimesMask &= ~(1 << (i - 1));
    }

    public int getConDay() {
        return conDay;
    }

    public int getCntDay() {
        return cntDay;
    }

    public void setConDay(int conDay) {
        this.conDay = conDay;
    }

    public void setCntDay(int cntDay) {
        this.cntDay = cntDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constrain constrain = (Constrain) o;
        return mainDaysMask == constrain.mainDaysMask &&
                secondaryDaysMask == constrain.secondaryDaysMask &&
                mainTimesMask == constrain.mainTimesMask &&
                secondaryTimesMask == constrain.secondaryTimesMask &&
                conDay == constrain.conDay &&
                cntDay == constrain.cntDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainDaysMask, secondaryDaysMask, mainTimesMask, secondaryTimesMask, conDay, cntDay);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Constrain c = new Constrain();
        c.mainTimesMask = mainTimesMask;
        c.secondaryDaysMask = secondaryDaysMask;
        c.cntDay = cntDay;
        c.secondaryTimesMask = secondaryTimesMask;
        c.mainTimesMask = mainTimesMask;
        c.conDay = conDay;
        return c;
    }
}
