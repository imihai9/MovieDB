package comparators;

import user.User;

import java.util.Comparator;

public class CompareUserNames implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return o1.getUsername().compareTo(o2.getUsername());
    }
}
