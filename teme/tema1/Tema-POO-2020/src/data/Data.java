package data;

import actions.commands.Command;
import actions.commands.FavoriteCommand;
import actions.commands.RatingCommand;
import actions.commands.ViewCommand;
import actions.queries.ActorQuery;
import actions.queries.Query;
import actions.queries.ShowQuery;
import actions.queries.UserQuery;
import actions.recommendations.BestUnseenRecommendation;
import actions.recommendations.FavoriteRecommendation;
import actions.recommendations.PopularRecommendation;
import actions.recommendations.Recommendation;
import actions.recommendations.SearchRecommendation;
import actions.recommendations.StandardRecommendation;
import actor.Actor;
import common.Constants;
import entertainment.Genre;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Data {
    private static List<Actor> actors;
    private static List<Movie> movies;
    private static List<Serial> serials;
    private static List<User> users;

    public Data() {
        actors = new ArrayList<>();
        movies = new ArrayList<>();
        serials = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static List<Actor> getActors() {
        return Collections.unmodifiableList(actors);
    }

    public static List<Movie> getMovies() {
        return Collections.unmodifiableList(movies);
    }

    public static List<Serial> getSerials() {
        return Collections.unmodifiableList(serials);
    }

    public static List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * @return - a list containing all shows (movies + serials, in this order)
     */
    public static List<Show> getShowList() {
        List<Movie> movieList = Data.getMovies();
        List<Serial> serialList = Data.getSerials();

        List<Show> showList = new ArrayList<>(movieList);
        showList.addAll(serialList);

        return showList;
    }
    //Helper function

    /**
     * Helper function. Transforms a list of show titles to a list of show objects
     *
     * @param filmographyTitles - Show titles to be searched
     * @return - a Show ArrayList containing all Shows (Movies/Serial) with given titles
     */
    private List<Show> getActorShows(final List<String> filmographyTitles) {
        List<Show> filmography = new ArrayList<>();
        if (filmographyTitles.isEmpty()) {
            return filmography;
        }

        for (String showTitle : filmographyTitles) {
            // Search in movies list:
            Optional<Movie> movieOptional = movies.stream()
                    .filter(s -> s.getTitle().equals(showTitle))
                    .findFirst();

            if (movieOptional.isPresent()) {
                filmography.add(movieOptional.get());
            } else {
                Optional<Serial> serialOptional = serials.stream()
                        .filter(s -> s.getTitle().equals(showTitle))
                        .findFirst();

                serialOptional.ifPresent(filmography::add);
            }
        }

        return filmography;
    }

    /**
     * Reads data from 'input' to inner fields (lists of actors, movies, serials and users)
     * @param input - Input object containing the data that will be read
     */
    public void readData(final Input input) {
        for (UserInputData userData : input.getUsers()) {
            User newUser = new User(userData.getUsername(), userData.getSubscriptionType(),
                    userData.getHistory(), userData.getFavoriteMovies());

            users.add(newUser);
        }

        for (MovieInputData movieData : input.getMovies()) {
            // Transform genres from String to Genre type
            ArrayList<String> genreNames = movieData.getGenres();
            ArrayList<Genre> genres = new ArrayList<>();
            genreNames.forEach(genreName -> genres.add(Utils.stringToGenre(genreName)));

            Movie newMovie = new Movie(movieData.getTitle(), movieData.getCast(),
                    genres, movieData.getYear(), movieData.getDuration());

            movies.add(newMovie);
        }

        for (SerialInputData serialData : input.getSerials()) {
            // Transform genres from String to Genre type
            ArrayList<String> genreNames = serialData.getGenres();
            ArrayList<Genre> genres = new ArrayList<>();
            genreNames.forEach(genreName -> genres.add(Utils.stringToGenre(genreName)));

            Serial newSerial = new Serial(serialData.getTitle(), serialData.getCast(),
                    genres, serialData.getNumberSeason(),
                    serialData.getSeasons(), serialData.getYear());

            serials.add(newSerial);
        }

        for (ActorInputData actorData : input.getActors()) {
            List<String> filmographyTitles = actorData.getFilmography();
            List<Show> filmography = getActorShows(filmographyTitles);

            Actor newActor = new Actor(actorData.getName(), actorData.getCareerDescription(),
                    filmography, actorData.getAwards());

            actors.add(newActor);
        }
    }

    /**
     * Searches the User object that has the corresponding *username*
     * @param username - the username of the requested User
     * @return - the requested User, if it exists
     *         - null, otherwise
     */
    public User getUserByUsername(final String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Driver-method for *actions*.
     * @param input - Input object (for reading the actions)
     * @param fileWriter - Writer object used to write to output files
     * @param arrayResult
     * @throws IOException
     */
    public void processData(final Input input, final Writer fileWriter, final JSONArray arrayResult)
            throws IOException {
        for (ActionInputData actionData : input.getCommands()) {
            String actionType = actionData.getActionType();
            int actionId = actionData.getActionId();

            String message = null;

            switch (actionType) {
                case Constants.COMMAND -> {
                    User user = getUserByUsername(actionData.getUsername());

                    String title = actionData.getTitle();
                    int seasonNumber = actionData.getSeasonNumber();

                    String commandType = actionData.getType();

                    Command command = switch (commandType) {
                        case Constants.VIEW_COMMAND -> new ViewCommand(user, title, seasonNumber);
                        case Constants.FAVORITE_COMMAND -> new FavoriteCommand(user, title,
                                seasonNumber);
                        // The remaining case: RATING_COMMAND
                        default -> new RatingCommand(user, title, actionData.getGrade(),
                                seasonNumber);
                    };

                    message = command.execute();
                }
                case Constants.QUERY -> {
                    String objectType = actionData.getObjectType();

                    Query query = switch (objectType) {
                        case Constants.ACTORS -> new ActorQuery();
                        case Constants.MOVIES, Constants.SHOWS -> new ShowQuery(objectType);
                        // The remaining case: USER Query
                        default -> new UserQuery();
                    };

                    message = query.execute(actionData.getNumber(),
                            actionData.getFilters(), actionData.getSortType(),
                            actionData.getCriteria());

                }
                case Constants.RECOMMENDATION -> {
                    String type = actionData.getType();

                    User user = getUserByUsername(actionData.getUsername());
                    Recommendation recommendation = switch (type) {
                        case Constants.STANDARD_RECOMM -> new StandardRecommendation();
                        case Constants.BEST_UNSEEN_RECOMM -> new BestUnseenRecommendation();
                        case Constants.POPULAR_RECOMM -> new PopularRecommendation();
                        case Constants.FAVORITE_RECOMM -> new FavoriteRecommendation();
                        // The remaining case: SEARCH RECOMMENDATION
                        default -> new SearchRecommendation(
                                Utils.stringToGenre(actionData.getGenre()));
                    };

                    message = recommendation.execute(user);
                }

                default -> {
                    message = null;
                }
            }

            if (message != null) {
                JSONObject output = fileWriter.writeFile(actionId, null, message);
                arrayResult.add(output);
            }
        }
    }
}
