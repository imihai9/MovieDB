package comparators;

import user.User;

import java.util.Comparator;

public class CompareUsersNumRatings implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return Integer.compare(o1.getTotalRatingsNum(), o2.getTotalRatingsNum());
    }
}
