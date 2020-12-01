package actions.recommendations;

import common.Constants;
import data.Data;
import entertainment.Show;
import user.User;

import java.util.List;

/**
 * Standard Recommendation = first unseen video by user
 */
public final class StandardRecommendation extends Recommendation {
    private final List<Show> showList;
    private String title;

    public StandardRecommendation() {
        title = null;
        // List containing all shows (movies and serials alike)
        showList = Data.getShowList();
    }

    private void setTitle(final String title) {
        this.title = title;
    }

    public String execute(final User user) {
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
        } else {
            message.append(Constants.STANDARD_RECOMM_PREFIX + " " + Constants.RECOMM_RESULTS);
            message.append(title);
        }

        return message.toString();
    }
}
