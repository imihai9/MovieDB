package actions.commands;

import user.User;

public abstract class Command {
    //TODO: delete Action id
    /**
     * Action id
     */
    final int actionId;
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
     * Constructor for commands referring to a *Serial*
     */
    public Command(int actionId, User user, String title, int seasonNumber) {
        this.actionId = actionId;
        this.user = user;
        this.title = title;
        this.seasonNumber = seasonNumber;
    }

    public int getActionId() {
        return actionId;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public abstract String execute();
}
