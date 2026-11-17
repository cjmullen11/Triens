# Triens

**Status:** In Design  
**Technologies:** Java · Object-Oriented Design · SQL

## Overview

Triens is a personal finance application for managing accounts, transactions, budgets, and investments. I am designing it around a consistent transaction ledger to make balances, scheduled activity, and historical valuations easier to calculate and audit.

The name **Triens** refers to an ancient Roman coin worth one-third of an *as*, reflecting the application's focus on accounting and allocating resources.

## Key Design Choices

- **Ledger-based accounting:** Derive balances from transaction entries rather than storing balances as mutable account fields.
- **Settlement awareness:** Represent pending and settled funds at the entry level.
- **Investment support:** Combine security transactions and historical prices to calculate holdings and valuations.
- **Separated services:** Keep transaction posting, interest calculations, schedules, and allocation logic distinct from data models.

## Roadmap

- [ ] Finalize the account, transaction, and ledger models.
- [ ] Implement transaction posting and balance calculations.
- [ ] Add budgets, allocations, and scheduled activity.
- [ ] Integrate investment holdings and historical valuations.
- [ ] Test ledger consistency and financial calculations.

## Usage

### Requirements

### Setup

### Configuration

### Operation
