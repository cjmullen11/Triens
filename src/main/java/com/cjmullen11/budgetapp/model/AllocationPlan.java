import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AllocationPlan {
    private UUID id;
    private String name;
    private List<AllocationStep> steps;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;


    public BigDecimal allocate(BigDecimal available, Map<BudgetBucket, BigDecimal> balances, Map<BudgetBucket, BigDecimal> goalProgress){
        //TODO
        return new BigDecimal(0);
    };
}
