package comparators;

import user.User;

import java.util.Comparator;

public final class CompareUserNames implements Comparator<User> {
    @Override
    public int compare(final User o1, final User o2) {
        return o1.getUsername().compareTo(o2.getUsername());
    }
}
