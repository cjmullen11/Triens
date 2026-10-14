import java.math.BigDecimal;

public abstract class FinancialAccount {
    private InterestPolicy interestPolicy;
    private abstract BigDecimal calculateInterest(List<DailyBalance> balances);
}