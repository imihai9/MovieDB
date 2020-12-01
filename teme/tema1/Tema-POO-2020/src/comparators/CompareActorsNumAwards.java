package comparators;

import actor.Actor;

import java.util.Comparator;

public final class CompareActorsNumAwards implements Comparator<Actor> {
    @Override
    public int compare(final Actor o1, final Actor o2) {
        return Integer.compare(o1.getNumOfAwards(), o2.getNumOfAwards());
    }
}
