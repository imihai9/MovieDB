package entertainment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Information about a tv show
 */
public class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final List<Season> seasons;

    public Serial(final String title, final ArrayList<String> cast,
                           final ArrayList<Genre> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return Collections.unmodifiableList(seasons);
    }

    /**
     * @return - a list containing the average rating for each season
     */

    @Override
    public Double getAverageRating() {
        Double ratingSum = 0d;

        for (Season season:seasons) {
            ratingSum += season.getAverageRating();
        }

        return ratingSum / numberOfSeasons;
    }

    // For ShowQuery -> a serial has been rated if at least a season has been rated
    @Override
    public boolean hasBeenRated() {
        for (Season season:seasons) {
            if (!season.getRatings().isEmpty())
                return true;
        }

        return false;
    }

    @Override
    public Integer getDuration() {
        Integer totalDuration = 0;
        for (Season season:seasons) {
            totalDuration += season.getDuration();
        }

        return totalDuration;
    }

    @Override
    public String toString() {
        return "Serial{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
