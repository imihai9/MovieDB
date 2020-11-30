package comparators;

import entertainment.Show;

import java.util.Comparator;

public class CompareShowsTitle implements Comparator<Show> {
    @Override
    public int compare(Show o1, Show o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
