package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class NetworkEntity {
    protected int id;
    protected int budget;
    protected boolean bankrupt;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    @JsonProperty("initialBudget")
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        this.bankrupt = bankrupt;
    }
}
