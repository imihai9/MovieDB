package comparators;

import actor.Actor;

import java.util.Comparator;

public class CompareActorsName implements Comparator<Actor> {
    @Override
    public int compare(Actor o1, Actor o2) {
        return o1.getName().compareTo(o2.getName());
    }
}


