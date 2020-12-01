package actions.recommendations;

import common.Constants;
import data.Data;
import entertainment.Show;
import user.User;
import utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public final  class FavoriteRecommendation extends Recommendation {
    private final List<User> userList;
    private String title;
    private List<Show> showList;

    public FavoriteRecommendation() {
        title = null;
        showList = Data.getShowList();
        userList = Data.getUsers();
    }

    public String execute(final User user) {
        if (user == null || securityCheckFail(user)) {
            title = null;
            return getQueryMessage(); // return error if user is not PREMIUM
        }

        // Filter the show list -> only the ones not seen by user
        //                      -> only the ones at least in a user's favorites list
        showList = showList.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .filter(show -> Utils.hasBeenFavorited(userList, show))
                .collect(Collectors.toList());

        Show mostFavoriteShow = null;
        int maxNumOfFavorites = -1;

        for (Show show : showList) {
            int numOfFavorites = Utils.getUserStats(userList, show, Constants.CRITERIA_FAVORITE);
            if (numOfFavorites > maxNumOfFavorites) {
                maxNumOfFavorites = numOfFavorites;
                mostFavoriteShow = show;
            }
        }

        if (mostFavoriteShow != null) {
            title = mostFavoriteShow.getTitle();
        }

        return getQueryMessage();
    }


    String getQueryMessage() {
        StringBuilder message = new StringBuilder();

        if (title == null) {
            message.append(Constants.FAVORITE_RECOMM_PREFIX + " " + Constants.RECOMM_ERROR_SUFFIX);
        } else {
            message.append(Constants.FAVORITE_RECOMM_PREFIX + " " + Constants.RECOMM_RESULTS);
            message.append(title);
        }

        return message.toString();
    }
}
