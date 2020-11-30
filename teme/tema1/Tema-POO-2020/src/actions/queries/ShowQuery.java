package actions.queries;

import common.Constants;
import comparators.*;
import data.Data;
import entertainment.Genre;
import entertainment.Show;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowQuery extends Query{
    private List<Show> filteredShows;
    private int year;
    private Genre genre;

    public ShowQuery(String showType) {
        if (showType.equals(Constants.MOVIES))
            filteredShows = new ArrayList<Show>(Data.getMovies());
        else
            filteredShows = new ArrayList<Show>(Data.getSerials());

        year = 0;
        genre = null;
    }

    @Override
    public String execute(int number, List<List<String>> filters, String sort_type,
                          String criteria) {
        // Getting filters
        // Filter:0 - year, 1 - genre
        String filter1 = filters.get(0).get(0); //TODO: resolve magic numbers
        if (filter1 != null) {
            year = Integer.parseInt(filter1);
        }

        String genreName = filters.get(1).get(0);
        if (genreName != null) {
            genre = Utils.stringToGenre(genreName);

            // If given genre doesn't exist => return an empty response
            if (genre == null) {
                filteredShows = new ArrayList<>();
                return getQueryMessage();
            }
        }

        filter();
        sort(number, sort_type, criteria);
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
                    .filter(show -> show.getGenres().contains(genre)) //check if given genre is among movie genre list
                    .collect(Collectors.toList());
        }
    }

    @Override
    void sort (int number, String sort_type, String criteria) {
        Comparator<Show> comparator = null;
        Comparator<Show> titleComparator = new CompareShowsTitle();

        switch(criteria) {
            case Constants.CRITERIA_RATINGS:
               // Filter out unrated shows
                filteredShows = filteredShows.stream()
                        .filter(Show::hasBeenRated)
                        .collect(Collectors.toList());

                comparator = new CompareShowsAvgRating().thenComparing(titleComparator);
                break;

            case Constants.CRITERIA_FAVORITE:
                // Filter out shows that are not in any user's favourite list
                filteredShows = filteredShows.stream()
                        .filter(show -> Utils.getUserStats(show, Constants.CRITERIA_FAVORITE) != 0)
                        .collect(Collectors.toList());

                comparator = new CompareShowsFavorite().thenComparing(titleComparator);
                break;

            case Constants.CRITERIA_LONGEST:
                comparator = new CompareShowsDuration().thenComparing(titleComparator);
                break;

            case Constants.CRITERIA_MOST_VIEWED:
                // Filter out shows that are not in any user's view history
                filteredShows = filteredShows.stream()
                        .filter(show -> Utils.getUserStats(show, Constants.CRITERIA_MOST_VIEWED) != 0)
                        .collect(Collectors.toList());

                comparator = new CompareShowsMostViewed().thenComparing(titleComparator);
                break;

            default: // Default case: sorting based on show title
                comparator = new CompareShowsTitle();
                break;
        }

        if (sort_type.equals("desc")) {
            comparator = comparator.reversed();
        }

        filteredShows = filteredShows.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (number > 0)
            filteredShows = filteredShows.stream()
                    .limit(number)
                    .collect(Collectors.toList());
    }

    @Override
    String getQueryMessage () {
        if (filteredShows.isEmpty()) {
            return Constants.QUERY_RESULT_EMPTY;
        }

        StringBuilder message = new StringBuilder(Constants.QUERY_RESULT_PREFIX);
        for (Show show:filteredShows) {
            message.append(show.getTitle());
            message.append(Constants.QUERY_RESULT_DELIMITER);
        }

        message.setLength(message.length() - Constants.QUERY_DELIMITER_SIZE); // deleting the last ", "
        message.append(Constants.QUERY_RESULT_SUFFIX);
        return message.toString();
    }
}