package actions.queries;

import actor.Actor;
import common.Constants;
import comparators.CompareUserNames;
import comparators.CompareUsersNumRatings;
import data.Data;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserQuery extends Query{
    private List<User> filteredUsers;

    public UserQuery() {
        filteredUsers = new ArrayList<User>(Data.getUsers());
    }

    @Override
    public String execute(int number, List<List<String>> filters, String sort_type, String criteria) {
        filter();
        sort(number, sort_type, criteria);

        return getQueryMessage();
    }

    @Override
    void filter() {}

    @Override
    void sort(int number, String sort_type, String criteria) {
        if (criteria.equals(Constants.NUM_RATINGS)) {
            filteredUsers = filteredUsers.stream()
                    .filter(user -> user.getTotalRatingsNum() != 0) //TODO: here -> .sorted(comparator)
                    .collect(Collectors.toList());

            Comparator<User> comparator = new CompareUsersNumRatings().thenComparing(new CompareUserNames());

            if (sort_type.equals(Constants.SORT_DESC))
                comparator = comparator.reversed();

            filteredUsers = filteredUsers.stream()
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
        for (User user:filteredUsers) {
            message.append(user.getUsername());
            message.append(Constants.QUERY_RESULT_DELIMITER);
        }

        message.setLength(message.length() - Constants.QUERY_DELIMITER_SIZE); // deleting the last ", "
        message.append(Constants.QUERY_RESULT_SUFFIX);
        return message.toString();
    }
}
