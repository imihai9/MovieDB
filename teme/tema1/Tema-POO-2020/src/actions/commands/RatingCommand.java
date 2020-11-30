package actions.commands;

import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import user.User;
import data.Data;

public class RatingCommand extends Command{
    private final Double grade;

    public RatingCommand(User user, String title, Double grade, int seasonNumber) {
        super(user, title, seasonNumber);
        this.grade = grade;
    }

    public String execute() {
        String message;
        if (!user.getHistory().containsKey(title)) {
            message = "error -> " + title + " is not seen";
        }

        else if (seasonNumber == 0) {  // if *title* is a Movie
            Movie movie = Data.getMovies().stream()
                    .filter(s -> s.getTitle().equals(title))
                    .findFirst()
                    .get();

            if (user.getMovieRatings().containsKey(movie)) {
                    message = "error -> " + title + " has been already rated";
                }

                else {
                    user.addMovieRating(movie, grade);
                    movie.addRating(grade);
                    message = "success -> " + title + " was rated with " + grade + " by " + user.getUsername();
                }
            }

        else {
            Serial serial = Data.getSerials().stream()
                    .filter(s -> s.getTitle().equals(title))
                    .findFirst()
                    .get();

            Season season = serial.getSeasons().stream()
                    .filter(s -> s.getCurrentSeason() == seasonNumber)
                    .findFirst()
                    .get();

            if (user.getSeasonRatings().containsKey(season)) {
                message = "error -> " + title + " has been already rated";
            }

            else {
                user.addSeasonRating(season, grade);
                season.addRating(grade);
                message = "success -> " + title + " was rated with " + grade + " by " + user.getUsername();
            }
        }

        return message;
    }
}
