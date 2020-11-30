package comparators;

import actor.Actor;
import actor.ActorsAwards;

import java.util.Comparator;
import java.util.List;

public class CompareActorsNumAwards implements Comparator<Actor> {
    private List<ActorsAwards> awardsToCheck;

    @Override
    public int compare(Actor o1, Actor o2) {
           return Integer.compare(o1.getNumOfAwards(), o2.getNumOfAwards());
    }
}
