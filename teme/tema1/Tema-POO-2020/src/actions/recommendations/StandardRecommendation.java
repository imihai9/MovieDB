package actions.recommendations;

import common.Constants;
import data.Data;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard Recommendation = first unseen video by user
 */
public class StandardRecommendation extends Recommendation{
    private String title;
    private List<Show> showList;

    public StandardRecommendation() {
        title = null;
        // List containing all shows (movies and serials alike)
        showList = Data.getShowList();
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String execute(User user) {
        showList.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .findFirst()
                .ifPresent(show -> setTitle(show.getTitle()));

        return getQueryMessage();
    }

    String getQueryMessage() {
        StringBuilder message = new StringBuilder();

        if (title == null) {
            message.append(Constants.STANDARD_RECOMM_PREFIX + " " + Constants.RECOMM_ERROR_SUFFIX);
        }

        else {
            message.append(Constants.STANDARD_RECOMM_PREFIX + " " + Constants.RECOMM_RESULTS);
            message.append(title);
        }

        return message.toString();
    }
}
