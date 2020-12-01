package actions.commands;

import user.User;

public abstract class Command {
    /**
     * User object
     */
    final User user;
    /**
     * Video title
     */
    final String title;
    /**
     * Season number
     */
    final int seasonNumber;
    /**
     * Constructor for commands
     */

    public Command(User user, String title, int seasonNumber) {
        this.user = user;
        this.title = title;
        this.seasonNumber = seasonNumber;
    }

    public abstract String execute();
}
