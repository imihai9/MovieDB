package entertainment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Information about a movie
 */
public class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * List of all ratings given
     */
    private List<Double> ratings;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<Genre> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        ratings = new ArrayList<Double>();
    }

    public Integer getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return Collections.unmodifiableList(ratings);
    }

    public Double getAverageRating() {
        if (ratings.isEmpty())
            return 0d;

        Double ratingSum = 0d;
        for (Double rating:ratings) {
            ratingSum += rating;
        }

        return ratingSum / ratings.size();
    }

    @Override
    public boolean hasBeenRated() {
        return !ratings.isEmpty();
    }

    public void addRating(Double rating) {
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
