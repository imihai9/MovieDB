package comparators;

import entertainment.Show;

import java.util.Comparator;

public final class CompareShowsDuration implements Comparator<Show> {
    @Override
    public int compare(final Show o1, final Show o2) {
        return Integer.compare(o1.getDuration(), o2.getDuration());
    }
}