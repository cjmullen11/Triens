import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public abstract class AllocationStep {
    private UUID id;
    private int position;
    private boolean active;

    public Map<BudgetBucket, BigDecimal> allocate(BigDecimal available, Map<BudgetBucket, BigDecimal> balances, Map<BudgetBucket, BigDecimal> goalProgress) {

    }
}
