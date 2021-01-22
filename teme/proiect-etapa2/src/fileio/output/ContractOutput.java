package fileio.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"consumerId", "price", "remainedContractMonths"})
public final class ContractOutput {
    private final int consumerId;
    private final int price;
    private final int remainedContractMonths;

    public ContractOutput(final int consumerId, final int price, final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    @JsonProperty("consumerId")
    public int getConsumerId() {
        return consumerId;
    }

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    @JsonProperty("remainedContractMonths")
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
}
