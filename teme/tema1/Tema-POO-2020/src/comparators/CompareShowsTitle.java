package comparators;

import entertainment.Show;

import java.util.Comparator;

public final class CompareShowsTitle implements Comparator<Show> {
    @Override
    public int compare(final Show o1, final Show o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
