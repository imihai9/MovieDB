package simulation;

import entities.Consumer;
import entities.Contract;
import entities.CostChange;
import entities.Distributor;
import entities.MonthlyUpdate;
import fileio.input.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Simulation {
    private final Input inputData;
    private final int round;

    // Lists of entities loaded and updated at each round Simulation

    private final List<Distributor> distributorsList;
    private List<Consumer> consumersList;

    public Simulation(final Input inputData, final int round) {
        this.inputData = inputData;
        this.round = round;

        // Getting the lists of the entities that are still in the game
        this.distributorsList = inputData.getDistributorList().stream()
                .filter(d -> !d.isBankrupt())
                .collect(Collectors.toList());
        this.consumersList = inputData.getConsumerList().stream()
                .filter(c -> !c.isBankrupt())
                .collect(Collectors.toList());
    }

    /**
     * Checks users that don't have a contract / have an expired contract
     * Renews their contracts
     */
    void processInvalidContracts() {
        List<Consumer> consumersToRenew = new ArrayList<>();

        for (Consumer consumer : consumersList) {
            Contract contract = consumer.getContract();
            if (contract == null) {
                consumersToRenew.add(consumer);
            } else if (contract.getRemainedContractMonths() == 0) {
                Distributor distributor = contract.getDistributor();

                if (distributor != null) {
                    distributor.removeContract(consumer.getContract());
                }
                consumersToRenew.add(consumer);
            }
        }

        for (Consumer consumer : consumersToRenew) {
            consumer.chooseContract(distributorsList);
        }
    }

    /**
     * Destroys contracts of bankrupt distributors
     */
    void processBankruptDistributors(final List<Distributor> bankruptDistributors) {
        for (Distributor distributor : bankruptDistributors) {
            distributor.declareBankruptcy(consumersList);
        }
    }

    /**
     * Update the remaining months of each contract
     */
    void monthPassed() {
        for (Distributor distributor : distributorsList) {
            for (Contract contract : distributor.getContractList()) {
                contract.monthPassed();
            }
        }
    }

    /**
     * Reads cost changes and new consumers for the current round
     */
    void readMonthlyUpdates() {
        MonthlyUpdate monthlyUpdate = inputData.getMonthlyUpdateList().get(round - 1);

        List<CostChange> costChanges = monthlyUpdate.getCostChanges();
        if (!costChanges.isEmpty()) {
            for (CostChange costChange : costChanges) {
                Distributor distributor = inputData.getDistributorList().stream()
                        .filter(d -> d.getId() == costChange.getId())
                        .findFirst()
                        .orElse(null);

                // Update changes in distributors
                distributor.setInfrastructureCost(costChange.getInfrastructureCost());
                distributor.setProductionCost(costChange.getProductionCost());
            }
        }

        List<Consumer> newConsumers = monthlyUpdate.getNewConsumers();

        if (!newConsumers.isEmpty()) {
            for (Consumer consumer : newConsumers) {
                inputData.addToConsumerList(consumer);
            }

            // Update local consumer list
            this.consumersList = inputData.getConsumerList().stream()
                    .filter(c -> !c.isBankrupt())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Driver function for the round simulation
     */
    public void execute() {
        List<Distributor> bankruptDistributors = new ArrayList<>();

        if (round == 0) {
            // Calculating the initial contract costs for each distributor (no clients)
            for (Distributor distributor : distributorsList) {
                distributor.calculateContractCost();
            }

            // Choosing the contract for each customer
            for (Consumer consumer : consumersList) {
                consumer.chooseContract(distributorsList);
            }
        } else {
            // Reading updates
            readMonthlyUpdates();

            // Recalculate contract costs
            for (Distributor distributor : distributorsList) {
                distributor.calculateContractCost();
            }

            // Process bankrupt distributors
            processBankruptDistributors(bankruptDistributors);

            // Process invalid contracts (for new consumers / consumers with a bankrupt distributor)
            processInvalidContracts();
        }

        for (Consumer consumer : consumersList) {
            // Consumers receive salary
            consumer.receiveMonthlyIncome();
        }

        // List of distributors that went bankrupt this month, and their clients must
        // choose another contract next month
        bankruptDistributors.clear();

        for (Distributor distributor : distributorsList) {
            // Distributors receive the payments from the consumers
            // Distributors pay the monthly fees
            distributor.processPayments();

            // Check if distributor has gone bankrupt
            if (distributor.isBankrupt()) {
                bankruptDistributors.add(distributor);
            }
        }

        monthPassed();
    }
}
