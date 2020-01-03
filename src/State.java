import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class State implements Cloneable {
    ArrayList<Integer> list;
    int currentCity;

    public State() {
        currentCity = -1;
        list = new ArrayList<>();
    }

    ArrayList<State> generateNext(ArrayList<Integer> nodes) {

        ArrayList<State> ret = new ArrayList<>();
        for (Integer next : nodes) {
            if (next == currentCity)
                continue;
            State s = (State) clone();
            s.list.add(next);
            s.currentCity = next;
            ret.add(s);
        }
        return ret;
    }

    public boolean isFinal(ArrayList<Integer> nodes) {
        TreeSet<Integer> set = new TreeSet<>();
        set.addAll(nodes);
        set.removeAll(list);
        if (set.isEmpty())
            return list.get(0) == currentCity;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return currentCity == state.currentCity &&
                list.equals(state.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    protected Object clone() {
        State s = new State();
        s.list = (ArrayList<Integer>) list.clone();
        s.currentCity = currentCity;
        return s;
    }

    public static void solve() throws FileNotFoundException {
        int[][] adj = new int[10][10];
        int MAX = (int) 1e9;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                adj[i][j] = MAX;
        Scanner cin = new Scanner(new BufferedInputStream(new FileInputStream("input.txt")));
        int n = cin.nextInt();
        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            nodes.add(i);
        int m = cin.nextInt();
        for (int i = 0; i < m; i++) {
            int x, y, z;
            x = cin.nextInt();
            y = cin.nextInt();
            z = cin.nextInt();
            adj[x][y] = z;
            adj[y][x] = z;
        }
        PriorityQueue<PqPair<State>> pq = new PriorityQueue<>();
        State start = new State();
        start.list.add(1);
        start.currentCity = 1;
        pq.add(new PqPair<>(0, start));
        HashMap<State, Integer> mp = new HashMap<>();
        while (!pq.isEmpty()) {

            PqPair<State> p = pq.poll();
            Integer cost = p.first;
            State s = p.second;

            if (s.isFinal(nodes)) {
                System.out.println("fuck!! it's solved");
                System.out.println("the final cost is " + cost);
                System.out.println(s.list);
                return;
            }

            if (mp.containsKey(s))
                if (cost > mp.get(s))
                    continue;

            ArrayList<State> list = s.generateNext(nodes);
            for (State child : list) {
                int edge = adj[child.currentCity][s.currentCity];
                if (edge == MAX)
                    continue;
                edge += cost;
                if (mp.containsKey(child)) {

                    int oldCost = mp.get(child);
                    if (oldCost > edge) {
                        mp.put(child, edge);
                        pq.add(new PqPair<>(edge, child));
                    }
                } else {
                    mp.put(child, edge);
                    pq.add(new PqPair<>(edge, child));
                }
            }
        }
        System.out.println("fuck You");
    }
}
