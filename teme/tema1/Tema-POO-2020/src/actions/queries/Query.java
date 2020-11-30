package actions.queries;

import java.util.List;

public abstract class Query {
    public abstract String execute(int number, List<List<String>> filters, String sort_type, String criteria);
    abstract void filter();
    abstract void sort(int number, String sort_type, String criteria);
    abstract String getQueryMessage();
}
