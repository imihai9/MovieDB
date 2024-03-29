package actions.queries;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import comparators.CompareActorsAvgRating;
import comparators.CompareActorsName;
import comparators.CompareActorsNumAwards;
import data.Data;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ActorQuery extends Query {
    private List<Actor> filteredActors;
    private List<ActorsAwards> awards;
    private List<String> description;

    public ActorQuery() {
        filteredActors = new ArrayList<>(Data.getActors());
        awards = null;
        description = null;
    }

    @Override
    public String execute(final int number, final List<List<String>> filters,
                          final String sortType, final String criteria) {
        // Getting filters
        description = filters.get(Constants.KEYWORDS_FILTER_INDEX);
        List<String> awardNames = filters.get(Constants.AWARDS_FILTER_INDEX);

        awards = new ArrayList<>();
        if (awardNames != null) {
            awardNames.forEach(awardName -> awards.add(Utils.stringToAwards(awardName)));
        }

        filter();
        sort(number, sortType, criteria);

        return getQueryMessage();
    }

    @Override
    void filter() {
        if (description != null) {
            filteredActors = filteredActors.stream()
                    .filter(actor -> Utils
                            .containsAllWords(actor.getCareerDescription(), description))
                    .collect(Collectors.toList());
        }

        if (awards != null) {
            filteredActors = filteredActors.stream()
                    .filter(actor -> awards.stream()
                            .allMatch(award -> actor.getAwards()
                                    .containsKey(award))) //checks if actor has all awards
                    .collect(Collectors.toList());
        }
    }

    @Override
    void sort(final int number, final String sortType, final String criteria) {
        Comparator<Actor> comparator;
        Comparator<Actor> nameComparator = new CompareActorsName();

        switch (criteria) {
            case Constants.CRITERIA_AVERAGE -> {
                comparator = new CompareActorsAvgRating().thenComparing(nameComparator);
                filteredActors = filteredActors.stream()
                        .filter(actor -> actor.getAvgRating() != -1)
                        .collect(Collectors.toList());
            }
            case Constants.CRITERIA_AWARDS -> {
                comparator = new CompareActorsNumAwards().thenComparing(nameComparator);
            }
            // The remaining case: CRITERIA_FILTER_DESCRIPTION
            default -> {
                comparator = nameComparator;
            }
        }

        if (sortType.equals(Constants.SORT_DESC)) {
            comparator = comparator.reversed();
        }

        filteredActors = filteredActors.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (number > 0) {
            filteredActors = filteredActors.stream()
                    .limit(number)
                    .collect(Collectors.toList());
        }
    }

    @Override
    String getQueryMessage() {
        if (filteredActors.isEmpty()) {
            return Constants.QUERY_RESULT_EMPTY;
        }

        StringBuilder message = new StringBuilder(Constants.QUERY_RESULT_PREFIX);
        for (Actor actor : filteredActors) {
            message.append(actor.getName());
            message.append(Constants.QUERY_RESULT_DELIMITER);
        }

        message.setLength(
                message.length() - Constants.QUERY_DELIMITER_SIZE); // deleting the last ", "
        message.append(Constants.QUERY_RESULT_SUFFIX);
        return message.toString();
    }
}
