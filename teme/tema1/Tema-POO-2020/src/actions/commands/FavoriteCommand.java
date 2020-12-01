package actions.commands;

import user.User;

public final class FavoriteCommand extends Command {

    public FavoriteCommand(final User user, final String title, final int seasonNumber) {
        super(user, title, seasonNumber);
    }

    public String execute() {
        String message;

        if (!user.getHistory().containsKey(title)) {
            message = "error -> " + title + " is not seen";
        } else if (user.getFavoriteShows().contains(title)) {
            message = "error -> " + title + " is already in favourite list";
        } else {
            user.addFavoriteShow(title);
            message = "success -> " + title + " was added as favourite";
        }
        return message;
    }
}
