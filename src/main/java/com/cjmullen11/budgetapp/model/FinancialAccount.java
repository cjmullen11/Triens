import java.math.BigDecimal;

public abstract class FinancialAccount {
    private InterestPolicy interestPolicy;

    public abstract BigDecimal calculateInterest(List<DailyBalance> balances);
}