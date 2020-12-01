package actions.queries;

import java.util.List;

public abstract class Query {
    /**
     * Driver-method, will be overridden in child-classes
     * @param number - if null, all query results will be displayed
     *               - otherwise, the first *number* results will be displayed;
     * @param filters - the filters being applied to the queried list
     * @param sortType - the sorting type applied to the queried list
     *                 - (asc / desc)
     * @param criteria - the sorting criteria applied to the queried list
     * @return - a string representing the query output
     */
    public abstract String execute(int number, List<List<String>> filters,
                                   String sortType, String criteria);

    abstract void filter();

    abstract void sort(int number, String sortType, String criteria);

    abstract String getQueryMessage();
}
