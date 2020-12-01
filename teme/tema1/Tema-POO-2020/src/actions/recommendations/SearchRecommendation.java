package actions.recommendations;

import common.Constants;
import comparators.CompareShowsAvgRating;
import comparators.CompareShowsTitle;
import data.Data;
import entertainment.Genre;
import entertainment.Show;
import user.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRecommendation extends Recommendation {
    private Genre genre;
    private List<Show> showList;

    public SearchRecommendation(Genre genre) {
        this.genre = genre;
        showList = Data.getShowList();
    }

    public String execute(User user) {
        if (user == null || securityCheckFail(user)) {
            return getQueryMessage(); // return error if user is not PREMIUM
        }

        Comparator<Show> comparator = new CompareShowsAvgRating().thenComparing(new CompareShowsTitle());

        // Filter the show list -> only the ones not seen by user
        //                      -> that belong to the specified genre
        // Sort it by average rating, then by title

        showList = showList.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .filter(show -> show.getGenres().contains(genre))
                .sorted(comparator)
                .collect(Collectors.toList());
        
        return getQueryMessage();
    }


    String getQueryMessage() {
        StringBuilder message = new StringBuilder();

        if (showList.isEmpty()) {
            message.append(Constants.SEARCH_RECOMM_PREFIX + " " + Constants.RECOMM_ERROR_SUFFIX);
        }

        else {
            message.append(Constants.SEARCH_RECOMM_PREFIX + " " + Constants.RECOMM_RESULTS);
            message.append(Constants.RESULT_BRACKET_OPEN); // '['

            for (Show show:showList) {
                message.append(show.getTitle());
                message.append(Constants.QUERY_RESULT_DELIMITER);
            }

            message.setLength(message.length() - Constants.QUERY_DELIMITER_SIZE); // deleting the last ", "
            message.append(Constants.QUERY_RESULT_SUFFIX); // ']'
        }

        return message.toString();
    }
}
