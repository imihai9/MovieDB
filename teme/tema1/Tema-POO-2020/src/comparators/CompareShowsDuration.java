package comparators;

import entertainment.Show;

import java.util.Comparator;

public class CompareShowsDuration implements Comparator<Show> {
    @Override
    public int compare(Show o1, Show o2) {
        return Integer.compare(o1.getDuration(), o2.getDuration());
    }
}
