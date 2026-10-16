package main.java.com.cjmullen11.budgetapp.model;

public class BudgetBucket extends LedgerAccount{
    private BudgetBucket parentBucket;
    private int displayOrder;
    private BudgetGoal goal;
}