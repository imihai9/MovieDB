package comparators;


import actor.Actor;

import java.util.Comparator;

public class CompareActorsAvgRating implements Comparator<Actor> {
    @Override
    public int compare(Actor o1, Actor o2) {
        return Double.compare(o1.getAvgRating(), o2.getAvgRating());
    }
}
