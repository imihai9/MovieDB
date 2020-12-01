package actions.queries;

import common.Constants;
import comparators.CompareShowsAvgRating;
import comparators.CompareShowsDuration;
import comparators.CompareShowsFavorite;
import comparators.CompareShowsMostViewed;
import comparators.CompareShowsTitle;
import data.Data;
import entertainment.Genre;
import entertainment.Show;
import user.User;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ShowQuery extends Query {
    private final List<User> users;
    private List<Show> filteredShows;
    private int year;
    private Genre genre;

    public ShowQuery(final String showType) {
        if (showType.equals(Constants.MOVIES)) {
            filteredShows = new ArrayList<>(Data.getMovies());
        } else {
            filteredShows = new ArrayList<>(Data.getSerials());
        }

        year = 0;
        genre = null;

        users = Data.getUsers();
    }

    @Override
    public String execute(final int number, final List<List<String>> filters,
                          final String sortType, final String criteria) {
        // Getting filters
        String filter1 = filters.get(Constants.YEAR_FILTER_INDEX).get(Constants.FIRST_VALUE_INDEX);
        if (filter1 != null) {
            year = Integer.parseInt(filter1);
        }

        String genreName =
                filters.get(Constants.GENRE_FILTER_INDEX).get(Constants.FIRST_VALUE_INDEX);
        if (genreName != null) {
            genre = Utils.stringToGenre(genreName);

            // If given genre doesn't exist => return an empty response
            if (genre == null) {
                filteredShows = new ArrayList<>();
                return getQueryMessage();
            }
        }

        filter();
        sort(number, sortType, criteria);
        return getQueryMessage();
    }

    @Override
    void filter() {
        if (year != 0) {
            filteredShows = filteredShows.stream()
                    .filter(show -> show.getYear() == year)
                    .collect(Collectors.toList());
        }

        if (genre != null) {
            filteredShows = filteredShows.stream()
                    .filter(show -> show.getGenres()
                            .contains(genre)) //check if given genre is among movie genre list
                    .collect(Collectors.toList());
        }
    }

    @Override
    void sort(final int number, final String sortType, final String criteria) {
        Comparator<Show> comparator;
        Comparator<Show> titleComparator = new CompareShowsTitle();

        // Filter out unrated shows
        // Filter out shows that are not in any user's favourite list
        // Filter out shows that are not in any user's view history
        switch (criteria) {
            case Constants.CRITERIA_RATINGS -> {
                filteredShows = filteredShows.stream()
                        .filter(Show::hasBeenRated)
                        .collect(Collectors.toList());
                comparator = new CompareShowsAvgRating().thenComparing(titleComparator);
            }
            case Constants.CRITERIA_FAVORITE -> {
                filteredShows = filteredShows.stream()
                        .filter(show ->
                                Utils.getUserStats(users, show, Constants.CRITERIA_FAVORITE) != 0)
                        .collect(Collectors.toList());
                comparator = new CompareShowsFavorite(users).thenComparing(titleComparator);
            }
            case Constants.CRITERIA_LONGEST -> {
                comparator = new CompareShowsDuration().thenComparing(titleComparator);
            }
            case Constants.CRITERIA_MOST_VIEWED -> {
                filteredShows = filteredShows.stream()
                        .filter(show ->
                                Utils.getUserStats(users, show, Constants.CRITERIA_MOST_VIEWED)
                                        != 0)
                        .collect(Collectors.toList());
                comparator = new CompareShowsMostViewed(users).thenComparing(titleComparator);
            }
            // Default case: sorting based on show title
            default -> {
                comparator = new CompareShowsTitle();
            }
        }

        if (sortType.equals("desc")) {
            comparator = comparator.reversed();
        }

        filteredShows = filteredShows.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (number > 0) {
            filteredShows = filteredShows.stream()
                    .limit(number)
                    .collect(Collectors.toList());
        }
    }

    @Override
    String getQueryMessage() {
        if (filteredShows.isEmpty()) {
            return Constants.QUERY_RESULT_EMPTY;
        }

        StringBuilder message = new StringBuilder(Constants.QUERY_RESULT_PREFIX);
        for (Show show : filteredShows) {
            message.append(show.getTitle());
            message.append(Constants.QUERY_RESULT_DELIMITER);
        }

        message.setLength(
                message.length() - Constants.QUERY_DELIMITER_SIZE); // deleting the last ", "
        message.append(Constants.QUERY_RESULT_SUFFIX);
        return message.toString();
    }
}
