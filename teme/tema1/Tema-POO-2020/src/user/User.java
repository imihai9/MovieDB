package user;

import entertainment.Movie;
import entertainment.Season;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about an user
 */
public final class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final SubscriptionType subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final List<String> favoriteShows;
    /**
     * Map between movies and the corresponding ratings given by the user
     */
    private final Map<Movie, Double> movieRatings;
    /**
     * Map between seasons and the corresponding ratings given by the user
     */
    private final Map<Season, Double> seasonRatings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteShows) {
        this.username = username;
        this.subscriptionType = Utils.stringToSubType(subscriptionType);
        this.favoriteShows = favoriteShows;
        this.history = history;
        movieRatings = new HashMap<>();
        seasonRatings = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return Collections.unmodifiableMap(history);
    }

    /**
     * Adds given show title to user's watch history
     * @param title - the title of the show
     * @param timesSeen - the times the show was watched
     */
    public void addToHistory(final String title, final int timesSeen) {
        history.put(title, timesSeen);
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public List<String> getFavoriteShows() {
        return Collections.unmodifiableList(favoriteShows);
    }

    /**
     * Adds given show title to user's favorite list
     * @param title - the title of the show
     */
    public void addFavoriteShow(final String title) {
        favoriteShows.add(title);
    }

    public Map<Movie, Double> getMovieRatings() {
        return Collections.unmodifiableMap(movieRatings);
    }

    /**
     * Adds given movie-rating pair to user's movieRatings map
     * @param movie - the movie to be rated
     * @param rating - the rating the movie will receive
     */
    public void addMovieRating(final Movie movie, final Double rating) {
        movieRatings.put(movie, rating);
    }

    public Map<Season, Double> getSeasonRatings() {
        return Collections.unmodifiableMap(seasonRatings);
    }

    /**
     * Adds given season-rating pair to user's seasonRatings map
     * @param season - the season to be rated
     * @param rating - the rating the season will receive
     */
    public void addSeasonRating(final Season season, final Double rating) {
        seasonRatings.put(season, rating);
    }

    public Integer getTotalRatingsNum() {
        return movieRatings.size() + seasonRatings.size();
    }

    @Override
    public String toString() {
        return "User{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteShows="
                + favoriteShows + '}';
    }
}
