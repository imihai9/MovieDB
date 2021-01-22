package fileio.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "budget", "isBankrupt", "contracts"})
public final class DistributorOutput {
    private final int id;
    private final int budget;
    private final boolean isBankrupt;
    private final List<ContractOutput> contracts;

    public DistributorOutput(final int id, final int budget, final boolean isBankrupt,
                             final List<ContractOutput> contracts) {
        this.id = id;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("budget")
    public int getBudget() {
        return budget;
    }

    @JsonProperty("isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    @JsonProperty("contracts")
    public List<ContractOutput> getContracts() {
        return contracts;
    }
}
