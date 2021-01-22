package fileio.input;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.CostChange;
import entities.Distributor;
import entities.MonthlyUpdate;
import factories.ConsumerFactory;
import factories.DistributorFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader {
    // Singleton class (it has no state)
    private static InputLoader instance = null;

    private InputLoader() {
    }

    public static InputLoader getInstance() {
        if (instance == null) {
            instance = new InputLoader();
        }
        return instance;
    }

    public Input readInitialData(final File inputFile) throws IOException {
        Input input = new Input();

        List<Consumer> consumerList = new ArrayList<>();
        List<Distributor> distributorList = new ArrayList<>();
        List<MonthlyUpdate> monthlyUpdateList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(inputFile);

        int numberOfTurns = root.at("/numberOfTurns").asInt();

        JsonNode consumerListRoot = root.at("/initialData/consumers");
        JsonNode distributorListRoot = root.at("/initialData/distributors");
        JsonNode monthlyUpdateListRoot = root.at("/monthlyUpdates");

        for (JsonNode consumerNode : consumerListRoot) {
            ConsumerInput consumerInput = mapper.treeToValue(consumerNode, ConsumerInput.class);
            consumerList.add(ConsumerFactory.getInstance().createConsumer(
                    "basic consumer", consumerInput));
        }

        for (JsonNode distributorNode : distributorListRoot) {
            DistributorInput distributorInput = mapper.treeToValue(distributorNode,
                    DistributorInput.class);
            distributorList.add(DistributorFactory.getInstance().createDistributor(
                    "basic distributor", distributorInput));
        }

        for (JsonNode monthlyUpdateNode : monthlyUpdateListRoot) {
            JsonNode newConsumersListRoot = monthlyUpdateNode.at("/newConsumers");
            JsonNode costsChangesListRoot = monthlyUpdateNode.at("/costsChanges");

            List<Consumer> newConsumerList = new ArrayList<>();
            List<CostChange> costChangeList = new ArrayList<>();

            for (JsonNode newConsumerNode : newConsumersListRoot) {
                ConsumerInput consumerInput =
                        mapper.treeToValue(newConsumerNode, ConsumerInput.class);
                newConsumerList.add(ConsumerFactory.getInstance().createConsumer(
                        "basic consumer", consumerInput));
            }
            for (JsonNode costChangeNode : costsChangesListRoot) {
                costChangeList.add(mapper.treeToValue(costChangeNode, CostChange.class));
            }

            monthlyUpdateList.add(new MonthlyUpdate(newConsumerList, costChangeList));
        }

        return new Input(numberOfTurns, consumerList, distributorList, monthlyUpdateList);
    }
}
