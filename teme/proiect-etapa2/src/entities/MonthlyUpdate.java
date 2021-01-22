package entities;

import java.util.List;

public final class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private final List<CostChange> costChanges;

    public MonthlyUpdate(final List<Consumer> newConsumers, final List<CostChange> costChanges) {
        this.newConsumers = newConsumers;
        this.costChanges = costChanges;
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<Consumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChange> getCostChanges() {
        return costChanges;
    }
}
