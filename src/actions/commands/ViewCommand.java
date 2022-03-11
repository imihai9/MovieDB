package actions.commands;

import user.User;

import java.util.Map;

public final class ViewCommand extends Command {
    public ViewCommand(final User user, final String title, final int seasonNumber) {
        super(user, title, seasonNumber);
    }

    public String execute() {
        String message;
        Map<String, Integer> history = user.getHistory();

        if (history.containsKey(title)) {
            int timesSeen = history.get(title);
            user.addToHistory(title, timesSeen + 1);
        } else {
            user.addToHistory(title, 1);
        }

        message = "success -> " + title + " was viewed with total views of " + history.get(title);
        return message;
    }
}
