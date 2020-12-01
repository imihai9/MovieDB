package comparators;

import common.Constants;
import entertainment.Show;
import user.User;
import utils.Utils;

import java.util.Comparator;
import java.util.List;

/**
 * Compares two shows by "most viewed" criteria, based on provided users list
 */
public final class CompareShowsMostViewed implements Comparator<Show> {
    private List<User> users;

    public CompareShowsMostViewed(final List<User> users) {
        this.users = users;
    }

    @Override
    public int compare(final Show o1, final Show o2) {
        return Integer.compare(Utils.getUserStats(users, o1, Constants.CRITERIA_MOST_VIEWED),
                Utils.getUserStats(users, o2, Constants.CRITERIA_MOST_VIEWED));
    }
}
