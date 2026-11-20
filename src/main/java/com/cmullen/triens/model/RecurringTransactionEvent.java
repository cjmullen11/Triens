package com.cmullen.triens.model;

import com.cmullen.triens.dto.CreateTransactionEntryRequest;

public class RecurringTransactionEvent extends ScheduledEvent{
    private String description;
    private CreateTransactionEntryRequest template;
}
