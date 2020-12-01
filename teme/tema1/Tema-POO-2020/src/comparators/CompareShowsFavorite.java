package comparators;

import common.Constants;
import data.Data;
import entertainment.Show;
import user.User;
import utils.Utils;

import java.util.Comparator;
import java.util.List;

public class CompareShowsFavorite implements Comparator<Show> {
    List<User> users;

    public CompareShowsFavorite(List<User> users) {
        this.users = users;
    }

    @Override
    public int compare(Show o1, Show o2) {
        return Integer.compare(Utils.getUserStats(users, o1, Constants.CRITERIA_FAVORITE),
                Utils.getUserStats(users, o2, Constants.CRITERIA_FAVORITE));
    }
}
