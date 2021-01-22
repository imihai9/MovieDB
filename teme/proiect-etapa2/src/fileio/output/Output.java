package fileio.output;

import entities.Consumer;
import entities.Contract;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

public final class Output {
    private final List<ConsumerOutput> consumers;
    private final List<DistributorOutput> distributors;

    public Output(final List<Consumer> consumerList, final List<Distributor> distributorList) {
        consumers = new ArrayList<>();
        distributors = new ArrayList<>();

        for (Consumer consumer : consumerList) {
            ConsumerOutput consumerOutput = new ConsumerOutput(consumer.getId(),
                    consumer.isBankrupt(), consumer.getBudget());

            this.consumers.add(consumerOutput);
        }

        for (Distributor distributor : distributorList) {
            List<ContractOutput> contracts = new ArrayList<>();

            for (Contract contract : distributor.getContractList()) {
                ContractOutput contractOutput = new ContractOutput(contract.getConsumer().getId(),
                        contract.getPrice(), contract.getRemainedContractMonths());

                contracts.add(contractOutput);
            }

            DistributorOutput distributorOutput = new DistributorOutput(distributor.getId(),
                    distributor.getBudget(), distributor.isBankrupt(), contracts);

            this.distributors.add(distributorOutput);
        }
    }

    public List<ConsumerOutput> getConsumers() {
        return consumers;
    }

    public List<DistributorOutput> getDistributors() {
        return distributors;
    }
}
