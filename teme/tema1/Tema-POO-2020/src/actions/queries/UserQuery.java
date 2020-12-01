package actions.queries;

import common.Constants;
import comparators.CompareUserNames;
import comparators.CompareUsersNumRatings;
import data.Data;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class UserQuery extends Query {
    private List<User> filteredUsers;

    public UserQuery() {
        filteredUsers = new ArrayList<>(Data.getUsers());
    }

    @Override
    public String execute(final int number, final List<List<String>> filters,
                          final String sortType, final String criteria) {
        filter();
        sort(number, sortType, criteria);

        return getQueryMessage();
    }

    @Override
    void filter() {
    }

    @Override
    void sort(final int number, final String sortType, final String criteria) {
        if (criteria.equals(Constants.NUM_RATINGS)) {
            Comparator<User> comparator =
                    new CompareUsersNumRatings().thenComparing(new CompareUserNames());
            if (sortType.equals(Constants.SORT_DESC)) {
                comparator = comparator.reversed();
            }

            // Filter out users that didn't give any rating
            // Sort users by number of ratings given, then by their usernames

            filteredUsers = filteredUsers.stream()
                    .filter(user -> user.getTotalRatingsNum() != 0)
                    .sorted(comparator)
                    .collect(Collectors.toList());

            if (number != 0) {
                filteredUsers = filteredUsers.stream()
                        .limit(number)
                        .collect(Collectors.toList());
            }
        }
    }

    @Override
    String getQueryMessage() {
        if (filteredUsers.isEmpty()) {
            return Constants.QUERY_RESULT_EMPTY;
        }

        StringBuilder message = new StringBuilder(Constants.QUERY_RESULT_PREFIX);
        for (User user : filteredUsers) {
            message.append(user.getUsername());
            message.append(Constants.QUERY_RESULT_DELIMITER);
        }

        message.setLength(
                message.length() - Constants.QUERY_DELIMITER_SIZE); // deleting the last ", "
        message.append(Constants.QUERY_RESULT_SUFFIX);
        return message.toString();
    }
}
