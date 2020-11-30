package entertainment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * General information about show (video)
 */
public abstract class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final List<String> cast;
    /**
     * Show genres
     */
    private final List<Genre> genres;

    public Show(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<Genre> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final List<String> getCast() {
        return Collections.unmodifiableList(cast);
    }

    public final List<Genre> getGenres() {
        return Collections.unmodifiableList(genres);
    }

    // To be overridden

    public abstract Double getAverageRating();

    public abstract boolean hasBeenRated();

    public abstract Integer getDuration();

}
