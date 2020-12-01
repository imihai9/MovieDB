package actions.recommendations;

import user.SubscriptionType;
import user.User;

public class Recommendation {
    /**
     * Driver-method, will be overridden in child-classes
     * @param user - the user to which the current recommendation is made
     * @return - a string representing the recommendation output
     */
    public String execute(final User user) {
        return null;
    }

    String getQueryMessage() {
        return null;
    }

    /**
     * @param user - user to be verified
     * @return - 1 if user has a premium subscription, 0 otherwise
     */
    boolean securityCheckFail(final User user) {
        return !user.getSubscriptionType().equals(SubscriptionType.PREMIUM);
    }
}
