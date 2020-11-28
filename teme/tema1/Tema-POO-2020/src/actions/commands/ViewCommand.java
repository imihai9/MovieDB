package actions.commands;

import user.User;

import java.util.Map;

public class ViewCommand extends Command{
    public ViewCommand(int actionId, User user, String title, int seasonNumber) {
        super(actionId, user, title, seasonNumber);
    }

    public String execute() {
        String message;
        Map<String, Integer> history = user.getHistory();

        if (history.containsKey(title)) {
            int timesSeen = history.get(title);
            user.addToHistory(title, timesSeen + 1);
        }
        else {
            user.addToHistory(title, 1);
        }

        message = "success -> " + title + " was viewed with total views of " + history.get(title);
        return message;
    }
}
