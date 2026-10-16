public abstract class TransactionEntry {
    private UUID id;
    private Instant settledAt;

    public boolean isSettled() {
        return settledAt != null;
    }
}