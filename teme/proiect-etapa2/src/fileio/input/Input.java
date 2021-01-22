package fileio.input;

import entities.Consumer;
import entities.Distributor;
import entities.MonthlyUpdate;

import java.util.Collections;
import java.util.List;

public final class Input {
    // initial data
    private final int numberOfTurns;
    private final List<Consumer> consumerList;
    private final List<Distributor> distributorList;

    // update data
    private final List<MonthlyUpdate> monthlyUpdateList;

    public Input() {
        numberOfTurns = 0;
        consumerList = null;
        distributorList = null;
        monthlyUpdateList = null;
    }

    public Input(final int numberOfTurns, final List<Consumer> consumerList,
                 final List<Distributor> distributorList,
                 final List<MonthlyUpdate> monthlyUpdateList) {
        this.numberOfTurns = numberOfTurns;
        this.consumerList = consumerList;
        this.distributorList = distributorList;
        this.monthlyUpdateList = monthlyUpdateList;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public List<Consumer> getConsumerList() {
        return Collections.unmodifiableList(consumerList);
    }

    public void addToConsumerList(final Consumer consumer) {
        consumerList.add(consumer);
    }

    public List<Distributor> getDistributorList() {
        return Collections.unmodifiableList(distributorList);
    }

    public List<MonthlyUpdate> getMonthlyUpdateList() {
        return monthlyUpdateList;
    }
}
