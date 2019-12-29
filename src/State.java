import java.util.ArrayList;
import java.util.TreeSet;

public class State {
    private ArrayList<TreeSet<Exam>> examSets;

    public State(int days) {
        examSets = new ArrayList<>();
        for(int i = 0;i < days; i++)
            examSets.add(new TreeSet<>());
    }
    public void addExam(Exam e, int i){
        examSets.get(i).add(e);
    }

}
