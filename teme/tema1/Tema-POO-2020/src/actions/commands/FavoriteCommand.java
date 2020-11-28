package actions.commands;

import user.User;

public class FavoriteCommand extends Command{

    public FavoriteCommand(int actionId, User user, String title, int seasonNumber) {
        super(actionId, user, title, seasonNumber);
    }

    public String execute() {
        String message;

        if (!user.getHistory().containsKey(title))
            message = "error -> " + title + " is not seen";
        else if (user.getFavoriteMovies().contains(title)) {
            message = "error -> " + title + " is already in favourite list";
        }
        else {
            user.addFavouriteMovie(title);
            message = "success -> " + title + " was added as favourite";
        }
        return message;
    }
}
