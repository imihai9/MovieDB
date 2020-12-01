package entertainment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Information about a movie
 */
public final class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * List of all ratings given
     */
    private final List<Double> ratings;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<Genre> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        ratings = new ArrayList<>();
    }

    public Integer getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return Collections.unmodifiableList(ratings);
    }

    /**
     * Calculates the average rating for the current movie
     * @return - the average movie rating
     */
    public Double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0d;
        }

        Double ratingSum = 0d;
        for (Double rating : ratings) {
            ratingSum += rating;
        }

        return ratingSum / ratings.size();
    }

    /**
     * @return - true, if current movie has been rated
     *         - false, otherwise
     */
    @Override
    public boolean hasBeenRated() {
        return !ratings.isEmpty();
    }

    /**
     * Adds given rating to the rating list of the current movie
     * @param rating - the rating to be added
     */
    public void addRating(final Double rating) {
        ratings.add(rating);
    }

    @Override
    public String toString() {
        return "Movie{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
