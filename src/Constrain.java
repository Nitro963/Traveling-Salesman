import java.util.Objects;

public class Constrain {
    private int mainDaysMask;
    private int secondaryDaysMask;
    private int mainTimesMask;
    private int secondaryTimesMask;
    private int conDay;
    private int cntDay;

    public Constrain() {}

    public Constrain(int mainDaysMask, int secondaryDaysMask, int mainTimesMask, int secondaryTimesMask, int conDay, int cntDay) {
        this.mainDaysMask = mainDaysMask;
        this.secondaryDaysMask = secondaryDaysMask;
        this.mainTimesMask = mainTimesMask;
        this.secondaryTimesMask = secondaryTimesMask;
        this.conDay = conDay;
        this.cntDay = cntDay;
    }

    public boolean availableAtDay(int i){
        return (mainDaysMask & (1 << i)) != 0;
    }

    public boolean preferDay(int i){
        return (secondaryDaysMask & (1 << i)) != 0;
    }

    public boolean availableAtTime(int i){
        return (mainTimesMask & (1 << i)) != 0;
    }

    public boolean preferTime(int i){
        return (secondaryTimesMask & (1 << i)) != 0;
    }

    public void setAvailableAtDay(int i){
        mainDaysMask |= (1 << i);
    }

    public void setPreferDay(int i){
        secondaryDaysMask |= (1 << i);
    }

    public void setAvailableAtTime(int i){
        mainTimesMask |= (1 << i);
    }

    public void setPreferTime(int i){
        secondaryTimesMask |= (1 << i);
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
}
