package data;

import actions.commands.Command;
import actions.commands.FavoriteCommand;
import actions.commands.RatingCommand;
import actions.commands.ViewCommand;
import actions.queries.ActorQuery;
import actions.queries.Query;
import actions.queries.ShowQuery;
import actions.queries.UserQuery;
import actions.recommendations.*;
import actor.Actor;
import common.Constants;
import entertainment.Genre;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;
import utils.Utils;

import java.io.IOException;
import java.util.*;

public class Data { //TODO: does this have to be singleton?
    private static List<Actor> actors; //TODO: add final
    private static List<Movie> movies;
    private static List<Serial> serials;
    private static List<Command> commands;
    private static List<User> users;

    public Data() {
        actors = new ArrayList<Actor>();
        movies = new ArrayList<Movie>();
        serials = new ArrayList<Serial>();
        commands = new ArrayList<Command>();
        users = new ArrayList<User>();
    }

    public static List<Actor> getActors() {
        return Collections.unmodifiableList(actors);
    }

    public static List<Movie> getMovies() {
        return Collections.unmodifiableList(movies);
    }

    public static List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
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
     * @param filmographyTitles - Show titles to be searched
     * @return - a Show ArrayList containing all Shows (Movies/Serial) with given titles
     */
    private List<Show> getActorShows(List<String> filmographyTitles) {
        List<Show> filmography = new ArrayList<Show>();
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
            }

            else {
                Optional<Serial> serialOptional = serials.stream()
                        .filter(s -> s.getTitle().equals(showTitle))
                        .findFirst();

                if (serialOptional.isPresent()) {
                    filmography.add(serialOptional.get());
                }
            }
        }

        return filmography;
    }

    public void readData (Input input) {
        for (UserInputData userData:input.getUsers()) {
            User newUser = new User(userData.getUsername(), userData.getSubscriptionType(),
                    userData.getHistory(), userData.getFavoriteMovies());

            users.add(newUser);
        }

        for (MovieInputData movieData:input.getMovies()) {
            // Transform genres from String to Genre type
            ArrayList<String> genreNames = movieData.getGenres();
            ArrayList<Genre> genres = new ArrayList<Genre>();
            genreNames.forEach(genreName -> genres.add(Utils.stringToGenre(genreName)));

            Movie newMovie = new Movie(movieData.getTitle(), movieData.getCast(),
                    genres, movieData.getYear(), movieData.getDuration());

            movies.add(newMovie);
        }

        for (SerialInputData serialData:input.getSerials()) {
            // Transform genres from String to Genre type
            ArrayList<String> genreNames = serialData.getGenres();
            ArrayList<Genre> genres = new ArrayList<Genre>();
            genreNames.forEach(genreName -> genres.add(Utils.stringToGenre(genreName)));

            Serial newSerial = new Serial(serialData.getTitle(), serialData.getCast(),
                    genres, serialData.getNumberSeason(),
                    serialData.getSeasons(), serialData.getYear());

            serials.add(newSerial);
        }

        for (ActorInputData actorData:input.getActors()) {
            List<String> filmographyTitles = actorData.getFilmography();
            List<Show> filmography = getActorShows(filmographyTitles);

            Actor newActor = new Actor(actorData.getName(), actorData.getCareerDescription(),
                    filmography, actorData.getAwards());

            actors.add(newActor);
        }
    }

    public User getUserByUsername (String username) {
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .get();

        return user;
    }

    public void processData(Input input, Writer fileWriter, JSONArray arrayResult) throws IOException {
        for (ActionInputData actionData:input.getCommands()) {
            String actionType = actionData.getActionType();
            int actionId = actionData.getActionId();

            if (actionType.equals(Constants.COMMAND)) {
                User user = getUserByUsername(actionData.getUsername());

                String title = actionData.getTitle();
                int seasonNumber = actionData.getSeasonNumber();
                String commandType = actionData.getType();

                Command command;

                switch (commandType) {
                    case Constants.VIEW_COMMAND:
                        command = new ViewCommand(user, title, seasonNumber);
                        break;

                    case Constants.FAVORITE_COMMAND:
                        command = new FavoriteCommand(user, title, seasonNumber);
                        break;

                    case Constants.RATING_COMMAND:
                    default:
                        command = new RatingCommand(user, title, actionData.getGrade(), seasonNumber);
                        break;
                }

                String message = command.execute();
                if (message != null) {
                    JSONObject output = fileWriter.writeFile(actionId, null, message);
                    arrayResult.add(output);
                }
            } else if (actionType.equals(Constants.QUERY)) {
                String message;
                String objectType = actionData.getObjectType();
                List<List<String>> filters = actionData.getFilters();

                Query query;

                switch (objectType) {
                    case Constants.ACTORS:
                        query = new ActorQuery();
                        break;

                    case Constants.MOVIES:
                    case Constants.SHOWS:
                        query = new ShowQuery(objectType);
                        break;

                    case Constants.USERS:
                    default:
                        query = new UserQuery();
                        break;
                }

                message = query.execute(actionData.getNumber(),
                        actionData.getFilters(), actionData.getSortType(), actionData.getCriteria());

                JSONObject output = fileWriter.writeFile(actionId, null, message);
                arrayResult.add(output);
            } else if (actionType.equals(Constants.RECOMMENDATION)) {
                String type = actionData.getType();

                User user = getUserByUsername(actionData.getUsername());
                Recommendation recommendation;

                switch (type) {
                    case Constants.STANDARD_RECOMM:
                        recommendation = new StandardRecommendation();
                        break;

                    case Constants.BEST_UNSEEN_RECOMM:
                        recommendation = new BestUnseenRecommendation();
                        break;

                    case Constants.POPULAR_RECOMM:
                        recommendation = new PopularRecommendation();
                        break;

                    case Constants.FAVORITE_COMMAND:
                        recommendation = new FavoriteRecommendation();
                        break;

                    default:
                    case Constants.SEARCH_RECOMM:
                        recommendation = new SearchRecommendation(Utils.stringToGenre(actionData.getGenre()));
                        break;
                }

                    String message = recommendation.execute(user);
                    JSONObject output = fileWriter.writeFile(actionId, null, message);
                    arrayResult.add(output);
                }
            }
        }
    }
