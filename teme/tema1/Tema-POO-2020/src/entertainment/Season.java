package entertainment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * List of ratings for each season
     */
    private final List<Double> ratings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getCurrentSeason() {
        return currentSeason;
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

    public void addRating(Double rating) {
        ratings.add(rating);
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

