package comparators;

import user.User;

import java.util.Comparator;

public final class CompareUsersNumRatings implements Comparator<User> {
    @Override
    public int compare(final User o1, final User o2) {
        return Integer.compare(o1.getTotalRatingsNum(), o2.getTotalRatingsNum());
    }
}
