package actions.commands;

import data.Data;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import user.User;

public final class RatingCommand extends Command {
    private final Double grade;

    public RatingCommand(final User user, final String title, final Double grade,
                         final int seasonNumber) {
        super(user, title, seasonNumber);
        this.grade = grade;
    }

    public String execute() {
        String message;
        if (!user.getHistory().containsKey(title)) {
            message = "error -> " + title + " is not seen";
        } else if (seasonNumber == 0) {  // if *title* is a Movie
            Movie movie = Data.getMovies().stream()
                    .filter(s -> s.getTitle().equals(title))
                    .findFirst()
                    .orElse(null);

            if (movie == null) {
                return "error";
            }

            if (user.getMovieRatings().containsKey(movie)) {
                message = "error -> " + title + " has been already rated";
            } else {
                user.addMovieRating(movie, grade);

                movie.addRating(grade);
                message = "success -> " + title + " was rated with " + grade + " by "
                        + user.getUsername();
            }
        } else {
            Serial serial = Data.getSerials().stream()
                    .filter(s -> s.getTitle().equals(title))
                    .findFirst()
                    .orElse(null);

            if (serial == null) {
                return "error";
            }

            Season season = serial.getSeasons().stream()
                    .filter(s -> s.getCurrentSeason() == seasonNumber)
                    .findFirst()
                    .orElse(null);

            if (season == null) {
                return "error";
            }

            if (user.getSeasonRatings().containsKey(season)) {
                message = "error -> " + title + " has been already rated";
            } else {
                user.addSeasonRating(season, grade);
                season.addRating(grade);
                message = "success -> " + title + " was rated with " + grade + " by "
                        + user.getUsername();
            }
        }

        return message;
    }
}
