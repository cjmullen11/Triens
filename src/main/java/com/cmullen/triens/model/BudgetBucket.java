package com.cmullen.triens.model;


public class BudgetBucket extends LedgerAccount{
    private BudgetBucket parentBucket;
    private int displayOrder;
    private BudgetGoal goal;
}