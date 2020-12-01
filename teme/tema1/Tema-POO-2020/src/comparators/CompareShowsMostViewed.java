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
public class CompareShowsMostViewed implements Comparator<Show> {
    List<User> users;

    public CompareShowsMostViewed(List<User> users) {
        this.users = users;
    }

    @Override
    public int compare(Show o1, Show o2) {
        return Integer.compare(Utils.getUserStats(users, o1, Constants.CRITERIA_MOST_VIEWED),
                Utils.getUserStats(users, o2, Constants.CRITERIA_MOST_VIEWED));
    }
}
