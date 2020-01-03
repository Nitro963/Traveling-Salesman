public class PqPair<T> extends Pair<Integer, T> implements Comparable<PqPair<T>> {
    public PqPair(Integer first, T second) {
        super(first, second);
    }

    @Override
    public int compareTo(PqPair<T> o) {
        return first - o.first;
    }
}
