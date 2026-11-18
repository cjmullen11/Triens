package com.cmullen.triens.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "entryType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CreateLedgerEntryRequest.class, name = "LEDGER"),
    @JsonSubTypes.Type(value = CreateSecurityEntryRequest.class, name = "SECURITY")
})
public sealed interface CreateTransactionEntryRequest
        permits CreateLedgerEntryRequest, CreateSecurityEntryRequest {

    UUID accountId();
}