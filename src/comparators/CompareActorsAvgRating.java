package comparators;


import actor.Actor;

import java.util.Comparator;

public final class CompareActorsAvgRating implements Comparator<Actor> {
    @Override
    public int compare(final Actor o1, final Actor o2) {
        return Double.compare(o1.getAvgRating(), o2.getAvgRating());
    }
}
