package comparators;

import entertainment.Show;

import java.util.Comparator;

public final class CompareShowsAvgRating implements Comparator<Show> {
    @Override
    public int compare(final Show o1, final Show o2) {
        return Double.compare(o1.getAverageRating(), o2.getAverageRating());
    }
}
