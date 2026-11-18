package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

public class CreditCardInterestPolicy extends InterestPolicy{
    private BigDecimal purchaseAPR;
    private int gracePeriodDays;
}
