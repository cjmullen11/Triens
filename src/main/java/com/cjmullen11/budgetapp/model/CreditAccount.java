import java.math.BigDecimal;

public class CreditAccount extends FinancialAccount {
    private BigDecimal limit;
    private BankAccount defaultPaymentAccount;
    private int StatementClosingDate;
    private int StatementDueDate;

    public BigDecimal getAvailableCredit(BigDecimal balance){
        return limit.subtract(balance);
    };

    public BigDecimal getUtilization(BigDecimal balance) {
        return balance.divide(limit);
    }
}