package comparators;

import common.Constants;
import entertainment.Show;
import utils.Utils;

import java.util.Comparator;

public class CompareShowsFavorite implements Comparator<Show> {

    @Override
    public int compare(Show o1, Show o2) {
        return Integer.compare(Utils.getUserStats(o1, Constants.CRITERIA_FAVORITE),
                Utils.getUserStats(o2, Constants.CRITERIA_FAVORITE));
    }
}
