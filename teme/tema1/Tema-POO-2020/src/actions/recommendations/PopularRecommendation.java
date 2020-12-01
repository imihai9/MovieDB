package actions.recommendations;

import common.Constants;
import data.Data;
import entertainment.Genre;
import entertainment.Show;
import user.User;
import utils.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularRecommendation extends Recommendation {
    private String title;
    private final List<User> userList;
    private List<Show> showList;

    public PopularRecommendation() {
        title = null;
        // List containing all users
        userList = Data.getUsers();
        // List containing all shows (movies and serials alike)
        showList = Data.getShowList();
    }

    private void setTitle(String title) {
        this.title = title;
    }


    public String execute(User user) {
        if (user == null || securityCheckFail(user)) {
            title = null;
            return getQueryMessage(); // return error if user is not PREMIUM
        }

        Map<Genre, Integer> genresMap = Utils.getFavoriteGenres(userList, showList);

        // If no user has viewed any shows
        if (genresMap.isEmpty()) {
            title = null;
            return getQueryMessage();
        }

        // Filter the show list -> only the ones not seen by user
        showList = showList.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .collect(Collectors.toList());

        while (title == null && !genresMap.isEmpty()) {
            // Gets most popular genre
            Genre mostPopularGenre = Collections.max(genresMap.entrySet(), Map.Entry.comparingByValue()).getKey();
            // Remove most popular genre (in order to get the next best genre on next iteration)
            genresMap.remove(mostPopularGenre);

            showList.stream()
                    .filter(show -> show.getGenres().contains(mostPopularGenre))
                    .findFirst()
                    .ifPresent(show -> setTitle(show.getTitle()));
        }

        return getQueryMessage();
    }


    String getQueryMessage() {
        StringBuilder message = new StringBuilder();

        if (title == null) {
            message.append(Constants.POPULAR_RECOMM_PREFIX + " " + Constants.RECOMM_ERROR_SUFFIX);
        }

        else {
            message.append(Constants.POPULAR_RECOMM_PREFIX + " " + Constants.RECOMM_RESULTS);
            message.append(title);
        }

        return message.toString();
    }
}
