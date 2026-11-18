import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "allocation_plan")
public class AllocationPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
