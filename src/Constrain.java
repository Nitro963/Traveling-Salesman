import java.io.Serializable;
import java.util.Objects;

public class Constrain implements Cloneable, Serializable {
    private String mainDaysMask;
    private String secondaryDaysMask;
    private String mainTimesMask;
    private String secondaryTimesMask;
    private int conDay;//main
    private int cntDay;//secondary

    public Constrain() {
        mainDaysMask = "11111";
        mainTimesMask = "111";
        secondaryTimesMask = mainTimesMask;
        secondaryDaysMask = mainDaysMask;
        conDay = 3;
        cntDay = 3;
    }

    public Constrain(String mainDaysMask, String secondaryDaysMask, String mainTimesMask, String secondaryTimesMask, long conDay, long cntDay) {
        this.mainDaysMask = mainDaysMask;
        this.secondaryDaysMask = secondaryDaysMask;
        this.mainTimesMask = mainTimesMask;
        this.secondaryTimesMask = secondaryTimesMask;
        this.conDay = (int) conDay;
        this.cntDay = (int) cntDay;
    }

    public Constrain(String mainDaysMask, String secondaryDaysMask, long conDay, long cntDay) {
        this.mainDaysMask = mainDaysMask;
        this.secondaryDaysMask = secondaryDaysMask;
        this.cntDay = (int) cntDay;
        this.conDay = (int) conDay;
    }

    public boolean isAvailableAtDay(int i) {
        return mainDaysMask.charAt(i - 1) == '1';
    }

    public boolean isPreferDay(int i) {
        return secondaryTimesMask.charAt(i - 1) == '1';
    }

    public boolean isAvailableAtTime(int i) {
        return mainTimesMask.charAt(i - 1) == '1';
    }

    public boolean isPreferTime(int i) {
        return secondaryTimesMask.charAt(i - 1) == '1';
    }

    public int getConDay() {
        return conDay;
    }

    public int getCntDay() {
        return cntDay;
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
