import java.math.BigDecimal;
import java.time.Instant;

public class BudgetGoal {
    private UUID id;
    private GoalType type;
    private BigDecimal targetAmount;
    private Recurrence  recurrence;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
