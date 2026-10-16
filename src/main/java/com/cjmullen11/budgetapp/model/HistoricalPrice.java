import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoricalPrice {
    private UUID id;
    private Security security;
    private BigDecimal closingPrice;
    private LocalDate date;
    private Instant retrievedAt;
}
