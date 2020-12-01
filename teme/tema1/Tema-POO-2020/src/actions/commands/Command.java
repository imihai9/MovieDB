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
    public Command(final User user, final String title, final int seasonNumber) {
        this.user = user;
        this.title = title;
        this.seasonNumber = seasonNumber;
    }

    /**
     * Driver-method, will be overridden in child-classes
     * @return - a string representing the command output
     */
    public abstract String execute();
}
