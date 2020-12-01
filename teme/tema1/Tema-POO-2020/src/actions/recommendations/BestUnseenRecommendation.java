package actions.recommendations;

import common.Constants;
import comparators.CompareShowsAvgRating;
import data.Data;
import entertainment.Show;
import user.User;

import java.util.Comparator;
import java.util.List;

public class BestUnseenRecommendation extends Recommendation{
    private String title;
    private final List<Show> showList;

    public BestUnseenRecommendation() {
        title = null;
        // List containing all shows (movies and serials alike)
        showList = Data.getShowList();
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String execute(User user) {
        Comparator<Show> comparator = new CompareShowsAvgRating().reversed();

        // Filter the show list -> only the ones not seen by user
        // Reverse sort the show list by average rating, get first element if exists
        showList.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .min(comparator)
                .ifPresent(show -> setTitle(show.getTitle()));

        return getQueryMessage();
    }

    String getQueryMessage() {
        StringBuilder message = new StringBuilder();

        if (title == null) {
            message.append(Constants.BEST_UNSEEN_RECOMM_PREFIX + " " + Constants.RECOMM_ERROR_SUFFIX);
        }

        else {
            message.append(Constants.BEST_UNSEEN_RECOMM_PREFIX + " " + Constants.RECOMM_RESULTS);
            message.append(title);
        }

        return message.toString();
    }
}