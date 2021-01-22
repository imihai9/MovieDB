package entities;

import fileio.input.DistributorInput;
import utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Distributor extends NetworkEntity {
    private final List<Contract> contractList;
    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private int contractCost;

    public Distributor(final DistributorInput distributorInput) {
        bankrupt = false;
        contractCost = 0;
        contractList = new ArrayList<>();

        this.id = distributorInput.getId();
        this.budget = distributorInput.getInitialBudget();
        this.contractLength = distributorInput.getContractLength();
        this.infrastructureCost = distributorInput.getInitialInfrastructureCost();
        this.productionCost = distributorInput.getInitialProductionCost();
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public int getContractCost() {
        return contractCost;
    }

    public List<Contract> getContractList() {
        return Collections.unmodifiableList(contractList);
    }

    /**
     * Adds contract to the distributor's contract list
     * @param contract the given contract
     */
    public void addContract(final Contract contract) {
        contractList.add(contract);
    }

    /**
     * Removes contract from the distributor's contract list
     * @param contract the given contract
     */
    public void removeContract(final Contract contract) {
        this.contractList.remove(contract);
    }

    /**
     * @return the profit
     */
    public int calculateProfit() {
        return (int) Math.round(Math.floor(Constants.PROFIT_FACTOR * productionCost));
    }

    /**
     * Wrapper function for contract cost calculation
     */
    public void calculateContractCost() {
        if (contractList.isEmpty()) {
            this.contractCost = calculateInitialContractCost();
        } else {
            this.contractCost = calculateFinalContractCost();
        }
    }

    /**
     * Calculates contract cost (for no clients)
     */
    private int calculateInitialContractCost() {
        return infrastructureCost + productionCost + this.calculateProfit();
    }

    /**
     * Calculates contract cost (for number of clients not zero)
     */
    private int calculateFinalContractCost() {
        return (int) Math.round(Math.floor((float) infrastructureCost / contractList.size())
                + productionCost + this.calculateProfit());
    }

    public void payInfrastructureCost() {
        this.budget -= infrastructureCost;
    }

    public void payProductionCost() {
        this.budget -= productionCost * contractList.size();
    }

    public void receiveClientsPayment(final int totalMonthPayments) {
        this.budget += totalMonthPayments;
    }

    /**
     * @param consumersList The list of consumers
     */
    public void declareBankruptcy(final List<Consumer> consumersList) {
        this.bankrupt = true;
        Iterator<Contract> it = this.contractList.iterator();
        while (it.hasNext()) {
            Contract contract = it.next();

            // Remove contract from the consumers
            contract.getConsumer().nullifyContract();
            it.remove();
        }
    }

    /**
     * Process payments received from consumers, pay fees and possibly declare bankruptcy.
     */
    public void processPayments() {
        // List of ids of consumers that will be disconnected
        List<Integer> idsToDisconnect = new ArrayList<>();

        int totalPayments = 0;

        // Verify clients
        for (Contract contract : this.getContractList()) {
            Consumer consumer = contract.getConsumer();

            // To pass warning, even though we're sure the consumer exist
            if (consumer == null) {
                continue;
            }

            boolean hasPostponed = consumer.hasPostponed();
            boolean hasPaid = consumer.payContractCost();

            if (hasPaid) {
                if (hasPostponed) {
                    totalPayments += (int) Math.round(Math.floor(Constants.POSTPONED_FACTOR
                            * contract.getPrice()));
                } else {
                    totalPayments += contract.getPrice();
                }
            } else {
                if (consumer.isBankrupt()) {
                    consumer.nullifyContract();
                    idsToDisconnect.add(consumer.getId());
                }
            }
        }

        // Distributor receives fees from clients
        this.receiveClientsPayment(totalPayments);

        // Distributor pays cost
        this.payInfrastructureCost();
        this.payProductionCost();

        this.contractList
                .removeIf(contract -> idsToDisconnect.contains(contract.getConsumer().getId()));

        if (this.getBudget() < 0) {
            this.setBankrupt(true);
        }
    }
}
