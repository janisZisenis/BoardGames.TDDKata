package Utilities.Transaction;

public class TransactionSpy extends TransactionDummy {

    private boolean didExecute = false;

    public boolean hasExecuted() {
        return didExecute;
    }
    public void execute() {
        didExecute = true;
    }
}