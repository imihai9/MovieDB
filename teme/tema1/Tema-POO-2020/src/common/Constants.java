package common;

/**
 * The class contains the minimum of constants needed.
 *
 * You can define your own constants here or create separate files.
 */
public final class Constants {
    private Constants() {
    }

    public static final String DATABASE = "database";
    public static final String ACTORS = "actors";
    public static final String MOVIES = "movies";
    public static final String USERS = "users";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String DESCRIPTION = "career_description";
    public static final String FILMOGRAPHY = "filmography";
    public static final String SUBSCRIPTION = "subscription";
    public static final String FAVORITE_MOVIES = "favourite";
    public static final String GENRES = "genres";
    public static final String SEASONS = "seasons";
    public static final String CURRENT_SEASON = "current_season";
    public static final String DURATION = "duration";
    public static final String NUMBER_OF_SEASONS = "number_of_seasons";
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String CAST = "cast";
    public static final String HISTORY = "history";
    public static final String SHOWS = "shows";
    public static final String ID = "action_id";
    public static final String ACTION_TYPE = "action_type";
    public static final String TYPE = "type";
    public static final String USER = "user";
    public static final String ACTIONS = "actions";
    public static final String FILTERS = "filters";
    public static final String SORT = "sort_type";
    public static final String GENRE = "genre";
    public static final String CRITERIA = "criteria";
    public static final String NUMBER = "number";
    public static final String OBJECT = "object_type";
    public static final String NUMBER_VIEWS = "no_views";
    public static final String AWARD_TYPE = "award_type";
    public static final String NUMBER_OF_AWARDS = "number_of_awards";
    public static final String SEASON = "season";
    public static final String GRADE = "grade";
    public static final String AWARDS = "awards";
    public static final String NUM_RATINGS = "num_ratings";
    public static final String FILTER_DESCRIPTIONS = "filter_description";
    public static final String WORDS = "words";
    public static final String REVIEWS = "reviews";
    public static final String MESSAGE = "message";
    public static final String QUERY = "query";
    public static final String COMMAND = "command";
    public static final String RECOMMENDATION = "recommendation";

    // checker constants
    public static final String ID_STRING = "id";
    public static final String LARGE = "large";
    public static final String NO_VALUES = "recommendation_no_values";
    public static final String TESTS_PATH = "test_db/test_files/";
    public static final String OUT_PATH = "result/out_";
    public static final String REF_PATH = "ref";
    public static final String RESULT_PATH = "result";
    public static final String JAR_PATH = "src/checker/checkstyle-8.36.2-all.jar";
    public static final String XML_PATH = "src/checker/poo_checks.xml";
    public static final String CHECKSTYLE_FILE = "checkstyle.txt";
    public static final String OUT_FILE = "out.txt";
    public static final int MIN_LINES = 2;
    public static final int NUM_CHECK_INFO = 3;
    public static final int MIN_CHECKSTYLE_ERR = 20;
    public static final int SINGLE_TEST = 2;
    public static final int LARGE_TEST = 3;
    public static final int MAX_LENGTH = 50;

    // -- solution constants

    // filter constants
    public static final int YEAR_FILTER_INDEX  = 0;
    public static final int GENRE_FILTER_INDEX = 1;
    public static final int KEYWORDS_FILTER_INDEX = 2;
    public static final int AWARDS_FILTER_INDEX = 3;
    public static final int FIRST_VALUE_INDEX = 0;

    // command-related constants:
    public static final String VIEW_COMMAND = "view";
    public static final String FAVORITE_COMMAND = "favorite";
    public static final String RATING_COMMAND = "rating";


    // query-related constants:
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

    public static final String CRITERIA_AWARDS = "awards";
    public static final String CRITERIA_AVERAGE = "average";
    public static final String CRITERIA_FILTER_DESCRIPTION = "filter_description";
    public static final String CRITERIA_RATINGS = "ratings";
    public static final String CRITERIA_FAVORITE = "favorite";
    public static final String CRITERIA_LONGEST = "longest";
    public static final String CRITERIA_MOST_VIEWED = "most_viewed";

    public static final String QUERY_RESULT_EMPTY = "Query result: []";
    public static final String QUERY_RESULT_PREFIX = "Query result: [";
    public static final String RESULT_BRACKET_OPEN = "[";
    public static final String QUERY_RESULT_SUFFIX = "]";
    public static final String QUERY_RESULT_DELIMITER = ", ";
    public static final int QUERY_DELIMITER_SIZE = 2;

    // recommendation-related constants:
    public static final String STANDARD_RECOMM = "standard";
    public static final String BEST_UNSEEN_RECOMM = "best_unseen";
    public static final String POPULAR_RECOMM = "popular";
    public static final String FAVORITE_RECOMM = "favorite";
    public static final String SEARCH_RECOMM = "search";

    public static final String STANDARD_RECOMM_PREFIX = "StandardRecommendation";
    public static final String BEST_UNSEEN_RECOMM_PREFIX = "BestRatedUnseenRecommendation"; //TODO: are these right?
    public static final String POPULAR_RECOMM_PREFIX = "PopularRecommendation";
    public static final String FAVORITE_RECOMM_PREFIX = "FavoriteRecommendation";
    public static final String SEARCH_RECOMM_PREFIX = "SearchRecommendation";


    public static final String RECOMM_ERROR_SUFFIX = "cannot be applied!";
    public static final String RECOMM_RESULTS = "result: ";

    public static final String ERROR = "error";
}
