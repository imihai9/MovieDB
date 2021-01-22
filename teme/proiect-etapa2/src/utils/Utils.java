package utils;

import entities.Distributor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Utils {
    private Utils() {
    }

    /**
     * @param distributorsList - the given distributors list
     * @return - the distributor with the lowest cost
     */
    public static Distributor getMinDistributor(final List<Distributor> distributorsList) {
        return Collections.min(distributorsList,
                Comparator.comparing(Distributor::getContractCost));
    }
}
