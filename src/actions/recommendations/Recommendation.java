package actions.recommendations;

import user.SubscriptionType;
import user.User;

public abstract class Recommendation {
    /**
     * Driver-method, will be overridden in child-classes
     * @param user - the user to which the current recommendation is made
     * @return - a string representing the recommendation output
     */
    public abstract String execute(final User user);

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
