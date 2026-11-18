import java.math.BigDecimal;
import java.util.List;

public abstract class FinancialAccount {
    private InterestPolicy interestPolicy;
    public abstract BigDecimal calculateInterest(List<DailyBalance> balances);
}