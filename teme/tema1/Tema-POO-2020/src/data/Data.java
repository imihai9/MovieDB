package data;

import actions.commands.Command;
import actions.commands.FavoriteCommand;
import actions.commands.RatingCommand;
import actions.commands.ViewCommand;
import actor.Actor;
import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.io.IOException;
import java.util.*;

public class Data {
    private static List<Actor> actors; //TODO: add final?!
    private static List<Movie> movies;
    private static List<Serial> serials;
    private static List<Command> commands;
    private static Map<String, User> users; //TODO: make users a simple arraylist, and use streams in action methods to
    //TODO: get the user by username (parameters will be usernames, not user objects)

    public Data() {
        actors = new ArrayList<Actor>();
        movies = new ArrayList<Movie>();
        serials = new ArrayList<Serial>();
        commands = new ArrayList<Command>();
        users = new HashMap<String, User>();
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

    public Map<String, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    public void readData (Input input) {
        for (UserInputData userData:input.getUsers()) {
            User newUser = new User(userData.getUsername(), userData.getSubscriptionType(),
                    userData.getHistory(), userData.getFavoriteMovies());

            users.put(newUser.getUsername(), newUser);
        }

        for (ActorInputData actorData:input.getActors()) {
            Actor newActor = new Actor(actorData.getName(), actorData.getCareerDescription(),
                    actorData.getFilmography(), actorData.getAwards());

            actors.add(newActor);
        }

        for (MovieInputData movieData:input.getMovies()) {
            Movie newMovie = new Movie(movieData.getTitle(), movieData.getCast(),
                    movieData.getGenres(), movieData.getYear(), movieData.getDuration());

            movies.add(newMovie);
        }

        for (SerialInputData serialData:input.getSerials()) {
            Serial newSerial = new Serial(serialData.getTitle(), serialData.getCast(),
                    serialData.getGenres(), serialData.getNumberSeason(),
                    serialData.getSeasons(), serialData.getYear());

            serials.add(newSerial);
        }
    }
    public void processData(Input input, Writer fileWriter, JSONArray arrayResult) throws IOException { //TODO: remove input
        for (ActionInputData actionData:input.getCommands()) {
            String actionType = actionData.getActionType();
            int actionId = actionData.getActionId();

            switch(actionType) {
                case Constants.COMMAND:
                    User user = users.get(actionData.getUsername());
                    String title = actionData.getTitle();
                    int seasonNumber = actionData.getSeasonNumber();

                    String commandType = actionData.getType();

                    Command command = null;

                    switch(commandType) {
                        case Constants.VIEW_COMMAND:
                            command = new ViewCommand(actionId, user, title, seasonNumber);
                            break;

                        case Constants.FAVORITE_COMMAND:
                            command = new FavoriteCommand(actionId, user, title, seasonNumber);
                            break;

                        case Constants.RATING_COMMAND:
                            command = new RatingCommand(actionId, user, title, actionData.getGrade(), seasonNumber);
                            break;
                        default:
                            break;
                    }

                    String message = command.execute();
                    if (message != null) {
                        JSONObject output = fileWriter.writeFile(actionId, null, message);
                        arrayResult.add(output);
                    }

                    break;

                case Constants.QUERY:
                    break;
                case Constants.RECOMMENDATION:
                    break;
                default:
                    break;
            }
        }
    }


}
