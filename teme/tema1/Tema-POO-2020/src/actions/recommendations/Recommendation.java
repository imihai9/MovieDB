package actions.recommendations;

import user.SubscriptionType;
import user.User;

public class Recommendation {
    public String execute(User user) {
        return null;
    }
    String getQueryMessage() {
        return null;
    }

    /**
     * @param user - user to be verified
     * @return - 1 if user has a premium subscription, 0 otherwise
     */
    boolean securityCheckFail(User user) {
        return !user.getSubscriptionType().equals(SubscriptionType.PREMIUM);
    }
}
