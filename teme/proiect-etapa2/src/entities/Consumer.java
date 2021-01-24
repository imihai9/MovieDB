package entities;

import fileio.input.ConsumerInput;
import utils.Constants;
import utils.Utils;

import java.util.List;

public final class Consumer extends NetworkEntity {
    private int monthlyIncome;
    private boolean postponed;
    private int duePay;

    private Contract contract;

    public Consumer(final ConsumerInput consumerInput) {
        super();
        contract = null;
        postponed = false;
        this.id = consumerInput.getId();
        this.budget = consumerInput.getInitialBudget();
        this.monthlyIncome = consumerInput.getMonthlyIncome();
    }

    public Contract getContract() {
        return contract;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void receiveMonthlyIncome() {
        this.budget += monthlyIncome;
    }

    public boolean hasPostponed() {
        return postponed;
    }

    public void nullifyContract() {
        this.contract = null;
    }

    public void acceptContract(final Contract contract) {
        this.contract = contract;
    }

    /**
     * Contract payment attempt
     *
     * @return true,  if consumer was able to pay
     * false, otherwise
     */
    public boolean payContractCost() {
        if (bankrupt) {
            return false;
        }

        if (postponed) {
            int potentialBudget = this.budget - (int) Math.round(Math.floor(
                    Constants.POSTPONED_FACTOR * duePay))
                    - contract.getPrice();

            if (potentialBudget < 0) {
                bankrupt = true;
                return false;
            } else {
                this.budget = potentialBudget;
                duePay = 0;
                return true;
            }
        } else {
            int potentialBudget = this.budget - contract.getPrice();

            if (potentialBudget < 0) {
                postponed = true;
                duePay = contract.getPrice();
                return false;
            } else {
                this.budget = potentialBudget;
                return true;
            }
        }
    }

    /**
     * Chooses the minimum cost contract; adds it to this user and to the corresponding
     * distributor's list
     *
     * @param distributorsList - the given list of distributors
     */
    public void chooseContract(final List<Distributor> distributorsList) {
        Distributor distributor = Utils.getMinDistributor(distributorsList);

        this.contract = new Contract(this, distributor, distributor.getContractCost(),
                distributor.getContractLength());

        distributor.addContract(contract);
        this.acceptContract(contract);
    }
}
