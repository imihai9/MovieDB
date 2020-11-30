package comparators;

import entertainment.Show;

import java.util.Comparator;

public class CompareShowsAvgRating implements Comparator<Show> {

    @Override
    public int compare(Show o1, Show o2) {
        return Double.compare(o1.getAverageRating(), o2.getAverageRating());
    }
}
