package user;

import entertainment.Movie;
import entertainment.Season;

import java.util.*;

/**
 * Information about an user
 */
public class User  {
    /**
     * User's username
     */
    private String username;
    /**
     * Subscription Type
     */
    private String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final List<String> favoriteMovies;
    /**
     * Map between movies and the corresponding ratings given by the user
     */
    private Map<Movie, Double> movieRatings;
    /**
     * Map between seasons and the corresponding ratings given by the user
     */
    private Map<Season, Double> seasonRatings;

    public User(String username, String subscriptionType,
                         Map<String, Integer> history,
                         ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        movieRatings = new HashMap<Movie, Double>();
        seasonRatings = new HashMap<Season, Double>();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return Collections.unmodifiableMap(history);
    }

    public void addToHistory(String title, int timesSeen) {
        history.put(title, timesSeen);
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public List<String> getFavoriteMovies() {
        return Collections.unmodifiableList(favoriteMovies);
    }

    public void addFavouriteMovie(String title) {
        favoriteMovies.add(title);

    }
    public Map<Movie, Double> getMovieRatings() {
        return Collections.unmodifiableMap(movieRatings);
    }

    public void addMovieRating(Movie movie, Double rating) {
        movieRatings.put(movie, rating);
    }

    public Map<Season, Double> getSeasonRatings() {
        return Collections.unmodifiableMap(seasonRatings);
    }

    public void addSeasonRating(Season season, Double rating) {
        seasonRatings.put(season, rating);
    }

    @Override
    public String toString() {
        return "User{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
